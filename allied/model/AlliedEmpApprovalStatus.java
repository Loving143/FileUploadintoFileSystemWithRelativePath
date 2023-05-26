package com.cdac.centralvista.allied.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cdac.centralvista.employee.model.Employee;
import com.cdac.centralvista.visitor.model.ApplicationStatus;

@Entity
public class AlliedEmpApprovalStatus{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(length = 500)
	private String remark;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emp_service_info_id")
	private AlliedEmpServiceInfo empServiceInfo;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_status_id")
	private AlliedEmpApprovalStatus currentStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Employee actor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Employee forwardedTo;
	
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;
	
	@Column(name = "\"order\"")
	private int order;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordCreationDate = new Date();
	
	
	
	public AlliedEmpApprovalStatus(String remark, AlliedEmpServiceInfo empServiceInfo, AlliedEmpApprovalStatus currentStatus,
			Employee actor, Employee forwardedTo, ApplicationStatus status, int order) {
		super();
		this.remark = remark;
		this.empServiceInfo = empServiceInfo;
		this.currentStatus = currentStatus;
		this.actor = actor;
		this.forwardedTo = forwardedTo;
		this.status = status;
		this.order = order;
	}
	
	public AlliedEmpApprovalStatus(String remark, AlliedEmpServiceInfo empServiceInfo, AlliedEmpApprovalStatus currentStatus,
			Employee actor, Employee forwardedTo, ApplicationStatus status, int order, Date recordCreationDate) {
		super();
		this.remark = remark;
		this.empServiceInfo = empServiceInfo;
		this.currentStatus = currentStatus;
		this.actor = actor;
		this.forwardedTo = forwardedTo;
		this.status = status;
		this.order = order;
		this.recordCreationDate = recordCreationDate;
	}

	public AlliedEmpApprovalStatus() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public AlliedEmpServiceInfo getEmpServiceInfo() {
		return empServiceInfo;
	}

	public void setEmpServiceInfo(AlliedEmpServiceInfo empServiceInfo) {
		this.empServiceInfo = empServiceInfo;
	}

	public AlliedEmpApprovalStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(AlliedEmpApprovalStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Employee getActor() {
		return actor;
	}

	public void setActor(Employee actor) {
		this.actor = actor;
	}

	public Employee getForwardedTo() {
		return forwardedTo;
	}

	public void setForwardedTo(Employee forwardedTo) {
		this.forwardedTo = forwardedTo;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Date getRecordCreationDate() {
		return recordCreationDate;
	}

	public void setRecordCreationDate(Date recordCreationDate) {
		this.recordCreationDate = recordCreationDate;
	}
	
}
