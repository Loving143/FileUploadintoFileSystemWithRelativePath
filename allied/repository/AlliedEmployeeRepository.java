package com.cdac.centralvista.allied.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cdac.centralvista.allied.model.AlliedEmpServiceInfo;
import com.cdac.centralvista.allied.model.AlliedEmployee;
import com.cdac.centralvista.alliedAdmin.dto.AlliedEmpListDto;
import com.cdac.centralvista.model.Address;

public interface AlliedEmployeeRepository extends JpaRepository<AlliedEmployee, Integer> {

	
	@Query("SELECT new com.cdac.centralvista.alliedAdmin.dto.AlliedEmpListDto( "
			+ "		emp.id, emp.orginationEmpId , emp.fullName, emp.photoCaptured, "
			+ "		emp.fingerDataCaptured, agn.agencyCode"
			+ "		) "
			+ "FROM AlliedEmployee emp inner join emp.serviceInfo servInfo "
			+ "		inner join servInfo.agencyType agnType "
			+ "		inner join servInfo.agency agn "
			+ "WHERE agnType.agencyTypeCode = :agencyTypeCode "
			+ "		AND ( lower(emp.orginationEmpId) LIKE lower(concat('%', :searchValue,'%')) "
			+ "			  OR lower(emp.fullName)  LIKE lower(concat('%', :searchValue,'%')) "
			+ "			  OR lower(agn.agencyCode) LIKE lower(concat('%', :searchValue,'%')) )")
	List<AlliedEmpListDto> fetchEmpListPage(String agencyTypeCode, String searchValue, Pageable pageable);
	
	@Query("SELECT COUNT(emp) "
		+ " FROM AlliedEmployee emp inner join emp.serviceInfo servInfo "
		+ "		inner join servInfo.agencyType agnType "
		+ "		inner join servInfo.agency agn "
		+ "WHERE agnType.agencyTypeCode = :agencyTypeCode" )
	Optional<Integer> totalEmpCountByAgencyTypeCode(String agencyTypeCode);
	
	@Query("SELECT COUNT(emp) "
			+ " FROM AlliedEmployee emp inner join emp.serviceInfo servInfo "
			+ "		inner join servInfo.agencyType agnType "
			+ "		inner join servInfo.agency agn "
			+ "WHERE agnType.agencyTypeCode = :agencyTypeCode"
			+ "		AND (emp.fingerData1 is not null "
			+ "			OR emp.fingerData2 is not null)" )
	Optional<Integer> empBiometricCapturedCountByAgencyTypeCode(String agencyTypeCode);
	
	@Query("SELECT COUNT(emp) "
			+ " FROM AlliedEmployee emp inner join emp.serviceInfo servInfo "
			+ "		inner join servInfo.agencyType agnType "
			+ "		inner join servInfo.agency agn "
			+ "WHERE agnType.agencyTypeCode = :agencyTypeCode"
			+ "		AND emp.photo is not null " )
	Optional<Integer> empPhotoCapturedCountByAgencyTypeCode(String agencyTypeCode);
	
	@Query("SELECT emp "
		+ "FROM AlliedEmployee emp INNER JOIN emp.serviceInfo servInfo "
		+ "		inner join servInfo.agencyType agnType "
		+ "WHERE emp.id = :id "
		+ "		AND agnType.agencyTypeCode = :agencyTypeCode")
	public Optional<AlliedEmployee> fetchEmpDetailsByIdAndAgencyTypeCode(Integer id, String agencyTypeCode);
	
	@Query("SELECT emp.address "
		+ "FROM AlliedEmployee emp inner join emp.serviceInfo servInfo "
		+ "		inner join servInfo.agencyType agnType "
		+ "WHERE emp.id = :id "
		+ "		AND agnType.agencyTypeCode = :agencyTypeCode")
	public List<Address> fetchAddressByEmpIdAndAgencyTypeCode(Integer id, String agencyTypeCode);
	
//	@Modifying
//	@Query("UPDATE AlliedEmployee emp SET emp.serviceInfo = :serviceInfo")
//	public void updateCurrentServiceInfo(AlliedEmpServiceInfo serviceInfo);
//	
//	@Query("SELECT emp.serviceInfo "
//			+ "FROM AlliedEmployee emp INNER JOIN emp.serviceInfo servInfo "
//			+ "		inner join servInfo.agencyType agnType "
//			+ "WHERE emp.id = :id "
//			+ "		AND agnType.agencyTypeCode = :agencyTypeCode")
//	public AlliedEmpServiceInfo fetchCurrentServiceInfoByEmpUserIdAndAgencyTypeCode(Integer id, String agencyTypeCode);
	
	boolean existsByEmailIgnoreCase(String email);
	
	boolean existsByEmpIdIgnoreCase(String empId);
	
	boolean existsByContactNumber(String contactNumber);
	
	boolean existsByAadhaarNumber(String aadhaarNumber);
	
	boolean existsByEmailIgnoreCaseAndIdNot(String email, Integer id);
	
	boolean existsByEmpIdIgnoreCaseAndIdNot(String empId, Integer id);
	
	boolean existsByContactNumberAndIdNot(String contactNumber, Integer id);
	
	boolean existsByAadhaarNumberAndIdNot(String aadhaarNumber, Integer id);
	
	boolean existsByOrginationEmpIdIgnoreCaseAndServiceInfoAgencyId(String organizationEmpId, Integer agencyId);
	
	boolean existsByOrginationEmpIdIgnoreCaseAndIdNotAndServiceInfoAgencyId(String organizationEmpId, Integer id, Integer agencyId);
	
}
