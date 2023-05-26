package com.cdac.centralvista.allied.service;

import com.cdac.centralvista.alliedAdmin.dto.request.AddAlliedEmpServiceInfoRequest;

public interface AlliedEmpServiceInfoService {

	void addServiceInfo(AddAlliedEmpServiceInfoRequest request, String agencyTypeCode, Integer empUserId,
			Integer creatorUserId);

}
