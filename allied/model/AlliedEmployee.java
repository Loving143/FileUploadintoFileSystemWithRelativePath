package com.cdac.centralvista.allied.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cdac.centralvista.employee.dto.request.EmployeeRegistrationRequest;
import com.cdac.centralvista.model.Address;
import com.cdac.centralvista.model.AddressType;
import com.cdac.centralvista.model.BloodGroup;
import com.cdac.centralvista.model.Category;
import com.cdac.centralvista.model.Document;
import com.cdac.centralvista.model.FingerData;
import com.cdac.centralvista.model.Gender;
import com.cdac.centralvista.model.Role;
import com.cdac.centralvista.model.User;

@Entity
public class AlliedEmployee extends User{
	
	@Column(nullable = false, length = 100)
	private String orginationEmpId;
	
	@Column(nullable = false, length = 128)
	private String fullName;

	private String fatherName;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dob;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 12)
	private Gender gender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_user_id")
	private User createdBy;
	
	@Lob
	@Column(nullable = true)
	@Basic(fetch=FetchType.LAZY)
	private byte[] photo;
	
	@AttributeOverrides({
			@AttributeOverride(name="fingerName", column=@Column(name = "finger1_name")),
			@AttributeOverride(name="fingerDataTemplate", column=@Column(name = "finger1_data_template")),
			@AttributeOverride(name="fingerDataImage", column=@Column(name = "finger1_data_image"))
	})
	private FingerData fingerData1;
	
	@AttributeOverrides({
			@AttributeOverride(name="fingerName", column=@Column(name = "finger2_name")),
			@AttributeOverride(name="fingerDataTemplate", column=@Column(name = "finger2_data_template")),
			@AttributeOverride(name="fingerDataImage", column=@Column(name = "finger2_data_image"))
	})
	private FingerData fingerData2;

	@Enumerated(EnumType.STRING)
	@Column(length = 11) 
	private BloodGroup bloodGroup;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date();

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updationDate = new Date();
	
	
	@Column(length = 14)
	private String otherContactNumber;

	@Column(nullable = true) 
	private String identificationMark;

	@Column(length = 14)
	private String officeContactNumber;

	@Column(length = 15)
	private String faxNumber;

	@JoinTable(
			name = "allied_emp_address_mapping",
			joinColumns = @JoinColumn(name = "emp_id" ),
			inverseJoinColumns = @JoinColumn(name = "address_id")
	)
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Address> address = new HashSet<>();

	@Column(length = 12, unique = true, nullable = false)
	private String aadhaarNumber;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "current_service_id")
	private AlliedEmpServiceInfo serviceInfo;

	private boolean photoCaptured = false;

	private boolean fingerDataCaptured = false;
	
	@Column(length = 500)
	private String remark;

	@JoinTable(
			name = "allied_emp_document_mapping",
			joinColumns = @JoinColumn(name = "emp_id" ),
			inverseJoinColumns = @JoinColumn(name = "document_id")
	)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Document> documentSet = new HashSet<>();

	private String icardNo;

	public AlliedEmployee() {
	}

	public AlliedEmployee(String empId, String password, String contactNumber, String email, Set<Role> roleSet,
			boolean credentialsExpired, boolean accountExpired, boolean accountLocked, boolean enable,
			String orginationEmpId, String fullName, String fatherName, Date dob, Gender gender, BloodGroup bloodGroup,
			String otherContactNumber, String identificationMark, String officeContactNumber,
			String faxNumber, String aadhaarNumber, String remark) {
		super(empId, password, contactNumber, email, roleSet, credentialsExpired, accountExpired, accountLocked,
				enable);
		this.orginationEmpId = orginationEmpId;
		this.fullName = fullName;
		this.fatherName = fatherName;
		this.dob = dob;
		this.gender = gender;
		this.bloodGroup = bloodGroup;
		this.otherContactNumber = otherContactNumber;
		this.identificationMark = identificationMark;
		this.officeContactNumber = officeContactNumber;
		this.faxNumber = faxNumber;
		this.aadhaarNumber = aadhaarNumber;
		this.remark = remark;
	}

	public String getOrginationEmpId() {
		return orginationEmpId;
	}

	public void setOrginationEmpId(String orginationEmpId) {
		this.orginationEmpId = orginationEmpId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public FingerData getFingerData1() {
		return fingerData1;
	}

	public void setFingerData1(FingerData fingerData1) {
		this.fingerData1 = fingerData1;
	}

	public FingerData getFingerData2() {
		return fingerData2;
	}

	public void setFingerData2(FingerData fingerData2) {
		this.fingerData2 = fingerData2;
	}

	public BloodGroup getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(BloodGroup bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdationDate() {
		return updationDate;
	}

	public void setUpdationDate(Date updationDate) {
		this.updationDate = updationDate;
	}

	public String getOtherContactNumber() {
		return otherContactNumber;
	}

	public void setOtherContactNumber(String otherContactNumber) {
		this.otherContactNumber = otherContactNumber;
	}

	public String getIdentificationMark() {
		return identificationMark;
	}

	public void setIdentificationMark(String identificationMark) {
		this.identificationMark = identificationMark;
	}

	public String getOfficeContactNumber() {
		return officeContactNumber;
	}

	public void setOfficeContactNumber(String officeContactNumber) {
		this.officeContactNumber = officeContactNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public AlliedEmpServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(AlliedEmpServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public boolean isPhotoCaptured() {
		return photoCaptured;
	}

	public void setPhotoCaptured(boolean photoCaptured) {
		this.photoCaptured = photoCaptured;
	}

	public boolean isFingerDataCaptured() {
		return fingerDataCaptured;
	}

	public void setFingerDataCaptured(boolean fingerDataCaptured) {
		this.fingerDataCaptured = fingerDataCaptured;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<Document> getDocumentSet() {
		return documentSet;
	}

	public void setDocumentSet(Set<Document> documentSet) {
		this.documentSet = documentSet;
	}

	public String getIcardNo() {
		return icardNo;
	}

	public void setIcardNo(String icardNo) {
		this.icardNo = icardNo;
	}
	
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	public void addAddress(Address addr) {
		this.address.add(addr);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlliedEmployee other = (AlliedEmployee) obj;
		return Objects.equals(this.getId(), other.getId());
	}

}
