package com.cdac.centralvista.allied.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.centralvista.allied.model.Agency;
import com.cdac.centralvista.allied.model.AlliedDesignation;
import com.cdac.centralvista.allied.repository.AlliedDesignationRepository;
import com.cdac.centralvista.alliedAdmin.dto.request.AddDesignationRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.UpdateDesignationRequest;

@Service
public class AlliedDesignationServiceImpl implements AlliedDesignationService {

	@Autowired
	private AlliedDesignationRepository designationRepo;
	
	@Autowired
	private AgencyService agencyService;
	
	@Override
	public AlliedDesignation fetchById(Integer id) {
		return designationRepo.findById(id).orElseThrow(() -> new RuntimeException("Invalid allied designation id"));
	}
	
	@Override
	public List<AlliedDesignation> fetchAllByAgencyId(Integer agencyId){
		List<AlliedDesignation> designations;
		
		try {
			designations = designationRepo.fetchAllByAgencyId(agencyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to fetch allied designations, Internal server error");
		}
		
		if(designations.size() == 0)
			throw new RuntimeException("No allied designations found");
		
		return designations;
	}
	
	@Override
	public List<AlliedDesignation> fetchAllDesignations(){
	
		List<AlliedDesignation> designations;
		
		try {
			designations = designationRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to fetch allied designations, Internal server error");
		}
		
		if(designations.size() == 0)
			throw new RuntimeException("No allied designations found");
		
		return designations;
	}
	
	@Override
	public void addDesignation(AddDesignationRequest request) {
		
		validateAddDesignationRequest(request);
		
		Agency agency = agencyService.fetchById(request.getAgencyId());
		
		save(new AlliedDesignation(request.getDesignationCode(), request.getDesignationName() , agency));
	}
	
	@Override
	public void updateDesignation(UpdateDesignationRequest request, Integer id) {
		
		validateUpdateDesignationRequest(request,id);
		
		AlliedDesignation designation = fetchById(id);
		
		designation.setDesignationCode(request.getDesignationCode());
		designation.setDesignationName(request.getDesignationName());
		designation.setAgency(agencyService.fetchById(request.getAgencyId()));
		
		save(designation);
	}
	
	private void validateAddDesignationRequest(AddDesignationRequest request) {
		
		if(designationRepo.existsByDesignationCodeIgnoreCase(request.getDesignationCode()))
			throw new RuntimeException("Designation  code already exists");
		
		if(designationRepo.existsByDesignationNameIgnoreCaseAndAgencyId(request.getDesignationName(), request.getAgencyId()))
			throw new RuntimeException("Designation name already exists");
		
	}
	
	private void validateUpdateDesignationRequest(UpdateDesignationRequest request, Integer id) {
		
		if(designationRepo.existsByDesignationCodeIgnoreCaseAndIdNot(request.getDesignationCode(), id))
			throw new RuntimeException("Designation  code already exists");
		
		if(designationRepo.existsByDesignationNameIgnoreCaseAndAgencyIdAndIdNot(request.getDesignationName(), request.getAgencyId(), id))
			throw new RuntimeException("Designation name already exists");
	
	}
	
//	
//	private void validateAddDesignationRequest(AddDesignationRequest request) {
//		
//		if(designationRepo.existsByDesignationCodeIgnoreCaseAndAgencyId(request.getDesignationCode(), request.getAgencyId()))
//			throw new RuntimeException("Designation  code already exists");
//		
//		if(designationRepo.existsByDesignationNameIgnoreCaseAndAgencyId(request.getDesignationName(), request.getAgencyId()))
//			throw new RuntimeException("Designation name already exists");
//	}
//	
//	private void validateUpdateDesignationRequest(UpdateDesignationRequest request, Integer id) {
//		
//		if(designationRepo.existsByDesignationCodeIgnoreCaseAndAgencyIdAndIdNot(request.getDesignationCode(), request.getAgencyId(), id))
//			throw new RuntimeException("Designation  code already exists");
//		
//		if(designationRepo.existsByDesignationNameIgnoreCaseAndAgencyIdAndIdNot(request.getDesignationName(), request.getAgencyId(), id))
//			throw new RuntimeException("Designation name already exists");
//	}
	
	private AlliedDesignation save(AlliedDesignation designation) {
		
		try {
			return designationRepo.save(designation);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to save allied designation. Internal server error");
		}
	}
}
