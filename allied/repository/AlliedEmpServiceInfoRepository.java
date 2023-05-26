package com.cdac.centralvista.allied.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdac.centralvista.allied.model.AlliedEmpServiceInfo;

public interface AlliedEmpServiceInfoRepository extends JpaRepository<AlliedEmpServiceInfo, Integer> {

	@Query("SELECT servInfo "
		+ "FROM AlliedEmpServiceInfo servInfo join fetch servInfo.employeeInfo emp "
		+ "		join fetch servInfo.agencyType agnType "
		+ "		join fetch servInfo.agency "
		+ "		join fetch  servInfo.designation "
		+ "WHERE emp.id = :id"
		+ "		AND agnType.agencyTypeCode = :agencyTypeCode")
	public List<AlliedEmpServiceInfo> fetchAllServiceInfoByEmpIdAndAgencyType(Integer id, String agencyTypeCode);
	
	@Query("SELECT servInfo "
			+ "FROM AlliedEmpServiceInfo servInfo INNER JOIN servInfo.agencyType agnType "
			+ "WHERE servInfo.employeeInfo.id = :id"
			+ "		AND agnType.agencyTypeCode = :agencyTypeCode"
			+ "		AND servInfo.active = true")
	public AlliedEmpServiceInfo fetchActiveServiceInfoByEmpUserIdAndAgencyType(Integer id, String agencyTypeCode);
	
}
