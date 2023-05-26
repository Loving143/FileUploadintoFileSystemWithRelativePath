package com.cdac.centralvista.allied.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.centralvista.allied.service.AgencyService;
import com.cdac.centralvista.allied.service.AgencyTypeService;
import com.cdac.centralvista.allied.service.AlliedDesignationService;
import com.cdac.centralvista.alliedAdmin.dto.request.AddAgencyRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.AddDesignationRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.UpdateAgencyRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.UpdateDesignationRequest;
import com.cdac.centralvista.alliedAdmin.dto.response.AgencyListResponse;
import com.cdac.centralvista.alliedAdmin.dto.response.AgencyResponse;
import com.cdac.centralvista.alliedAdmin.dto.response.AgencyTypeResponse;
import com.cdac.centralvista.alliedAdmin.dto.response.AlliedDesignationListResponse;
import com.cdac.centralvista.alliedAdmin.dto.response.AlliedDesignationResponse;
import com.cdac.centralvista.dto.response.ResponseMessage;

@RestController
@RequestMapping("/alliedAgences/mstrs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AlliedMastersControllers {

	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private AgencyTypeService agencyTypeService;
	
	@Autowired
	private AlliedDesignationService alliedDesignationService;
	
	@GetMapping("/fetch/agencyTypes")
	public ResponseEntity<?> fetchAllAgencyTypes(){
		
		List<AgencyTypeResponse> list = agencyTypeService.fetchAll()
				 										.stream()
				 										.map(AgencyTypeResponse::new)
				 										.collect(Collectors.toList());
		
		return ResponseEntity.ok(new ResponseMessage("1",list));
	}
	
	@GetMapping("/fetch/agencies/{agencyTypeId}")
	public ResponseEntity<?> fetchAgencyByAgencyTypeId(@PathVariable Integer agencyTypeId){
		
		List<AgencyResponse> list = agencyService.findAllByAgencyTypeId(agencyTypeId)
												 .stream()
												 .map(AgencyResponse::new)
												 .collect(Collectors.toList());
		
		return ResponseEntity.ok(new ResponseMessage("1",list));
	}
	
	@GetMapping("/alliedSecurity/fetch/agencies")
	public ResponseEntity<?> fetchAlliedSecurityAgencies(){
		
		List<AgencyResponse> list = agencyService.findAllByAgencyTypeCode("ALLD_SEC")
				 									.stream()
				 									.map(AgencyResponse::new)
				 									.collect(Collectors.toList());

		return ResponseEntity.ok(new ResponseMessage("1",list));
	}
	
	
	@GetMapping("/alliedGeneral/fetch/agencies")
	public ResponseEntity<?> fetchAlliedGeneralAgencies(){
		
		List<AgencyResponse> list = agencyService.findAllByAgencyTypeCode("ALLD_GEN")
				 									.stream()
				 									.map(AgencyResponse::new)
				 									.collect(Collectors.toList());

		return ResponseEntity.ok(new ResponseMessage("1",list));
	}
	
	@GetMapping("/fetch/agencies/all")
	public ResponseEntity<?> fetchAllAgencies(){
		
		List<AgencyListResponse> list = agencyService.fetchAllAgencies()
													 .stream()
													 .map(AgencyListResponse::new)
													 .collect(Collectors.toList());
		
		return ResponseEntity.ok(new ResponseMessage("1",list));
	}
	
	@GetMapping("/fetch/agency/{id}")
	public ResponseEntity<?> fetchAgencyById(@PathVariable Integer id){
		return ResponseEntity.ok(new ResponseMessage("1", new AgencyResponse(agencyService.fetchById(id))));
		
	}
	
	@PostMapping("/add/agency")
	public ResponseEntity<?> addAgency(@RequestBody AddAgencyRequest request){
		agencyService.addAgency(request);
		return ResponseEntity.ok(new ResponseMessage("1","Agency added successfully"));
	}
	
	@PutMapping("/update/agency/{id}")
	public ResponseEntity<?> updateAgency(@RequestBody UpdateAgencyRequest request,@PathVariable Integer id){
		agencyService.updateAgency(request, id);
		return ResponseEntity.ok(new ResponseMessage("1", "Agency updated successfully"));
	}
	
	@PostMapping("/add/designation")
	public ResponseEntity<?> addDesignation(@RequestBody AddDesignationRequest request){
		alliedDesignationService.addDesignation(request);
		return ResponseEntity.ok(new ResponseMessage("1","Allied Designation added successfully"));
	}
	
	@GetMapping("/fetch/designation/{id}")
	public ResponseEntity<?> fetchDesignationById(@PathVariable Integer id){
		return ResponseEntity.ok(new ResponseMessage("1", new AlliedDesignationResponse(alliedDesignationService.fetchById(id))));
	}
	
	@GetMapping("/fetch/designations/{agencyId}")
	public ResponseEntity<?> fetchDesignationsByAgencyId(@PathVariable Integer agencyId){
		
		List<AlliedDesignationResponse> list = alliedDesignationService.fetchAllByAgencyId(agencyId)
																		.stream()
																		.map(AlliedDesignationResponse::new)
																		.collect(Collectors.toList());
		
		return ResponseEntity.ok(new ResponseMessage("1",list));
	}
	
	@GetMapping("/fetch/designations/all")
	public ResponseEntity<?> fetchAllDesignations(){
		
		List<AlliedDesignationListResponse> list = alliedDesignationService.fetchAllDesignations()
																		.stream()
																		.map(AlliedDesignationListResponse::new)
																		.collect(Collectors.toList());
		
		return ResponseEntity.ok(new ResponseMessage("1",list));
	}
	
	@PutMapping("/update/designation/{id}")
	public ResponseEntity<?> updateDesignation(@RequestBody UpdateDesignationRequest request,@PathVariable Integer id){
		alliedDesignationService.updateDesignation(request, id);
		return ResponseEntity.ok(new ResponseMessage("1", "Designation updated successfully"));
	}
	
}
