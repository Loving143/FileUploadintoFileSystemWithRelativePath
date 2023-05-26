package com.cdac.centralvista.allied.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.centralvista.allied.model.Agency;
import com.cdac.centralvista.allied.model.AlliedDesignation;
import com.cdac.centralvista.allied.model.AlliedEmpServiceInfo;
import com.cdac.centralvista.allied.repository.AgencyRepository;
import com.cdac.centralvista.allied.repository.AlliedDesignationRepository;
import com.cdac.centralvista.allied.repository.AlliedEmpServiceInfoRepository;
import com.cdac.centralvista.alliedAdmin.dto.request.AddAlliedEmpServiceInfoRequest;
import com.cdac.centralvista.model.Category;
import com.cdac.centralvista.model.User;
import com.cdac.centralvista.repository.CategoryRepository;
import com.cdac.centralvista.repository.UserRepository;

@Service
public class AlliedEmpServiceInfoServiceImpl implements AlliedEmpServiceInfoService {
	
	@Autowired
	private AlliedEmpServiceInfoRepository serviceInfoRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AgencyRepository agencyRepo;
	
	@Autowired
	private AlliedDesignationRepository designationRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	@Transactional
	public void addServiceInfo(AddAlliedEmpServiceInfoRequest request, String agencyTypeCode, Integer empUserId, Integer creatorUserId) {
		
		AlliedEmpServiceInfo currentServiceInfo = serviceInfoRepo.fetchActiveServiceInfoByEmpUserIdAndAgencyType(empUserId, agencyTypeCode);
		
		AlliedEmpServiceInfo archivedServiceInfo = currentServiceInfo.archive();
		
		User actor = userRepo.getReferenceById(creatorUserId);
		
		Agency agency;
		try {
			agency = agencyRepo.getReferenceById(request.getAgencyId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Invalid agency id");
		}
		
		AlliedDesignation designation;
		try {
			designation = designationRepo.getReferenceById(request.getDesignationId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Invalid designation id");
		}
		
		Category category;
		try {
			category = categoryRepo.getReferenceById(request.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Invalid category id");
		}
		
		currentServiceInfo.setAgency(agency);
		currentServiceInfo.setBattalion(request.getBattalion());
		currentServiceInfo.setCompany(request.getCompany());
		currentServiceInfo.setDesignation(designation);
		currentServiceInfo.setCategory(category);
		currentServiceInfo.setServiceStartDate(request.getServiceStartDate());
		currentServiceInfo.setServiceEndDate(request.getServiceEndDate());
		currentServiceInfo.setCreatedBy(actor);
		currentServiceInfo.setCreationDate(new Date());
		currentServiceInfo.setUpdationDate(new Date());
		
		serviceInfoRepo.save(archivedServiceInfo);
	}
}
