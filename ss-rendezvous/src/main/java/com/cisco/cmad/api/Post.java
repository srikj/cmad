package com.cisco.cmad.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Indexed
public class Post {

	@Id
	@GeneratedValue
	private int post_id;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String title;
	
	@Lob
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String postText;
	private String abstractText;
	private Interest topic;
	
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	@Temporal(TemporalType.DATE)
	private Date updatedDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "username", nullable = false)
	private User user;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy="post")
	private List<Comment> comments = new ArrayList<>();
	
	@IndexedEmbedded
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Tag> tags = new HashSet<Tag>(0);

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "favouritePosts")
	private Set<User> favouritedUsers = new HashSet<User>(0);

	public Post() {
	}

	public Post(int post_id, String title, String postText, String abstractText, Interest topic, Date createdDate,
			Date updatedDate, User user, List<Comment> comments, Set<Tag> tags, Set<User> favouritedUsers) {
		super();
		this.post_id = post_id;
		this.title = title;
		this.postText = postText;
		this.abstractText = abstractText;
		this.topic = topic;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.user = user;
		this.comments = comments;
		this.tags = tags;
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

	public Interest getTopic() {
		return topic;
	}

	public void setTopic(Interest topic) {
		this.topic = topic;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<User> getFavouritedUsers() {
		return favouritedUsers;
	}

	public void setFavouritedUsers(Set<User> favouritedUsers) {
		this.favouritedUsers = favouritedUsers;
	}

	

}
