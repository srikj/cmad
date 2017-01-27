package com.cisco.cmad.biz;

import java.util.Date;
import java.util.List;

import com.cisco.cmad.api.Comment;
import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.InvalidDataException;
import com.cisco.cmad.api.InvalidInterestException;
import com.cisco.cmad.api.Message;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.PostNotFoundException;
import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.RendezvousException;
import com.cisco.cmad.api.TagNotFoundException;
import com.cisco.cmad.api.User;
import com.cisco.cmad.api.UserAlreadyExistsException;
import com.cisco.cmad.api.UserNotFoundException;
import com.cisco.cmad.data.DAO;
import com.cisco.cmad.data.JPADAO;

public class SimpleRendezvous implements Rendezvous {
	
	private DAO dao;

	public SimpleRendezvous() {
		dao = new JPADAO();
	}

	@Override
	public void register(User user) throws UserAlreadyExistsException, InvalidDataException, RendezvousException {
		if(user.getUsername()==null|| user.getUsername().trim().isEmpty()) 
			throw new InvalidDataException();
		if(user.getEmail()==null || user.getEmail().trim().isEmpty())
			throw new InvalidDataException();
		if(user.getName()==null || user.getName().trim().isEmpty())
			throw new InvalidDataException();
			
		if(dao.getUser(user.getUsername()) != null) throw new UserAlreadyExistsException();
		
		dao.createUser(user);
	}

	@Override
	public User login(String username, String password) throws UserNotFoundException,InvalidDataException, RendezvousException {
		if(username==null|| username.trim().isEmpty()||password == null || password.trim().isEmpty()) {
			throw new InvalidDataException();
		}
		User user = dao.getUser(username);
		if(user == null) {
			throw new UserNotFoundException();
		}
		if(!user.getPassword().equals(password)) {
			throw new InvalidDataException();
		}
		return user;
	}

	@Override
	public User update(User user) throws UserNotFoundException, InvalidDataException, RendezvousException {
		if(user.getUsername()==null|| user.getUsername().trim().isEmpty()) 
			throw new InvalidDataException();
		user.setUpdatedDate(new Date());
		dao.update(user);
		return null;
	}

	@Override
	public void invite(String emailIds) throws InvalidDataException, RendezvousException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Post> getFavouritePosts(String username) throws UserNotFoundException, RendezvousException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createPost(Post post) throws InvalidDataException, RendezvousException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Comment> getComments(int post_id) throws PostNotFoundException, RendezvousException {
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
	public List<Post> getPostsByTag(int tag_id) throws TagNotFoundException, RendezvousException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPostsByInterest(Interest interest) throws InvalidInterestException, RendezvousException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post getPost(int post_id) throws PostNotFoundException, RendezvousException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFavouritePostCount(int post_id) throws PostNotFoundException, RendezvousException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void markFavourite(int post_id, String username)
			throws PostNotFoundException, UserNotFoundException, RendezvousException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unMarkFavourite(int post_id, String username)
			throws PostNotFoundException, UserNotFoundException, RendezvousException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Post> search(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createComment(int post_id, Comment comment)
			throws PostNotFoundException, InvalidDataException, RendezvousException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createMessage(Message message) throws InvalidDataException, RendezvousException {
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
