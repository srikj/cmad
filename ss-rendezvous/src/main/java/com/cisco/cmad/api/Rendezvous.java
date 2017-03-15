package com.cisco.cmad.api;

import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.client.FindIterable;

public interface Rendezvous {

	public void register(User user) throws UserAlreadyExistsException, InvalidDataException, RendezvousException;
	
	public User login(String username, String password) throws UserNotFoundException, InvalidDataException, RendezvousException;
	
	public User update(User user) throws UserNotFoundException, InvalidDataException, RendezvousException;
	
	public Document getUserByUsername(String username) throws UserNotFoundException, InvalidDataException, RendezvousException;
	
	public Document getUserByEmail(String email) throws UserNotFoundException, InvalidDataException, RendezvousException;

	public void invite(String emailIds) throws InvalidDataException, RendezvousException;
	
	public Set<Post> getFavouritePosts(String username) throws UserNotFoundException,RendezvousException;
	
	public void createPost(Post post) throws InvalidDataException,RendezvousException;
	
	public List<Comment> getComments(int post_id) throws PostNotFoundException,RendezvousException;
	
	public Object getPosts() ;
	
	public List<Post> getPosts(int offset, int size) ;
	
	public Set<Post> getPostsByTag(int tag_id) throws TagNotFoundException,RendezvousException;
	
	public List<Post> getPostsByInterest(Interest interest) throws InvalidInterestException,RendezvousException;
	
	public Post getPost(int post_id) throws PostNotFoundException,RendezvousException;
	
	public int getFavouritePostCount(int post_id) throws PostNotFoundException,RendezvousException;
	
	public void markFavourite(int post_id,String username) throws PostNotFoundException,UserNotFoundException, RendezvousException, InvalidDataException;
	
	public void unMarkFavourite(int post_id,String username) throws PostNotFoundException,UserNotFoundException, RendezvousException, InvalidDataException;
	
	public List<Post> search(String key);
	
	public void createComment(int post_id, Comment comment) throws PostNotFoundException, InvalidDataException, RendezvousException;
	
	public void createMessage(Message message) throws InvalidDataException, RendezvousException;
	
	public List<Message> getMessages() ;
	
	public List<Message> getMessages(int offset, int size) ;
	
	public List<Tag> getTags() ;
	
	
}
