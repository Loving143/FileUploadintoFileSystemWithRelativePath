package com.cdac.centralvista.allied.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdac.centralvista.allied.model.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Integer> {

	@Query("SELECT agn FROM Agency agn WHERE agn.agencyType.id = :agencyTypeId")
	public List<Agency> findAllByAgencyTypeId(Integer agencyTypeId);
	
	@Query("SELECT agn "
		+ "FROM Agency agn inner join agn.agencyType type "
		+ "WHERE lower(type.agencyTypeCode) = lower(:agencyTypeCode) ")
	public List<Agency> fetchByAgencyTypeCode(String agencyTypeCode);
	
	@Query("SELECT agn "
			+ "FROM Agency agn inner join agn.agencyType type "
			+ "WHERE agn.id = :id "
			+ "		AND lower(type.agencyTypeCode) = lower(:agencyTypeCode) ")
	public Optional<Agency> fetchByIdAndAgencyTypeCode(Integer id, String agencyTypeCode);
	
	public boolean existsByAgencyCodeIgnoreCase(String agencyCode);
	
	public boolean existsByAgencyNameIgnoreCase(String agencyName);
	
	public boolean existsByAgencyCodeIgnoreCaseAndIdNot(String agencyCode, Integer id);
	
	public boolean existsByAgencyNameIgnoreCaseAndIdNot(String agencyName, Integer id);
}
