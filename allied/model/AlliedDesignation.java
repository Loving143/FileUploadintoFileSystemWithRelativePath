package com.cdac.centralvista.allied.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"designationName", "agency_id"}) })
public class AlliedDesignation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 50, unique = true)
	private String designationCode;

	@Column(nullable = false, length = 100)
	private String designationName;

	@ManyToOne
	@JoinColumn(nullable = false, name = "agency_id")
	private Agency agency;

	public AlliedDesignation() {

	}

	public AlliedDesignation(String designationCode, String designationName, Agency agency) {
		super();
		this.designationCode = designationCode;
		this.designationName = designationName;
		this.agency = agency;
	}

	public String getDesignationCode() {
		return designationCode;
	}

	public void setDesignationCode(String designationCode) {
		this.designationCode = designationCode;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public int getId() {
		return id;
	}

}
