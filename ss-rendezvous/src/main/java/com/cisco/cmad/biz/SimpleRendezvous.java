package com.cisco.cmad.biz;

import java.util.List;

import com.cisco.cmad.api.Comment;
import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.Message;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.User;
import com.cisco.cmad.data.DAO;
import com.cisco.cmad.data.JPADAO;

public class SimpleRendezvous implements Rendezvous {
	
	private DAO dao;

	public SimpleRendezvous() {
		dao = new JPADAO();
		
	}

	public void register(User user) {
		// TODO Auto-generated method stub
		
	}

	public User login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public void invite(String emailIds) {
		// TODO Auto-generated method stub
		
	}

	public List<Post> getFavouritePosts(String username) {
		// TODO Auto-generated method stub
		return null;
	}

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

	public void createMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	public List<Message> getMessages(int number) {
		// TODO Auto-generated method stub
		return null;
	}

}
