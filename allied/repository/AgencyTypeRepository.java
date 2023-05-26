package com.cdac.centralvista.allied.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.centralvista.allied.model.AgencyType;

public interface AgencyTypeRepository extends JpaRepository<AgencyType, Integer> {

	public Optional<AgencyType> findByAgencyTypeCode(String agencyTypeCode);
}
