package com.cdac.centralvista.allied.service;

import java.util.List;

import com.cdac.centralvista.allied.model.AgencyType;

public interface AgencyTypeService {

	AgencyType fetchById(Integer id);

	List<AgencyType> fetchAll();

	AgencyType fetchByAgencyTypeCode(String agencyTypeCode);

}
