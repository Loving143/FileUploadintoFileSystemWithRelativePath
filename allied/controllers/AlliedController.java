package com.cdac.centralvista.allied.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.centralvista.allied.service.AlliedEmpServiceInfoService;
import com.cdac.centralvista.allied.service.AlliedEmployeeService;
import com.cdac.centralvista.alliedAdmin.dto.request.AddAlliedEmpServiceInfoRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.RegisterAlliedEmpRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.UpdateAlliedEmpRequest;
import com.cdac.centralvista.alliedAdmin.dto.response.AlliedAdminDashboardResponse;
import com.cdac.centralvista.alliedAdmin.dto.response.AlliedEmpAddressResponse;
import com.cdac.centralvista.alliedAdmin.dto.response.AlliedEmpCompleteDetailsResponse;
import com.cdac.centralvista.alliedAdmin.dto.response.AlliedEmpDetailsResponse;
import com.cdac.centralvista.alliedAdmin.dto.response.AlliedEmpListPageResponse;
import com.cdac.centralvista.alliedAdmin.dto.response.AlliedEmpListResponse;
import com.cdac.centralvista.dto.pagable.request.PageRequest;
import com.cdac.centralvista.dto.request.AddressRequest;
import com.cdac.centralvista.dto.request.BiometricRequest;
import com.cdac.centralvista.dto.response.AddressResponse;
import com.cdac.centralvista.dto.response.ResponseMessage;
import com.cdac.centralvista.model.Address;
import com.cdac.centralvista.model.AddressType;
import com.cdac.centralvista.mp.request.AddLsMpServiceInfoRequest;
import com.cdac.centralvista.security.model.UserAuthenticationToken;

@RestController
@RequestMapping("/alliedAgencies")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AlliedController {

	@Autowired
	private AlliedEmployeeService alliedEmpService;
	
	@Autowired
	private AlliedEmpServiceInfoService empServiceInfoService;
	
	@PostMapping("/alliedSecurity/register/employee")
	public ResponseEntity<?> registerAlliedSecurityEmployee(@RequestBody RegisterAlliedEmpRequest request){
		UserAuthenticationToken userAuthenticationToken= (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		alliedEmpService.registerEmployee(request,"ALLD_SEC", userAuthenticationToken.getId());
		return ResponseEntity.ok(new ResponseMessage("1","Employee registered successfully"));
	}

	@PostMapping("/alliedGeneral/register/employee")
	public ResponseEntity<?> registerAlliedGeneralEmployee(@RequestBody RegisterAlliedEmpRequest request){
		UserAuthenticationToken userAuthenticationToken= (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		alliedEmpService.registerEmployee(request,"ALLD_GEN", userAuthenticationToken.getId());
		return ResponseEntity.ok(new ResponseMessage("1","Employee registered successfully"));
	}
	
	@PutMapping("/alliedSecurity/update/serviceInfo/{empUserId}")
	public ResponseEntity<?> addAlliedSecurityEmpServiceInfo(@RequestBody AddAlliedEmpServiceInfoRequest request, @PathVariable Integer empUserId){
		UserAuthenticationToken userAuthenticationToken= (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		empServiceInfoService.addServiceInfo(request, "ALLD_SEC", empUserId, userAuthenticationToken.getId());
		return ResponseEntity.ok(new ResponseMessage("1", "Service info updated successfully"));
	}
	
	@PutMapping("/alliedGeneral/update/serviceInfo/{empUserId}")
	public ResponseEntity<?> addAlliedGeneralEmpServiceInfo(@RequestBody AddAlliedEmpServiceInfoRequest request, @PathVariable Integer empUserId){
		UserAuthenticationToken userAuthenticationToken= (UserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		empServiceInfoService.addServiceInfo(request, "ALLD_GEN", empUserId, userAuthenticationToken.getId());
		return ResponseEntity.ok(new ResponseMessage("1", "Service info updated successfully"));
	}
	
	@PostMapping("/alliedSecurity/emp/page")
	public ResponseEntity<?> fetchAlliedSecurityEmpPage(@RequestBody PageRequest request){
		
		List<AlliedEmpListResponse> list = alliedEmpService.fetchEmpPageByAgencyType("ALLD_SEC",request)
															.stream()
															.map(AlliedEmpListResponse::new)
															.collect(Collectors.toList());
		
		return ResponseEntity.ok(new ResponseMessage("1",new AlliedEmpListPageResponse(list, 
														alliedEmpService.empCountByAgencyType("ALLD_SEC"))));
		
	}
	
	@PostMapping("/alliedGeneral/emp/page")
	public ResponseEntity<?> fetchAlliedGeneralEmpPage(@RequestBody PageRequest request){
		
		List<AlliedEmpListResponse> list = alliedEmpService.fetchEmpPageByAgencyType("ALLD_GEN",request)
															.stream()
															.map(AlliedEmpListResponse::new)
															.collect(Collectors.toList());
		
		return ResponseEntity.ok(new ResponseMessage("1",new AlliedEmpListPageResponse(list, 
														alliedEmpService.empCountByAgencyType("ALLD_GEN"))));
		
	}
	
	@GetMapping("/alliedSecurity/fetch/empDetails/{id}")
	public ResponseEntity<?> fetchAlliedSecurityEmpDetailsById(@PathVariable Integer id){
		return ResponseEntity.ok(new ResponseMessage("1", new AlliedEmpDetailsResponse(alliedEmpService.fetchAlliedEmpByIdAndAgencyTypeCode(id,"ALLD_SEC"))));
	}
	
	@GetMapping("/alliedGeneral/fetch/empDetails/{id}")
	public ResponseEntity<?> fetchAlliedGeneralEmpDetailsById(@PathVariable Integer id){
		return ResponseEntity.ok(new ResponseMessage("1", new AlliedEmpDetailsResponse(alliedEmpService.fetchAlliedEmpByIdAndAgencyTypeCode(id,"ALLD_GEN"))));
	}
	
	@PutMapping("/alliedSecurity/update/emp/{id}")
	public ResponseEntity<?> updateAlliedSecurityEmployee(@RequestBody UpdateAlliedEmpRequest request, @PathVariable Integer id){
		alliedEmpService.updateEmployee(request, id, "ALLD_SEC");
		return ResponseEntity.ok(new ResponseMessage("1","Employee details updated successfully"));
	}
	
	@PutMapping("/alliedGeneral/update/emp/{id}")
	public ResponseEntity<?> updateAlliedGeneralEmployee(@RequestBody UpdateAlliedEmpRequest request, @PathVariable Integer id){
		alliedEmpService.updateEmployee(request, id, "ALLD_GEN");
		return ResponseEntity.ok(new ResponseMessage("1","Employee details updated successfully"));
	}
	
	@PutMapping("/alliedSecurity/update/biometrics/{id}")
	public ResponseEntity<?> updateAlliedSecurityEmpBiometric(@RequestBody BiometricRequest request, @PathVariable Integer id){
		alliedEmpService.updateBiometrics(request, id, "ALLD_SEC");
		return ResponseEntity.ok(new ResponseMessage("1", "Biometrics Updated successfully"));
	}
	
	@PutMapping("/alliedGeneral/update/biometrics/{id}")
	public ResponseEntity<?> updateAlliedGeneralEmpBiometric(@RequestBody BiometricRequest request, @PathVariable Integer id){
		alliedEmpService.updateBiometrics(request, id, "ALLD_GEN");
		return ResponseEntity.ok(new ResponseMessage("1", "Biometrics Updated successfully"));
	}
	
	@PutMapping("/alliedSecurity/update/photo/{id}")
	public ResponseEntity<?> updateAlliedSecurityEmpPhoto(@PathVariable Integer id, @RequestBody HashMap<String,String> request){
		alliedEmpService.updatePhoto(request.get("photo"), id, "ALLD_SEC");
		return new ResponseEntity<>(new ResponseMessage("1","Photo updated successfully"), HttpStatus.OK);
	}
	
	@PutMapping("/alliedGeneral/update/photo/{id}")
	public ResponseEntity<?> updateAlliedGeneralEmpPhoto(@PathVariable Integer id, @RequestBody HashMap<String,String> request){
		alliedEmpService.updatePhoto(request.get("photo"), id, "ALLD_GEN");
		return new ResponseEntity<>(new ResponseMessage("1","Photo updated successfully"), HttpStatus.OK);
	}
	
	@GetMapping("/alliedSecurity/fetch/completeEmpDetails/{id}")
	public ResponseEntity<?> fetchAlliedSecurityEmpCompleteDetails(@PathVariable Integer id){
		AlliedEmpCompleteDetailsResponse response =  alliedEmpService.fetchEmpCompleteDetails(id, "ALLD_SEC");
		return  ResponseEntity.ok(new ResponseMessage("1",response));	
	}
	
	@GetMapping("/alliedGeneral/fetch/completeEmpDetails/{id}")
	public ResponseEntity<?> fetchAlliedGeneralEmpCompleteDetails(@PathVariable Integer id){
		AlliedEmpCompleteDetailsResponse response =  alliedEmpService.fetchEmpCompleteDetails(id, "ALLD_GEN");
		return  ResponseEntity.ok(new ResponseMessage("1",response));	
	}
	
	@GetMapping("/alliedSecurity/fetch/address/{id}")
	public ResponseEntity<?> fetchAlliedSecurityEmpAddress(@PathVariable Integer id){
		
		List<Address> address = alliedEmpService.fetchAddressByEmpIdAndAgencyTypeCode(id, "ALLD_SEC");
		
		List<AddressResponse> presentAddress = address.stream()
													  .filter((addr) -> addr.getAddressType() == AddressType.CURRENT_ADDRESS || 
													  					addr.getAddressType() == AddressType.CURRENT_ADDRESS_OLD)
													  .map(AddressResponse::new)
													  .collect(Collectors.toList());
		
		List<AddressResponse> permanentAddress = address.stream()
				  										.filter((addr) -> addr.getAddressType() == AddressType.PERMANENT_ADDRESS || 
				  															addr.getAddressType() == AddressType.PERMANENT_ADDRESS_OLD)
				  										.map(AddressResponse::new)
				  										.collect(Collectors.toList());
		
		return ResponseEntity.ok(new AlliedEmpAddressResponse(presentAddress, permanentAddress));
	}
	
	@GetMapping("/alliedGeneral/fetch/address/{id}")
	public ResponseEntity<?> fetchAlliedGeneralEmpAddress(@PathVariable Integer id){
		
		List<Address> address = alliedEmpService.fetchAddressByEmpIdAndAgencyTypeCode(id, "ALLD_GEN");
		
		List<AddressResponse> presentAddress = address.stream()
													  .filter((addr) -> addr.getAddressType() == AddressType.CURRENT_ADDRESS || 
													  					addr.getAddressType() == AddressType.CURRENT_ADDRESS_OLD)
													  .map(AddressResponse::new)
													  .collect(Collectors.toList());
		
		List<AddressResponse> permanentAddress = address.stream()
				  										.filter((addr) -> addr.getAddressType() == AddressType.PERMANENT_ADDRESS || 
				  															addr.getAddressType() == AddressType.PERMANENT_ADDRESS_OLD)
				  										.map(AddressResponse::new)
				  										.collect(Collectors.toList());
		
		return ResponseEntity.ok(new AlliedEmpAddressResponse(presentAddress, permanentAddress));
	}
	
	@PutMapping("/alliedSecurity/add/address/{id}")
	public ResponseEntity<?> addAlliedSecurityEmpAddress(@RequestBody AddressRequest request,@PathVariable Integer id){
		alliedEmpService.addAddress(request, id, "ALLD_SEC");
		return ResponseEntity.ok(new ResponseMessage("1", "Address added successfully"));
	}
	
	@PutMapping("/alliedGeneral/add/address/{id}")
	public ResponseEntity<?> addAlliedGeneralEmpAddress(@RequestBody AddressRequest request,@PathVariable Integer id){
		alliedEmpService.addAddress(request, id, "ALLD_GEN");
		return ResponseEntity.ok(new ResponseMessage("1", "Address added successfully"));
	}
	
	@GetMapping("/alliedSecurity/dashboard")
	public ResponseEntity<?> alliedSecurityDashboard(){
		
		Integer totalEmpCount = alliedEmpService.empCountByAgencyType("ALLD_SEC");
		Integer empBiometricsCapturedCount = alliedEmpService.fetchEmpBiometricsCapturedCountByAgenctTypeCode("ALLD_SEC");
		Integer empPhotoCapturedCount = alliedEmpService.fetchEmpPhotoCapturedCountByAgencyTypeCode("ALLD_SEC");
		
		return ResponseEntity.ok(new AlliedAdminDashboardResponse(totalEmpCount, empBiometricsCapturedCount, empPhotoCapturedCount));
	}
	
	@GetMapping("/alliedGeneral/dashboard")
	public ResponseEntity<?> alliedGeneralDashboard(){
		
		Integer totalEmpCount = alliedEmpService.empCountByAgencyType("ALLD_GEN");
		Integer empBiometricsCapturedCount = alliedEmpService.fetchEmpBiometricsCapturedCountByAgenctTypeCode("ALLD_GEN");
		Integer empPhotoCapturedCount = alliedEmpService.fetchEmpPhotoCapturedCountByAgencyTypeCode("ALLD_GEN");
		
		return ResponseEntity.ok(new AlliedAdminDashboardResponse(totalEmpCount, empBiometricsCapturedCount, empPhotoCapturedCount));
	}
}
