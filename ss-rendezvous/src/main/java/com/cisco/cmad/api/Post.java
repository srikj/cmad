package com.cisco.cmad.api;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
public class Post {

	@Id
	@GeneratedValue
	private int post_id;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String title;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String postText;
	private String abstractText;
	private Interest interest;
	
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	@Temporal(TemporalType.DATE)
	private Date updatedDate;
	
	private String username;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	@IndexedEmbedded
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Tag> tags = new HashSet<Tag>(0);

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "favouritePosts")
	private Set<User> favouritedUsers = new HashSet<User>(0);

	public Post() {
	}

	public Post(int post_id, String title, Set<Tag> tags, String postText, String abstractText, List<Comment> comments,
			Date createdDate, Date updatedDate, String username, Interest interest, Set<User> favouritedUsers) {
		super();
		this.post_id = post_id;
		this.title = title;
		this.tags = tags;
		this.postText = postText;
		this.abstractText = abstractText;
		this.comments = comments;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.username = username;
		this.interest = interest;
		this.favouritedUsers = favouritedUsers;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
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

	public String getUser() {
		return username;
	}

	public void setUser(String username) {
		this.username = username;
	}

	public Set<User> getFavouritedUsers() {
		return favouritedUsers;
	}

	public void setFavouritedUsers(Set<User> favouritedUsers) {
		this.favouritedUsers = favouritedUsers;
	}

	public Interest getInterest() {
		return interest;
	}

	public void setInterest(Interest interest) {
		this.interest = interest;
	}

}
