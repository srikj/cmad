package com.cisco.cmad.api;

import java.util.HashSet;
import java.util.Set;

public class Tag {

	private int id;
	private String tagName;
	
	private Set<Post> taggedPosts = new HashSet<Post>(0);

	public Tag() {
	}

	public Tag(int id, String tagName, Set<Post> taggedPosts) {
		super();
		this.id = id;
		this.tagName = tagName;
		this.taggedPosts = taggedPosts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Set<Post> getTaggedPosts() {
		return taggedPosts;
	}

	public void setTaggedPosts(Set<Post> taggedPosts) {
		this.taggedPosts = taggedPosts;
	}
	
	
}
