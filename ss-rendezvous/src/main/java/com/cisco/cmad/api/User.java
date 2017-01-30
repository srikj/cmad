package com.cisco.cmad.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {

	@Id
	@PrimaryKeyJoinColumn
	private String username;
	
	@Embedded
	protected UserInfo userInfo;
	
	private String password;
	
	private String lastLoginIP;
	
	@Temporal(TemporalType.DATE)
	private Date lastLoginDate;
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Post> favouritePosts = new HashSet<Post>(0);
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy="user")
	private List<Post> posts = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy="user")
	private List<Message> messages = new ArrayList<>();

	public User() {
	}

	public User(String username, UserInfo userInfo, String lastLoginIP, Date lastLoginDate, Date createdDate,
			Date updatedDate, Set<Post> favouritePosts) {
		super();
		this.username = username;
		this.userInfo = userInfo;
		this.lastLoginIP = lastLoginIP;
		this.lastLoginDate = lastLoginDate;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.favouritePosts = favouritePosts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
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

	public Set<Post> getFavouritePosts() {
		return favouritePosts;
	}

	public void setFavouritePosts(Set<Post> favouritePosts) {
		this.favouritePosts = favouritePosts;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Message> getMessages(){
		return messages;
	}
	
	public void setMessages(List<Message> msgs) {
		this.messages = msgs;
	}

}
