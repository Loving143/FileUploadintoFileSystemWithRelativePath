package com.cdac.centralvista.allied.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.centralvista.allied.model.Agency;
import com.cdac.centralvista.allied.model.AgencyType;
import com.cdac.centralvista.allied.repository.AgencyRepository;
import com.cdac.centralvista.alliedAdmin.dto.request.AddAgencyRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.UpdateAgencyRequest;

@Service
public class AgencyServiceImpl implements AgencyService {

	@Autowired
	private AgencyRepository agencyRepo;
	
	@Autowired
	private AgencyTypeService agencyTypeService;
	
	@Override
	public Agency fetchById(Integer id) {
		
		return agencyRepo.findById(id).orElseThrow(() -> new RuntimeException("invalid agency id"));
	}
	
	
	
	@Override
	public List<Agency> findAllByAgencyTypeId(Integer agencyTypeId){
		
		List<Agency> agencies;
		
		try {
			agencies = agencyRepo.findAllByAgencyTypeId(agencyTypeId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to fetch agencies. Internal server error");
		}
		
		if(agencies.size() == 0)
			throw new RuntimeException("No agencies found");
		
		return agencies;
	}
	
	@Override
	public List<Agency> findAllByAgencyTypeCode(String agencyTypeCode){
		
		List<Agency> agencies;
		
		try {
			agencies = agencyRepo.fetchByAgencyTypeCode(agencyTypeCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to fetch agencies. Internal server error");
		}
		
		if(agencies.size() == 0)
			throw new RuntimeException("No agencies found");
		
		return agencies;
	}
	
	@Override
	public List<Agency> fetchAllAgencies(){
		
		List<Agency> agencies;
		
		try {
			agencies = agencyRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to fetch agencies. Internal server error");
		}
		
		if(agencies.size() == 0)
			throw new RuntimeException("No agencies found");
		
		return agencies;
	}
	
	@Override
	public void addAgency(AddAgencyRequest request) {
		
		validateAddAgencyRequest(request);
		
		AgencyType type = agencyTypeService.fetchById(request.getAgencyTypeId());
		
		save(new Agency(request.getAgencyCode(),request.getAgencyName(),type));
	}
	
	@Override
	public void updateAgency(UpdateAgencyRequest request, Integer id) {
		
		validateUpdateAgencyRequest(request,id);
		
		Agency agency = fetchById(id);
		
		agency.setAgencyCode(request.getAgencyCode());
		agency.setAgencyName(request.getAgencyName());
		agency.setAgencyType(agencyTypeService.fetchById(request.getAgencyTypeId()));
		
		save(agency);
		
	}
	
	private void validateUpdateAgencyRequest(UpdateAgencyRequest request, Integer id) {
		
		if(agencyRepo.existsByAgencyCodeIgnoreCaseAndIdNot(request.getAgencyCode(), id))
			throw new RuntimeException("Agency code already exists");
		
		if(agencyRepo.existsByAgencyNameIgnoreCaseAndIdNot(request.getAgencyName(), id))
			throw new RuntimeException("Agency name already exists");
	}
	
	private void validateAddAgencyRequest(AddAgencyRequest request) {
		
		if(agencyRepo.existsByAgencyCodeIgnoreCase(request.getAgencyCode()))
			throw new RuntimeException("Agency code already exists");
		
		if(agencyRepo.existsByAgencyNameIgnoreCase(request.getAgencyName()))
			throw new RuntimeException("Agency name already exists");
	}
	
	private Agency save(Agency agency) {
		
		try {
			return agencyRepo.save(agency);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to save agency. Internal server error");
		}
	}
}
