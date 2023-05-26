package com.cdac.centralvista.allied.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.centralvista.allied.model.AgencyType;
import com.cdac.centralvista.allied.model.AlliedEmpServiceInfo;
import com.cdac.centralvista.allied.model.AlliedEmployee;
import com.cdac.centralvista.allied.repository.AlliedEmpServiceInfoRepository;
import com.cdac.centralvista.allied.repository.AlliedEmployeeRepository;
import com.cdac.centralvista.alliedAdmin.dto.AlliedEmpListDto;
import com.cdac.centralvista.alliedAdmin.dto.request.AddAlliedEmpServiceInfoRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.RegisterAlliedEmpRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.UpdateAlliedEmpRequest;
import com.cdac.centralvista.alliedAdmin.dto.response.AlliedEmpCompleteDetailsResponse;
import com.cdac.centralvista.alliedAdmin.dto.response.AlliedEmpServiceInfoResponse;
import com.cdac.centralvista.dto.pagable.request.Order;
import com.cdac.centralvista.dto.pagable.request.PageRequest;
import com.cdac.centralvista.dto.request.AddressRequest;
import com.cdac.centralvista.dto.request.BiometricRequest;
import com.cdac.centralvista.employee.model.Employee;
import com.cdac.centralvista.model.Address;
import com.cdac.centralvista.model.FingerData;
import com.cdac.centralvista.model.User;
import com.cdac.centralvista.repository.UserRepository;
import com.cdac.centralvista.service.CategoryService;

@Service
public class AlliedEmployeeServiceImpl implements AlliedEmployeeService {

	@Autowired
	private AlliedEmployeeRepository empRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AlliedEmpServiceInfoService serviceInfoService;
	
	@Autowired
	private AlliedEmpServiceInfoRepository serviceInfoRepo;
	
	@Autowired
	private AgencyTypeService agencyTypeService;
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private AlliedDesignationService designationService;
	
	@Autowired
	private CategoryService categoryServices;
	
	@Override
	@Transactional
	public void registerEmployee(RegisterAlliedEmpRequest request, String agencyTypeCode, Integer creatorUserId) {
		
		validateRegisterAlliedEmpRequest(request);
		
		AlliedEmployee newEmp = new AlliedEmployee(request.getEmail(), new SimpleDateFormat("dd/MM/yyyy").format(request.getDob()) ,
													request.getContactNumber(), request.getEmail(), null, true, false, false, false ,
													request.getOrginationEmpId(), request.getFullName(), request.getFatherName(), request.getDob(),
													request.getGender(),request.getBloodGroup(), request.getOtherContactNumber(),
													request.getIdentificationMark(), request.getOfficeContactNumber(), request.getFaxNumber(), 
													request.getAadhaarNumber(),request.getRemark());
		
		User actor = userRepo.getReferenceById(creatorUserId);
		
		newEmp.getAddress().add(new Address(request.getCurrentAddress()));
		newEmp.getAddress().add(new Address(request.getPermanentAddress()));
		newEmp.setCreatedBy(actor);
		
		
		AlliedEmpServiceInfo serviceInfo = new AlliedEmpServiceInfo(newEmp, agencyTypeService.fetchByAgencyTypeCode(agencyTypeCode),
																	agencyService.fetchById(request.getAgencyId()), request.getBattalion(), request.getCompany(),
																	designationService.fetchById(request.getDesignationId()), categoryServices.getCategory(request.getCategoryId()),
																	request.getServiceStartDate(), request.getServiceEndDate(), true, actor);
		
		empRepo.save(newEmp);
		
	}
	
	
	@Override
	public void updateEmployee(UpdateAlliedEmpRequest request, Integer id, String agencyTypeCode) {
		
		validateUpdateAlliedEmpRequest(request, id);
		
		AlliedEmployee emp = fetchAlliedEmpByIdAndAgencyTypeCode(id, agencyTypeCode);
		
		emp.setEmpId(request.getEmail());
		emp.setOrginationEmpId(request.getOrginationEmpId());
		emp.setContactNumber(request.getContactNumber());
		emp.setEmail(request.getEmail());
		emp.setFullName(request.getFullName());
		emp.setFatherName(request.getFatherName());
		emp.setDob(request.getDob());
		emp.setGender(request.getGender());
		emp.setBloodGroup(request.getBloodGroup());
		emp.setOtherContactNumber(request.getOtherContactNumber());
		emp.setFaxNumber(request.getFaxNumber());
		emp.setOfficeContactNumber(request.getOfficeContactNumber());
		emp.setIdentificationMark(request.getIdentificationMark());
		emp.setAadhaarNumber(request.getAadhaarNumber());
		emp.setRemark(request.getRemark());
		emp.setUpdationDate(new Date());
		
		if(emp.isCredentialsExpired())
			emp.setPassword( new SimpleDateFormat("dd/MM/yyyy").format(request.getDob()));
		
		AlliedEmpServiceInfo serviceInfo = emp.getServiceInfo();
		
		serviceInfo.setAgencyType(agencyTypeService.fetchByAgencyTypeCode(agencyTypeCode));
		serviceInfo.setAgency(agencyService.fetchById(request.getAgencyId()));
		serviceInfo.setBattalion(request.getBattalion());
		serviceInfo.setCompany(request.getCompany());
		serviceInfo.setDesignation(designationService.fetchById(request.getDesignationId()));
		serviceInfo.setCategory(categoryServices.getCategory(request.getCategoryId()));
		serviceInfo.setServiceStartDate(request.getServiceStartDate());
		serviceInfo.setServiceEndDate(request.getServiceEndDate());
		serviceInfo.setUpdationDate(new Date());
		
		empRepo.save(emp);
		
	}
	
	@Transactional
	@Override
	public void updateBiometrics(BiometricRequest request, Integer id, String agencyTypeCode) {
		AlliedEmployee emp = fetchAlliedEmpByIdAndAgencyTypeCode(id, agencyTypeCode);
		
		if(request.getFingerPrint1() != null) {
			emp.setFingerData1(new FingerData(request.getFingerPrint1()));
			emp.setFingerDataCaptured(true);
		}

		if(request.getFingerPrint2() != null) {
			emp.setFingerData2(new FingerData(request.getFingerPrint2()));
			emp.setFingerDataCaptured(true);
		}
		
		empRepo.save(emp);
	}
	
	@Override
	@Transactional
	public void updatePhoto(String base64Photo, Integer id, String agencyTypeCode) {

		AlliedEmployee emp = fetchAlliedEmpByIdAndAgencyTypeCode(id, agencyTypeCode);

		emp.setPhoto(Base64.getDecoder().decode(base64Photo));
		emp.setPhotoCaptured(true);

		empRepo.save(emp);
	}
	
	@Override
	public AlliedEmployee fetchAlliedEmpByIdAndAgencyTypeCode(Integer id, String agencyTypeCode) {
		
		return empRepo.fetchEmpDetailsByIdAndAgencyTypeCode(id,agencyTypeCode)
						.orElseThrow(() -> {
							String agencyTypeName = agencyTypeService.fetchByAgencyTypeCode(agencyTypeCode).getAgencyTypeName();
							return new RuntimeException("No employee exists with the given id in " + agencyTypeName );
						});
	}
	
	private void validateRegisterAlliedEmpRequest(RegisterAlliedEmpRequest request) {
		
		if(empRepo.existsByEmailIgnoreCase(request.getEmail()) || empRepo.existsByEmpIdIgnoreCase(request.getEmail()))
			throw new RuntimeException("Email id already exists");
		
		if(empRepo.existsByAadhaarNumber(request.getAadhaarNumber()))
			throw new RuntimeException("Aadhaar number already exists");
		
		if(empRepo.existsByContactNumber(request.getContactNumber()))
			throw new RuntimeException("Contact number already exists");
		
		if(empRepo.existsByOrginationEmpIdIgnoreCaseAndServiceInfoAgencyId(request.getOrginationEmpId(), request.getAgencyId()))
			throw new RuntimeException("Employee id already exists for the given agency");

	}
	
	private void validateUpdateAlliedEmpRequest(UpdateAlliedEmpRequest request, Integer id) {
		if(empRepo.existsByEmailIgnoreCaseAndIdNot(request.getEmail(),id) || empRepo.existsByEmpIdIgnoreCaseAndIdNot(request.getEmail(), id))
			throw new RuntimeException("Email id already exists");
		
		if(empRepo.existsByAadhaarNumberAndIdNot(request.getAadhaarNumber(),id))
			throw new RuntimeException("Aadhaar number already exists");
		
		if(empRepo.existsByContactNumberAndIdNot(request.getContactNumber(),id))
			throw new RuntimeException("Contact number already exists");
		
		if(empRepo.existsByOrginationEmpIdIgnoreCaseAndIdNotAndServiceInfoAgencyId(request.getOrginationEmpId(), id, request.getAgencyId()))
			throw new RuntimeException("Employee id already exists for the given agency");
	}
	
	@Override
	public List<AlliedEmpListDto> fetchEmpPageByAgencyType(String agencyType, PageRequest request){
		
		List<Sort.Order> orders = request.getOrder()
										.stream()
										.map(this::buildSortOrder)
										.collect(Collectors.toList());
		
		int pageNumber = request.getStart()/request.getLength();
		
		Pageable pagable = org.springframework.data.domain.PageRequest.of(pageNumber, request.getLength(), Sort.by(orders));
		
		return empRepo.fetchEmpListPage(agencyType, request.getSearch().getValue(),pagable);
	}
	
	@Override
	public AlliedEmpCompleteDetailsResponse fetchEmpCompleteDetails(Integer id, String agencyTypeCode) {
		
		List<AlliedEmpServiceInfo> servInfo = serviceInfoRepo.fetchAllServiceInfoByEmpIdAndAgencyType(id, agencyTypeCode);
		
		if(servInfo.size() == 0)
			throw new RuntimeException("No data found");
		
		Map<AlliedEmployee, List<AlliedEmpServiceInfo>> empServInfoMap = servInfo.stream()
																			.collect(Collectors.groupingBy(AlliedEmpServiceInfo::getEmployeeInfo));
		
		if(empServInfoMap.entrySet().size() > 1)
			throw new RuntimeException("Internal server error");
		
		return empServInfoMap.entrySet().stream()
								.map((entry) -> new AlliedEmpCompleteDetailsResponse(entry.getKey(), entry.getValue()))
								.findFirst()
								.orElseThrow(() -> new RuntimeException("No data found"));
		
		
	}
	
	@Override
	public Integer fetchEmpBiometricsCapturedCountByAgenctTypeCode(String agencyTypeCode) {
		
		return empRepo.empBiometricCapturedCountByAgencyTypeCode(agencyTypeCode)
				.orElseThrow(() -> new RuntimeException("Unable to fetch biometrics captured count. Internal server error"));

	}
	
	@Override
	public Integer fetchEmpPhotoCapturedCountByAgencyTypeCode(String agencyTypeCode) {
		
		return empRepo.empPhotoCapturedCountByAgencyTypeCode(agencyTypeCode)
				.orElseThrow(() -> new RuntimeException("Unable to fetch biometrics captured count. Internal server error.") );
		
	}
	
	@Override
	public List<Address> fetchAddressByEmpIdAndAgencyTypeCode(Integer id, String agencyTypeCode){
		
		List<Address> addressList;
		try {
			addressList = empRepo.fetchAddressByEmpIdAndAgencyTypeCode(id, agencyTypeCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to fetch address. Internal server error");
		}
		
		if(addressList.size() == 0)
			throw new RuntimeException("No data found");
		
		return addressList;
	}
	
	@Override
	public void addAddress(AddressRequest request, Integer id, String agencyTypeCode) {
		
		AlliedEmployee emp = fetchAlliedEmpByIdAndAgencyTypeCode(id,agencyTypeCode);
		
		Address newAddress = new Address(request);
		
		emp.addAddress(newAddress);
		
		empRepo.save(emp);
	}
	
	@Override
	public Integer empCountByAgencyType(String agencyTypeCode) {
		
		return empRepo.totalEmpCountByAgencyTypeCode(agencyTypeCode)
				.orElseThrow(() -> new RuntimeException("Unable to fetch total allied employee count, Internal server error"));

	}
	
	private Sort.Order buildSortOrder(Order order) {
		
		int columnIndx = order.getColumn();
		Sort.Order sortOrder ;
		switch(columnIndx){
			case 0 : sortOrder = new Sort.Order(getDirection(order.getDir()), "orginationEmpId");
					break;
					
			case 1 : sortOrder = new Sort.Order(getDirection(order.getDir()), "fullName");
					break;
					
			case 4 : sortOrder = new Sort.Order(getDirection(order.getDir()), "agencyCode");
					break;
					
			default : sortOrder = new Sort.Order(getDirection("asc"), "fullName");
					break;
		}
		
		return sortOrder;
		
	}
	
	private Direction getDirection(String dir) {
		if(dir.equalsIgnoreCase("asc"))
			return Direction.ASC;
		else if(dir.equalsIgnoreCase("desc"))
			return Direction.DESC;
		else 
			return Sort.DEFAULT_DIRECTION;
	}
}
