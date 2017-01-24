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
public class User {

	@Id
	private String username;
	
	private String name;
	private String password;
	private String email;
	private Number phoneNumber;
	private Interest interest;
	private String lastLoginIP;
	
	@Temporal(TemporalType.DATE)
	private Date lastLoginDate;
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Post> favouritePosts = new HashSet<Post>(0);

	public User() {
	}

	public User(String username, String name, String password, String email, Number phoneNumber, Interest interest,
			Date createdDate, Date updatedDate, String lastLoginIP, Date lastLoginDate, Set<Post> favouritePosts) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.interest = interest;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.lastLoginIP = lastLoginIP;
		this.lastLoginDate = lastLoginDate;
		this.favouritePosts = favouritePosts;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Number getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Number phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Interest getInterest() {
		return interest;
	}

	public void setInterest(Interest interest) {
		this.interest = interest;
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

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Set<Post> getFavouritePosts() {
		return favouritePosts;
	}

	public void setFavouritePosts(Set<Post> favouritePosts) {
		this.favouritePosts = favouritePosts;
	}

}
