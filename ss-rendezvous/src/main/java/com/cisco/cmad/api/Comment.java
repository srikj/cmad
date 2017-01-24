package com.cisco.cmad.api;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Comment {
	@Id
	private int id;
	private String commentText;
	private String username;
	private Date createdDate;
	private Date updatedDate;
	public Comment() {
	}
	public Comment(int id, String commentText, String username, Date createdDate, Date updatedDate) {
		super();
		this.id = id;
		this.commentText = commentText;
		this.username = username;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public String getUser() {
		return username;
	}
	public void setUser(String user) {
		this.username = user;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
	

}
