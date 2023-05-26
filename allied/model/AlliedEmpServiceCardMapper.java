package com.cdac.centralvista.allied.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cdac.centralvista.model.Card;

@Entity
public class AlliedEmpServiceCardMapper {

	public static class Id implements Serializable{
		
		private static final long serialVersionUID = 1L;

		@Column(name = "emp_service_id")
		private int empServiceId;
		
		@Column(name = "card_id")
		private int cardId;
	
		public Id() {

		}

		public Id(int empServiceId, int cardId) {
			super();
			this.empServiceId = empServiceId;
			this.cardId = cardId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(cardId, empServiceId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Id other = (Id) obj;
			return cardId == other.cardId && empServiceId == other.empServiceId;
		}	
		
	}
	
	@EmbeddedId
	private Id id = new Id();
	
	@ManyToOne
	@JoinColumn(name = "emp_service_id", insertable = false, updatable = false)
	private AlliedEmpServiceInfo empServiceInfo;
	
	@ManyToOne
	@JoinColumn(name = "card_id", insertable = false, updatable = false)
	private Card card;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date issuedOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date handoverOn;

	private boolean active;	
	
	public AlliedEmpServiceCardMapper() {
		
	}

	public AlliedEmpServiceCardMapper(int empServiceId, int cardId, Date issuedDate, boolean active) {
		this.id.empServiceId = empServiceId;
		this.id.cardId = cardId;
		this.issuedOn = issuedDate;
		this.active = active;
	}
	
	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public AlliedEmpServiceInfo getEmpServiceInfo() {
		return empServiceInfo;
	}

	public void setEmpServiceInfo(AlliedEmpServiceInfo empServiceInfo) {
		this.empServiceInfo = empServiceInfo;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Date getIssuedOn() {
		return issuedOn;
	}

	public void setIssuedOn(Date issuedOn) {
		this.issuedOn = issuedOn;
	}

	public Date getHandoverOn() {
		return handoverOn;
	}

	public void setHandoverOn(Date handoverOn) {
		this.handoverOn = handoverOn;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
