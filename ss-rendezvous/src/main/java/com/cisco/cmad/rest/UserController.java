package com.cisco.cmad.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.bson.Document;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Rendezvous rendezvous = new SimpleRendezvous();
	
	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validateUsername")
	public Response validateUsername(@QueryParam(value = "username") String username) {
		Document a = null;
		try {
			a = rendezvous.getUserByUsername(username);
		} catch (UserNotFoundException e) {
			return Response.ok().build();
		} catch (InvalidDataException e) {
			return Response.status(400).entity("Username can't be empty").build();
		} catch (RendezvousException e) {
			return Response.status(400).entity("Invalid server error").build();
		}
		return Response.status(404).build();
	}
	
	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validateEmail")
	public Response validateEmail(@QueryParam(value = "email") String email) {
		Document a = null;
		try {
			a = rendezvous.getUserByEmail(email);
		} catch (UserNotFoundException e) {
			return Response.ok().build();
		} catch (InvalidDataException e) {
			return Response.status(500).entity("Invlid email address given").build();
		} catch (RendezvousException e) {
			return Response.status(500).entity("Invalid server error").build();
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
			return Response.status(500).entity("Invalid server error").build();
		}
		return Response.ok().build();
	}
	
	@PermitAll
	@GET
	@Path("/login")
	public Response login(@Context HttpServletRequest request,@QueryParam(value = "username") String username, @QueryParam(value = "password")String password) throws URISyntaxException {
		User a = null;
		try {
			a = rendezvous.login(username, password);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity("Invalid server error").build();
		}
		Date date = new Date();
		a.setLastLoginDate(date);
		a.setLastLoginIP(request.getRemoteAddr());
		try {
			a.setUpdatedDate(date);
			rendezvous.update(a);
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(500).entity("Invalid server error").build();
		}
		String auth = username+":"+password;
		HttpSession session = request.getSession();
		session.setAttribute("username", a.getUsername());
		session.setAttribute("auth", Base64.getEncoder().encodeToString(auth.getBytes()));
		session.setMaxInactiveInterval(30*60);
//		NewCookie[] cookies = new NewCookie[2];
//		cookies[0] = new NewCookie(new NewCookie("username", a.getUsername()), "", 30*60, false);
//		cookies[1] = new NewCookie(new NewCookie("auth", Base64.getEncoder().encodeToString(auth.getBytes())), "", 30*60, false);
		//setting cookie to expiry in 30 mins
		return Response.temporaryRedirect(new URI("/rendezvous/home.jsp")).build();
	}
	
	@PermitAll
	@GET
	@Path("/logout")
	public Response logout(@Context HttpServletRequest request) throws URISyntaxException {
		
		HttpSession session = request.getSession(false);
    	if(session != null){
    		session.invalidate();
    	}
		return Response.temporaryRedirect(new URI("/rendezvous/index.jsp")).build();
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
			e.printStackTrace();
			return Response.status(404).entity("Given user doesn't exist").build();
		} catch (InvalidDataException e) {
			e.printStackTrace();
			return Response.status(400).entity("Invalid data entered ").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(500).entity("Invalid server error").build();
		}
		return Response.ok().entity(updatedUser).build();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/invite/")
	public Response invite(@QueryParam(value="emailids") String emailIds) {
		
		try {
			rendezvous.invite(emailIds);
		} catch (InvalidDataException e) {
			e.printStackTrace();
			return Response.status(400).entity("Invalid data entered ").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(500).entity("Invalid server error").build();
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
			e.printStackTrace();
			return Response.status(404).entity("Given user doesn't exist").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(500).entity("Invalid server error").build();
		}
		return Response.ok().entity(posts).build();
	}
	
	

}
