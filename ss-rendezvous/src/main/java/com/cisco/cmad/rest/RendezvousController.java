package com.cisco.cmad.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cisco.cmad.api.Comment;
import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.InvalidDataException;
import com.cisco.cmad.api.Message;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.RendezvousException;
import com.cisco.cmad.api.User;
import com.cisco.cmad.api.UserAlreadyExistsException;
import com.cisco.cmad.api.UserNotFoundException;
import com.cisco.cmad.biz.SimpleRendezvous;


@Path("/rendezvous")
public class RendezvousController {
	
	static Rendezvous rendezvous = new SimpleRendezvous();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/user/register/")
	public Response register(User user) {
		try {
			rendezvous.register(user);
		} catch (UserAlreadyExistsException e) {
			e.printStackTrace();
		} catch (RendezvousException e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/login")
	public Response login(@QueryParam(value = "username") String username, @QueryParam(value = "password")String password) {
		User a = new User();
		a.setName("Srikanth Janardhan");
		a.setEmail("srikj@cisco.com");
		a.setInterest(Interest.MOVIES);
		a.setPassword("hello");
		a.setPhoneNumber(new Long(9731938514l));
		a.setUsername("srikanthj");
		return Response.ok().entity(a).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/user/update")
	public Response update(User user) {
		User updatedUser = null;
		try {
			updatedUser = rendezvous.update(user);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().entity(updatedUser).build();
	}

	public Response invite(String emailIds) {
		// TODO Auto-generated method stub
		return Response.ok().build();
		
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
