package com.ss.lms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ss.lms.entity.id.BookCopyId;

//@IdClass(com.ss.lms.entity.BookCopy.class)
@Entity
@Table(name = "tbl_book_copies")
public class BookCopy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7441409147455698231L;
	//Book book;
	@EmbeddedId
	BookCopyId bookCopyId;
	@Column(name = "noOfCopies")
	Integer noOfCopies;
	
	
	
	public BookCopy() {
		
	}
	
	public BookCopy(BookCopyId bookCopyId, Integer noOfCopies) {
		this.bookCopyId = bookCopyId;
		this.noOfCopies = noOfCopies;
	}
	
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookCopyId == null) ? 0 : bookCopyId.hashCode());
		result = prime * result + ((noOfCopies == null) ? 0 : noOfCopies.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookCopy other = (BookCopy) obj;
		if (bookCopyId == null) {
			if (other.bookCopyId != null)
				return false;
		} else if (!bookCopyId.equals(other.bookCopyId))
			return false;
		if (noOfCopies == null) {
			if (other.noOfCopies != null)
				return false;
		} else if (!noOfCopies.equals(other.noOfCopies))
			return false;
		return true;
	}

	/**
	 * @return the noOfCopies
	 */
	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	/**
	 * @return the bookCopyId
	 */
	public BookCopyId getBookCopyId() {
		return bookCopyId;
	}

	/**
	 * @param bookCopyId the bookCopyId to set
	 */
	public void setBookCopyId(BookCopyId bookCopyId) {
		this.bookCopyId = bookCopyId;
	}

	/**
	 * @param noOfCopies the noOfCopies to set
	 */
	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	
	
}