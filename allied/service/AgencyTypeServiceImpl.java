package com.cdac.centralvista.allied.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.centralvista.allied.model.AgencyType;
import com.cdac.centralvista.allied.repository.AgencyTypeRepository;

@Service
public class AgencyTypeServiceImpl implements AgencyTypeService {

	@Autowired
	private AgencyTypeRepository agencyTypeRepo;
	
	@Override
	public AgencyType fetchById(Integer id) {
		return agencyTypeRepo.findById(id).orElseThrow(() -> new RuntimeException("Invalid agency type id"));
		
	}
	
	@Override
	public AgencyType fetchByAgencyTypeCode(String agencyTypeCode) {
		return agencyTypeRepo.findByAgencyTypeCode(agencyTypeCode).orElseThrow(() -> new RuntimeException("Invalid agency type code"));
	}
	
	@Override
	public List<AgencyType> fetchAll(){
		
		List<AgencyType> types;
		try {
			types = agencyTypeRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to fetch agency types. Internal server error");
		}
		
		if(types.size() == 0)
			throw new RuntimeException("No data found");
		
		return types;
	}
}
