package com.cdac.centralvista.allied.service;

import java.util.List;

import com.cdac.centralvista.allied.model.AlliedEmployee;
import com.cdac.centralvista.alliedAdmin.dto.AlliedEmpListDto;
import com.cdac.centralvista.alliedAdmin.dto.request.RegisterAlliedEmpRequest;
import com.cdac.centralvista.alliedAdmin.dto.request.UpdateAlliedEmpRequest;
import com.cdac.centralvista.alliedAdmin.dto.response.AlliedEmpCompleteDetailsResponse;
import com.cdac.centralvista.dto.pagable.request.PageRequest;
import com.cdac.centralvista.dto.request.AddressRequest;
import com.cdac.centralvista.dto.request.BiometricRequest;
import com.cdac.centralvista.model.Address;

public interface AlliedEmployeeService {

	List<AlliedEmpListDto> fetchEmpPageByAgencyType(String agencyType, PageRequest request);

	Integer empCountByAgencyType(String agencyTypeCode);

	AlliedEmployee fetchAlliedEmpByIdAndAgencyTypeCode(Integer id, String agencyTypeCode);

	void registerEmployee(RegisterAlliedEmpRequest request, String agencyTypeCode, Integer creatoruserId);

	void updateEmployee(UpdateAlliedEmpRequest request, Integer id, String agencyTypeCode);

	void updateBiometrics(BiometricRequest request, Integer id, String agencyTypeCode);

	void updatePhoto(String base64Photo, Integer id, String agencyTypeCode);

	AlliedEmpCompleteDetailsResponse fetchEmpCompleteDetails(Integer id, String agencyTypeCode);

	List<Address> fetchAddressByEmpIdAndAgencyTypeCode(Integer id, String agencyTypeCode);

	void addAddress(AddressRequest request, Integer id, String agencyTypeCode);

	Integer fetchEmpBiometricsCapturedCountByAgenctTypeCode(String agencyTypeCode);

	Integer fetchEmpPhotoCapturedCountByAgencyTypeCode(String agencyTypeCode);

}
