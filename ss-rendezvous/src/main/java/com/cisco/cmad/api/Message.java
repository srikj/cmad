package com.cisco.cmad.api;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Message {
	@Id
	private int messageId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "username", nullable = false)
	private User user;
	
	private String messageText;
	
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	public Message() {
	}

	public Message(int messageId, User user, String messageText, Date createdDate) {
		super();
		this.messageId = messageId;
		this.user = user;
		this.messageText = messageText;
		this.createdDate = createdDate;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	

}
