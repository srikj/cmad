package com.cisco.cmad.biz;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.cisco.cmad.api.UserInfo;
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
		UserInfo userinfo = user.getUserInfo();
		if(userinfo.getEmail()==null || userinfo.getEmail().trim().isEmpty())
			throw new InvalidDataException();
		if(userinfo.getName()==null || userinfo.getName().trim().isEmpty())
			throw new InvalidDataException();
		
		if(userinfo.getUsername()==null || userinfo.getUsername().trim().isEmpty()) {
			userinfo.setUsername(user.getUsername());
		}
			
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
		user.setFavouritePosts(null);
		return user;
	}

	@Override
	public User update(User user) throws UserNotFoundException, InvalidDataException, RendezvousException {
		if(user.getUsername()==null|| user.getUsername().trim().isEmpty()) 
			throw new InvalidDataException();
		User updatedUser = dao.update(user);
		return updatedUser;
	}

	@Override
	public void invite(String emailIds) throws InvalidDataException, RendezvousException {
		for (String emailid: emailIds.split(",")) {
			if(emailid==null|| emailid.trim().isEmpty()) {
				throw new InvalidDataException();
			}
			Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
		    Matcher regMatcher   = regexPattern.matcher(emailid);
		    if(regMatcher.matches()){
		    	System.out.println("Email Invite sent to" + emailid);
		    } else {
		    	throw new InvalidDataException();
		    }
		}
		return;
		
	}

	@Override
	public Set<Post> getFavouritePosts(String username) throws UserNotFoundException, RendezvousException {
		if(username==null|| username.trim().isEmpty()) 
			throw new InvalidDataException();
		
		Set<Post> posts = dao.getFavouritePosts(username); 
		if(posts == null){
			throw new RendezvousException();
		}
		return posts;
	}

	@Override
	public void createPost(Post post) throws InvalidDataException, RendezvousException {
		if (post.getTitle() == null || post.getTitle().trim().isEmpty() ||
				post.getPostText() == null || post.getPostText().trim().isEmpty()) {
			throw new InvalidDataException();
		}
		post.setAbstractText(post.getPostText().substring(0, 100));
		post.setCreatedDate(new Date());
		post.setUpdatedDate(new Date());
		dao.createPost(post);
	}

	@Override
	public List<Comment> getComments(int post_id) throws PostNotFoundException, RendezvousException {
		List <Comment> comments = null;
		comments = dao.getComments(post_id);
		if (comments.isEmpty() ) {
			throw new PostNotFoundException();
		}
		else {
			return comments;
		}
	}

	@Override
	public List<Post> getPosts() {
		List<Post> allPosts = null;
		allPosts = dao.getPosts();
		if(allPosts.isEmpty()) {
			return null;
		}
		return allPosts;
	}

	@Override
	public List<Post> getPosts(int offset, int size) {
		List<Post> numPosts = null;
		numPosts = dao.getPosts(offset, size);
		if (numPosts.isEmpty()) {
			return null;
		}
		return numPosts;
	}

	@Override
	public Set<Post> getPostsByTag(int tag_id) throws TagNotFoundException, RendezvousException {
		Set<Post> postByTag = dao.getPostsByTag(tag_id);;
		if (postByTag == null) {
			throw new TagNotFoundException();
		}
		return postByTag;
	}

	@Override
	public List<Post> getPostsByInterest(Interest interest) throws InvalidInterestException, RendezvousException {
		//SHould add here to validate the interest
		List<Post> postByInt = dao.getPostsByInterest(interest);
		if(postByInt.isEmpty()) {
			throw new RendezvousException();
		}
		return postByInt;
	}

	@Override
	public Post getPost(int post_id) throws PostNotFoundException, RendezvousException {
		Post post = dao.getPost(post_id);
		if (post == null)  {
			throw new PostNotFoundException();
		}
		return post;
	}

	@Override
	public int getFavouritePostCount(int post_id) throws PostNotFoundException, RendezvousException {
		int size = dao.getFavouritePostCount(post_id);
		if (size == 0) {
			throw new PostNotFoundException();
		}
		return size;
	}

	@Override
	public void markFavourite(int post_id, String username)
			throws PostNotFoundException, UserNotFoundException, RendezvousException, InvalidDataException {
		if (post_id == 0 || username.trim().isEmpty() || username == null) {
			throw new InvalidDataException();
		}
		User user = dao.getUser(username);
		if (user == null) {
			throw new UserNotFoundException();
		} else {
			dao.markFavourite(post_id, username);
		}
		
	}

	@Override
	public void unMarkFavourite(int post_id, String username)
			throws PostNotFoundException, UserNotFoundException, RendezvousException, InvalidDataException {
		if (post_id == 0 || username.trim().isEmpty() || username == null) {
			throw new InvalidDataException();
		}
		User user = dao.getUser(username);
		if (user == null) {
			throw new UserNotFoundException();
		} else {
			dao.unMarkFavourite(post_id, username);
		}
		
	}

	@Override
	public List<Post> search(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createComment(int post_id, Comment comment)
			throws PostNotFoundException, InvalidDataException, RendezvousException {
		if(comment.getCommentText() == null || comment.getCommentText().trim().isEmpty() ||
				comment.getUserInfo().getUsername() == null || comment.getUserInfo().getUsername().trim().isEmpty() || post_id == 0) {
			throw new InvalidDataException();
		}
		comment.setCreatedDate(new Date());
		comment.setUpdatedDate(new Date());
		dao.createComment(post_id, comment);
		
	}

	@Override
	public void createMessage(Message message) throws InvalidDataException, RendezvousException {
		if (message.getMessageText() == null || message.getMessageText().trim().isEmpty() ||
				message.getUserInfo() == null) {
			throw new InvalidDataException();
		}
		message.setCreatedDate(new Date());
		dao.createMessage(message);
	}

	@Override
	public List<Message> getMessages() {
		List<Message> allMessage = dao.getMessages();
		return allMessage;
	}

	@Override
	public List<Message> getMessages(int offset, int size) {
		List<Message> numMessages = dao.getMessages(offset, size);
		return numMessages;
	}

	@Override
	public User getUserByUsername(String username)
			throws UserNotFoundException, InvalidDataException, RendezvousException {
		if(username==null|| username.trim().isEmpty()) 
			throw new InvalidDataException();
		
		User user = dao.getUser(username);
		
		if(user == null) 
			throw new UserNotFoundException();
		return user;
	}

	@Override
	public User getUserByEmail(String email) throws UserNotFoundException, InvalidDataException, RendezvousException {
		if(email==null|| email.trim().isEmpty()) 
			throw new InvalidDataException();
		
		User user = dao.getUserByEmail(email);
		
		if(user == null) 
			throw new UserNotFoundException();
		return user;
	}

	

}
