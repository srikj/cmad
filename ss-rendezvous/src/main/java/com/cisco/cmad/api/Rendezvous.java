package com.cisco.cmad.api;

import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.client.FindIterable;

public interface Rendezvous {

	public void register(User user) throws UserAlreadyExistsException, InvalidDataException, RendezvousException;
	
	public boolean login(String username, String password) throws UserNotFoundException, InvalidDataException, RendezvousException;
	
	public Document update(User user) throws UserNotFoundException, InvalidDataException, RendezvousException;
	
	public Document getUserByUsername(String username) throws UserNotFoundException, InvalidDataException, RendezvousException;
	
	public Document getUserByEmail(String email) throws UserNotFoundException, InvalidDataException, RendezvousException;

	public void invite(String emailIds) throws InvalidDataException, RendezvousException;
	
	public FindIterable<Document> getFavouritePosts(String username) throws UserNotFoundException,RendezvousException;
	
	public void createPost(Post post) throws InvalidDataException,RendezvousException;
	
	public List<String> getComments(String post_id) throws PostNotFoundException,RendezvousException;
	
	public Object getPosts() ;
	
	public FindIterable<Document> getPosts(int offset, int size) ;
	
	public FindIterable<Document> getPostsByTag(String tag) throws TagNotFoundException,RendezvousException;
	
	public FindIterable<Document> getPostsByInterest(Interest interest) throws InvalidInterestException,RendezvousException;
	
	public FindIterable<Document> getPost(String post_id) throws PostNotFoundException,RendezvousException;
	
	//public int getFavouritePostCount(int post_id) throws PostNotFoundException,RendezvousException;
	
	public void markFavourite(String post_id,String username) throws PostNotFoundException,UserNotFoundException, RendezvousException, InvalidDataException;
	
	public void unMarkFavourite(String post_id,String username) throws PostNotFoundException,UserNotFoundException, RendezvousException, InvalidDataException;
	
	public FindIterable<Document> search(String key);
	
	public void createComment(String post_id, Comment comment) throws PostNotFoundException, InvalidDataException, RendezvousException;
	
	public void createMessage(Message message) throws InvalidDataException, RendezvousException;
	
	public FindIterable<Document> getMessages() ;
	
	public FindIterable<Document> getMessages(int offset, int size) ;
	
	public List<String> getTags() ;
	
	
}
