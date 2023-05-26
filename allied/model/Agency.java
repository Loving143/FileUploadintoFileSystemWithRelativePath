package com.cdac.centralvista.allied.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Agency {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(unique = true, nullable = false)
	private String agencyCode;
	
	@Column(unique = true, nullable = false)
	private String agencyName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agency_type_id", nullable = false)
	private AgencyType agencyType;

	public Agency() {

	}

	public Agency(String agencyCode, String agencyName, AgencyType agencyType) {
		super();
		this.agencyCode = agencyCode;
		this.agencyName = agencyName;
		this.agencyType = agencyType;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Integer getId() {
		return id;
	}

	public AgencyType getAgencyType() {
		return agencyType;
	}

	public void setAgencyType(AgencyType agencyType) {
		this.agencyType = agencyType;
	}
	
	
}
