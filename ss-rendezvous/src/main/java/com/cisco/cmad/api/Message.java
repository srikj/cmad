package com.cisco.cmad.api;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Message {
	@Id
	private int messageId;
	
	@OneToOne(cascade = CascadeType.ALL)
	private UserInfo userInfo;
	
	private String messageText;
	
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	public Message() {
	}

	public Message(int messageId, UserInfo userInfo, String messageText, Date createdDate) {
		super();
		this.messageId = messageId;
		this.userInfo = userInfo;
		this.messageText = messageText;
		this.createdDate = createdDate;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
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
