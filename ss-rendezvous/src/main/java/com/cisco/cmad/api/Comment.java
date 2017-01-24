package com.cisco.cmad.api;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Comment {
	@Id
	private int id;
	private String commentText;
	private User user;
	private Post post;
	private Date createdDate;
	private Date updatedDate;
	public Comment() {
	}
	public Comment(int id, String commentText, User user, Post post, Date createdDate, Date updatedDate) {
		super();
		this.id = id;
		this.commentText = commentText;
		this.user = user;
		this.post = post;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
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
