package com.ss.lms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@IdClass(com.ss.lms.entity.Book.class)
@Entity
@Table(name = "tbl_book")
public class Book implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1438645014368993534L;
	@Id
	private Integer bookId;
	@Column(name = "title")
	private String title;
	@Column(name = "authId")
	private Integer authId;
	@Column(name = "pubId")
	private Integer pubId;
//	private Author author;
//	private Publisher publisher;
	
	public Book(Integer bookId, String title, Integer authId, Integer pubId) {
		this.bookId = bookId;
		this.title = title;
		this.authId = authId;
		this.pubId = pubId;
	}
	
	/**
	 * @return the authId
	 */
	public Integer getAuthorId() {
		return authId;
	}

	/**
	 * @param authId the authId to set
	 */
	public void setAuthorId(Integer authId) {
		this.authId = authId;
	}

	/**
	 * @return the pubId
	 */
	public Integer getPublisherId() {
		return pubId;
	}

	/**
	 * @param pubId the pubId to set
	 */
	public void setPublisherId(Integer pubId) {
		this.pubId = pubId;
	}

	@Override
	public String toString() 
	{
		return  bookId + "\t" + title + "\t" + authId + "\t" + pubId;
	}
	

	/**
	 * @return the bookId
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

//	/**
//	 * @return the authorId
//	 */
//	public Author getAuthor() {
//		return author;
//	}
//
//	/**
//	 * @param authorId the authorId to set
//	 */
//	public void setAuthor(Author author) {
//		this.author= author;
//	}
//	
//	/**
//	 * @return the publisher
//	 */
//	public Publisher getPublisher() {
//		return publisher;
//	}
//
//	/**
//	 * @param publisher the publisher to set
//	 */
//	public void setPublisher(Publisher publisher) {
//		this.publisher = publisher;
//	}

	public Book() {
		
	}
	
}