package com.cisco.cmad.data;

import java.util.List;
import java.util.Set;

import com.cisco.cmad.api.Comment;
import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.Message;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.Tag;
import com.cisco.cmad.api.User;

public interface DAO {
	public void createUser(User user);

	public User getUser(String username) ;
	
	public User getUserByEmail(String email) ;

	public User update(User user) ;

	public Set<Post> getFavouritePosts(String username); 

	public int createPost(Post post) ;

	public List<Comment> getComments(int post_id); 

	public List<Post> getPosts() ;
	
	public List<Post> getPosts(int offset, int size) ;

	public Set<Post> getPostsByTag(int tag_id); 

	public List<Post> getPostsByInterest(Interest interest);

	public Post getPost(int post_id) ;

	public int getFavouritePostCount(int post_id) ;

	public void markFavourite(int post_id, String username) ;

	public void unMarkFavourite(int post_id, String username) ;

	public List<Post> search(String key) ;

	public void createComment(int post_id,Comment comment) ;

	public void createMessage(Message message) ;

	public List<Message> getMessages() ;
	
	public List<Message> getMessages(int offset, int size) ;
	
	public List<Tag> getTags();
}
