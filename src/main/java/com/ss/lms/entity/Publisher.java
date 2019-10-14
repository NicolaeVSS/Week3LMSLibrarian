package com.ss.lms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_publisher")
public class Publisher implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3607759577307213492L;
	@Id
	Integer publisherId;
	@Column(name = "publisherName")
	String publisherName;
	@Column(name = "publisherAddress")
	String publisherAddress;
	@Column(name = "publisherPhone")
	String publisherPhone;
	
	public Publisher() {}
	
	public Publisher(Integer publisherId, String publisherName, 
			String publisherAddress, String publisherPhone) 
	{
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.publisherAddress = publisherAddress;
		this.publisherPhone = publisherPhone;
	}

	@Override
	public String toString() 
	{
		return publisherId + "\t" + publisherName + "\t" + publisherAddress + "\t" + publisherPhone;
	}
	
	/**
	 * @return the publisherId
	 */
	public Integer getPublisherId() {
		return publisherId;
	}

	/**
	 * @param publisherId the publisherId to set
	 */
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	/**
	 * @return the publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}

	/**
	 * @param publisherName the publisherName to set
	 */
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	/**
	 * @return the publisherAddress
	 */
	public String getPublisherAddress() {
		return publisherAddress;
	}

	/**
	 * @param publisherAddress the publisherAddress to set
	 */
	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}

	/**
	 * @return the publisherPhone
	 */
	public String getPublisherPhone() {
		return publisherPhone;
	}

	/**
	 * @param publisherPhone the publisherPhone to set
	 */
	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}
}