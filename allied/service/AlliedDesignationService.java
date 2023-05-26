package com.cdac.centralvista.allied.service;

import java.util.List;

import com.cdac.centralvista.allied.model.AlliedDesignation;
import com.cdac.centralvista.alliedAdmin.dto.request.AddDesignationRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.UpdateDesignationRequest;

public interface AlliedDesignationService {

	AlliedDesignation fetchById(Integer id);

	List<AlliedDesignation> fetchAllByAgencyId(Integer agencyId);

	void addDesignation(AddDesignationRequest request);

	List<AlliedDesignation> fetchAllDesignations();

	void updateDesignation(UpdateDesignationRequest request, Integer id);

}
