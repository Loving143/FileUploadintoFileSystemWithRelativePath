package com.cdac.centralvista.allied.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdac.centralvista.allied.model.AlliedDesignation;

public interface AlliedDesignationRepository extends JpaRepository<AlliedDesignation, Integer> {

	@Query("SELECT desig FROM AlliedDesignation desig WHERE desig.agency.id = :agencyId")
	List<AlliedDesignation> fetchAllByAgencyId(Integer agencyId);
	
	public boolean existsByDesignationCodeIgnoreCaseAndAgencyId(String designationCode, Integer agencyId);
	
	public boolean existsByDesignationCodeIgnoreCase(String designationCode);
	
	public boolean existsByDesignationNameIgnoreCaseAndAgencyId(String designationName, Integer agencyId);
	
	public boolean existsByDesignationCodeIgnoreCaseAndAgencyIdAndIdNot(String designationCode, Integer agencyId, Integer id);
	
	public boolean existsByDesignationCodeIgnoreCaseAndIdNot(String designationCode, Integer id);
	
	public boolean existsByDesignationNameIgnoreCaseAndAgencyIdAndIdNot(String designationName, Integer agencyId, Integer id);
}
