package com.cisco.cmad.api;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity

public class UserInfo {
	
	@Id
	private String username;
	
	private String name;
	private String email;
	private String phoneNumber;
	private Interest interest;
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserInfo(String username, String name, String email, String phoneNumber,
			Interest interest) {
		super();
		this.username = username;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.interest = interest;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Interest getInterest() {
		return interest;
	}
	public void setInterest(Interest interest) {
		this.interest = interest;
	}
	
	
	
}
