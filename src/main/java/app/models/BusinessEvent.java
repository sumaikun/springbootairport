package app.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import app.enums.*;

@Document(collection = "businessEvents")


public class BusinessEvent implements Serializable{
	
	private static final long serialVersionUID = -7788619177798333712L;
	
	
	@Id
	public String id;
	
	public String customer;	

	public String transaction;
	
	@NotNull
	public String card;
	
	@NotNull
	public EventState state;
	
	@NotNull
	public String lounge;
	
	public String adultC;
	
	public String childC;
	
	@CreatedDate	
	private Date createdDate;
	 
	@LastModifiedDate	
	private Date lastModifiedDate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public EventState getState() {
		return state;
	}

	public void setState(EventState state) {
		this.state = state;
	}

	public String getLounge() {
		return lounge;
	}

	public void setLounge(String lounge) {
		this.lounge = lounge;
	}

	public String getAdultC() {
		return adultC;
	}

	public void setAdultC(String adultC) {
		this.adultC = adultC;
	}

	public String getChildC() {
		return childC;
	}

	public void setChildC(String childC) {
		this.childC = childC;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	
	

}
