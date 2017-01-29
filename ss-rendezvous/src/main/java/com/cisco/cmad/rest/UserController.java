package com.cisco.cmad.rest;

import java.util.Date;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.cisco.cmad.api.InvalidDataException;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.RendezvousException;
import com.cisco.cmad.api.User;
import com.cisco.cmad.api.UserAlreadyExistsException;
import com.cisco.cmad.api.UserNotFoundException;
import com.cisco.cmad.biz.SimpleRendezvous;

@Path("/user")
public class UserController {
	
	static Rendezvous rendezvous = new SimpleRendezvous();
	
	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validateUsername")
	public Response validateUsername(@QueryParam(value = "username") String username) {
		User a = null;
		try {
			a = rendezvous.getUserByUsername(username);
		} catch (UserNotFoundException e) {
			return Response.ok().build();
		} catch (InvalidDataException e) {
			return Response.status(400).build();
		} catch (RendezvousException e) {
			return Response.status(400).build();
		}
		return Response.status(404).build();
	}
	
	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validateEmail")
	public Response validateEmail(@QueryParam(value = "email") String email) {
		User a = null;
		try {
			a = rendezvous.getUserByEmail(email);
		} catch (UserNotFoundException e) {
			return Response.ok().build();
		} catch (InvalidDataException e) {
			return Response.status(500).build();
		} catch (RendezvousException e) {
			return Response.status(500).build();
		}
		return Response.status(404).build();
	}
	
	@PermitAll
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/register/")
	public Response register(User user) {
		Date date = new Date();
		user.setCreatedDate(date);
		user.setUpdatedDate(date);
		try {
			rendezvous.register(user);
		} catch (UserAlreadyExistsException e) {
			e.printStackTrace();
		} catch (RendezvousException e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}
	
	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public Response login(@Context HttpServletRequest request,@QueryParam(value = "username") String username, @QueryParam(value = "password")String password) {
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
		Date date = new Date();
		a.setLastLoginDate(date);
		a.setLastLoginIP(request.getRemoteAddr());
		try {
			a.setUpdatedDate(date);
			rendezvous.update(a);
		} catch (RendezvousException e) {
			e.printStackTrace();
		}
		return Response.ok().entity(a).build();
	}
	
	@GET
	@Path("/home")
	public Viewable home(@Context HttpServletRequest request) {
		Viewable view = new Viewable("/home.jsp");
		return view;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update")
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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/invite/")
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
	@Path("/favouritePosts/{username}")
	public Response getFavouritePosts(@PathParam("username") String username) {
		
		Set<Post> posts = null;
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
