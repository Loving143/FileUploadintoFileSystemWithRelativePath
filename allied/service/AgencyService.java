package com.cdac.centralvista.allied.service;

import java.util.List;

import com.cdac.centralvista.allied.model.Agency;
import com.cdac.centralvista.alliedAdmin.dto.request.AddAgencyRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.UpdateAgencyRequest;

public interface AgencyService {

	Agency fetchById(Integer id);

	List<Agency> findAllByAgencyTypeId(Integer agencyTypeId);

	void addAgency(AddAgencyRequest request);

	List<Agency> fetchAllAgencies();

	List<Agency> findAllByAgencyTypeCode(String agencyTypeCode);

	void updateAgency(UpdateAgencyRequest request, Integer id);
	

}
