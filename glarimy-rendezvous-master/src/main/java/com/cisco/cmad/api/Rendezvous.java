package com.cisco.cmad.api;

import java.util.List;

public interface Rendezvous {

	public void register(User user) throws UserAlreadyExistsException, InvalidDataException, RendezvousException;
	
	public User login(String username, String password) throws UserNotFoundException, RendezvousException;
	
	public User update(User user) throws UserNotFoundException, InvalidDataException, RendezvousException;

	public void invite(String emailIds);
	
	public List<Post> getFavouritePosts(String username);
	
	public void createPost(Post post);
	
	public List<Comment> getComments(int post_id);
	
	public List<Post> getPosts(int number);
	
	public List<Post> getPostsByTag(int tag_id);
	
	public List<Post> getPostsByInterest(Interest interest);
	
	public Post getPost(int post_id);
	
	public int getFavouritePostCount(int post_id);
	
	public void markFavourite(int post_id,String username);
	
	public void unMarkFavourite(int post_id,String username);
	
	public List<Post> search(String key);
	
	public void createComment(Comment comment);
	
	public void createMessage(Message message);
	
	public List<Message> getMessages(int number);
	
	
}
