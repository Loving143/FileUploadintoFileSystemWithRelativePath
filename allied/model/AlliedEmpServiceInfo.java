package com.cdac.centralvista.allied.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cdac.centralvista.model.Category;
import com.cdac.centralvista.model.User;

@Entity
public class AlliedEmpServiceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private AlliedEmployee employeeInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_service_info_id")
    private AlliedEmpServiceInfo currentServiceInfo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_type_id")
    private AgencyType agencyType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id")
    private Agency agency;

    private String battalion;
 
    private String company;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designation_id") 
    private AlliedDesignation designation;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
    
    @Column//(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date serviceStartDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date serviceEndDate;

    private boolean active = true;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updationDate = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_user_id")
	private User createdBy;
    
	@OneToMany(mappedBy = "empServiceInfo", fetch = FetchType.LAZY)
	private Collection<AlliedEmpServiceCardMapper> cardMapper = new ArrayList<>();
    
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "current_status_id")
	private AlliedEmpApprovalStatus currentStatus;
	
    public AlliedEmpServiceInfo() {
    }
    
	public AlliedEmpServiceInfo(AlliedEmployee employeeInfo, AgencyType agencyType,
			Agency agency, String battalion, String company, AlliedDesignation designation,
			Category category, Date serviceStartDate, Date serviceEndDate, boolean active, User createdBy) {
		super();
		this.employeeInfo = employeeInfo;
		employeeInfo.setServiceInfo(this);
		this.currentServiceInfo = this;
		this.agencyType = agencyType;
		this.agency = agency;
		this.battalion = battalion;
		this.company = company;
		this.designation = designation;
		this.category = category;
		this.serviceStartDate = serviceStartDate;
		this.serviceEndDate = serviceEndDate;
		this.active = active;
		this.createdBy = createdBy;
	}

	public AlliedEmpServiceInfo(AlliedEmployee employeeInfo, AlliedEmpServiceInfo currentServiceInfo,
			AgencyType agencyType, Agency agency, String battalion, String company, AlliedDesignation designation,
			Category category, Date serviceStartDate, Date serviceEndDate, boolean active, Date creationDate,
			Date updationDate, Collection<AlliedEmpServiceCardMapper> cardMapper, AlliedEmpApprovalStatus currentStatus, User createdBy) {
		super();
		this.employeeInfo = employeeInfo;
		employeeInfo.setServiceInfo(this);
		this.currentServiceInfo = currentServiceInfo;
		this.agencyType = agencyType;
		this.agency = agency;
		this.battalion = battalion;
		this.company = company;
		this.designation = designation;
		this.category = category;
		this.serviceStartDate = serviceStartDate;
		this.serviceEndDate = serviceEndDate;
		this.active = active;
		this.cardMapper = cardMapper;
		this.currentStatus = currentStatus;
		this.createdBy = createdBy;
	}


	public AlliedEmployee getEmployeeInfo() {
		return employeeInfo;
	}

	public void setEmployeeInfo(AlliedEmployee employeeInfo) {
		this.employeeInfo = employeeInfo;
	}

	public AlliedEmpServiceInfo getCurrentServiceInfo() {
		return currentServiceInfo;
	}

	public void setCurrentServiceInfo(AlliedEmpServiceInfo currentServiceInfo) {
		this.currentServiceInfo = currentServiceInfo;
	}

	public AgencyType getAgencyType() {
		return agencyType;
	}

	public void setAgencyType(AgencyType agencyType) {
		this.agencyType = agencyType;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public String getBattalion() {
		return battalion;
	}


	public void setBattalion(String battalion) {
		this.battalion = battalion;
	}


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public AlliedDesignation getDesignation() {
		return designation;
	}

	public void setDesignation(AlliedDesignation designation) {
		this.designation = designation;
	}

	public Date getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(Date serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public Date getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(Date serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public int getId() {
		return id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Collection<AlliedEmpServiceCardMapper> getCardMapper() {
		return cardMapper;
	}

	public void setCardMapper(Collection<AlliedEmpServiceCardMapper> cardMapper) {
		this.cardMapper = cardMapper;
	}

	public AlliedEmpApprovalStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(AlliedEmpApprovalStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdationDate() {
		return updationDate;
	}
	
	public void setUpdationDate(Date updationDate) {
		this.updationDate = updationDate;
	}

	public Category getCategory() {
		return category;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public AlliedEmpServiceInfo archive() {
		return new AlliedEmpServiceInfo(this.employeeInfo, this.currentServiceInfo, this.agencyType, this.agency,
					this.battalion, this.company, this.designation,this.category , this.serviceStartDate, this.serviceEndDate, false, 
					this.creationDate, this.updationDate, this.cardMapper, this.currentStatus, this.createdBy);
	}
}
