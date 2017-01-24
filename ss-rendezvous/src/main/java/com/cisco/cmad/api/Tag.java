package com.cisco.cmad.api;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.search.annotations.Field;

@Entity
public class Tag {

	
	@Id
	@GeneratedValue
	private int id;
	@Field
	private String tagName;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
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
