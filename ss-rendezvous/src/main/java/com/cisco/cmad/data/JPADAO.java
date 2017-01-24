package com.cisco.cmad.data;

import java.util.List;

import com.cisco.cmad.api.Comment;
import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.Message;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.User;

public class JPADAO implements DAO {

	@Override
	public String createUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getFavouritePosts(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPost(Post post) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Comment> getComments(int post_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPosts(int offset, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPostsByTag(int tag_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPostsByInterest(Interest interest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post getPost(int post_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFavouritePostCount(int post_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void markFavourite(int post_id, String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unMarkFavourite(int post_id, String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Post> search(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createComment(Comment comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Message> getMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getMessages(int offset, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
