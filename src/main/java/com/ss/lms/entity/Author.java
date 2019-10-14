
package com.ss.lms.entity;

import java.io.Serializable;

//import org.springframework.boot.autoconfigure.domain.EntityScan;
import javax.persistence.*;

@IdClass(com.ss.lms.entity.Author.class)
@Entity
@Table(name = "tbl_author")
public class Author implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3002288345129007776L;
	@Id
	private Integer authorId;
	@Column(name = "authorName")
	private String authorName;
	
	public Author(){
		
	}
	
	public Author(Integer authorId, String authorName) {
		this.authorId = authorId;
		this.authorName = authorName;
	}
	
	@Override
	public String toString() 
	{
		return authorId + "\t" + authorName;
	}

	/**
	 * @return the authorId
	 */
	public Integer getAuthorId() {
		return authorId;
	}

	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorId == null) ? 0 : authorId.hashCode());
		result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
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
		Author other = (Author) obj;
		if (authorId == null) {
			if (other.authorId != null)
				return false;
		} else if (!authorId.equals(other.authorId))
			return false;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equals(other.authorName))
			return false;
		return true;
	}
}