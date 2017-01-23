package com.cisco.cmad.api;

public class Message {
	private int messageId;
	private User user;
	private String messageText;
	public Message() {
	}
	public Message(int messageId, User user, String messageText) {
		super();
		this.messageId = messageId;
		this.user = user;
		this.messageText = messageText;
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
	
	

}
