package com.cdac.centralvista.allied.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AgencyType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(unique = true, nullable = false)
	private String agencyTypeCode;
	
	@Column(unique = true, nullable = false)
	private String agencyTypeName;
	
	public AgencyType() {

	}

	public AgencyType(String agencyTypeCode, String agencyTypeName) {
		super();
		this.agencyTypeCode = agencyTypeCode;
		this.agencyTypeName = agencyTypeName;
	}

	public String getAgencyTypeCode() {
		return agencyTypeCode;
	}

	public void setAgencyTypeCode(String agencyTypeCode) {
		this.agencyTypeCode = agencyTypeCode;
	}

	public String getAgencyTypeName() {
		return agencyTypeName;
	}

	public void setAgencyTypeName(String agencyTypeName) {
		this.agencyTypeName = agencyTypeName;
	}

	public Integer getId() {
		return id;
	}
	
	
}
