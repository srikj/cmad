package com.cisco.cmad.rest;

import java.util.List;

import javax.ws.rs.Path;

import com.cisco.cmad.api.Comment;
import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.biz.SimpleRendezvous;

@Path("/rendezvous")
public class PostController {
	
	static Rendezvous rendezvous = new SimpleRendezvous();
	
	public void createPost(Post post) {
		// TODO Auto-generated method stub
		
	}

	public List<Comment> getComments(int post_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Post> getPosts(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Post> getPostsByTag(int tag_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Post> getPostsByInterest(Interest interest) {
		// TODO Auto-generated method stub
		return null;
	}

	public Post getPost(int post_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getFavouritePostCount(int post_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void markFavourite(int post_id, String username) {
		// TODO Auto-generated method stub
		
	}

	public void unMarkFavourite(int post_id, String username) {
		// TODO Auto-generated method stub
		
	}

	public List<Post> search(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createComment(Comment comment) {
		// TODO Auto-generated method stub
		
	}

}
