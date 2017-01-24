package com.cisco.cmad.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cisco.cmad.api.InvalidDataException;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.RendezvousException;
import com.cisco.cmad.api.User;
import com.cisco.cmad.api.UserAlreadyExistsException;
import com.cisco.cmad.api.UserNotFoundException;
import com.cisco.cmad.biz.SimpleRendezvous;

@Path("/rendezvous")
public class UserController {
	
	static Rendezvous rendezvous = new SimpleRendezvous();
	
	@POST
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
		User a = null;
		try {
			a = rendezvous.login(username, password);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		try {
			rendezvous.invite(emailIds);
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/favouritePosts/{username}")
	public Response getFavouritePosts(@PathParam("username") String username) {
		
		List<Post> posts = null;
		try {
			posts = rendezvous.getFavouritePosts(username);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().entity(posts).build();
	}

}
