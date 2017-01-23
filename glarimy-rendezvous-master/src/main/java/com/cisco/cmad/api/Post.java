package com.cisco.cmad.api;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Post {

	private int post_id;
	private String title;
	private Set<Tag> tags = new HashSet<Tag>(0);
	private String postText;
	private String abstractText;
	private List<Comment> comments;
	private Date createdDate;
	private Date updatedDate;
	private User user;
	private Interest interest;

	private Set<User> favouritedUsers = new HashSet<User>(0);

	public Post() {
	}

	public Post(int post_id, String title, Set<Tag> tags, String postText, String abstractText, List<Comment> comments,
			Date createdDate, Date updatedDate, User user, Interest interest, Set<User> favouritedUsers) {
		super();
		this.post_id = post_id;
		this.title = title;
		this.tags = tags;
		this.postText = postText;
		this.abstractText = abstractText;
		this.comments = comments;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
