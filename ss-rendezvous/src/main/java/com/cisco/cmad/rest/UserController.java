package com.cisco.cmad.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.cisco.cmad.api.Comment;
import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.InvalidDataException;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.RendezvousException;
import com.cisco.cmad.api.Tag;
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/login")
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
			rendezvous.update(a);
		} catch (RendezvousException e) {
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
		
		String[] emailArray = emailIds.split(",");
		for(String email : emailArray) {
			//invite people
		}
		
		return Response.ok().build();
		
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/favouritePosts/{username}")
	public Response getFavouritePosts(@PathParam("username") String username) {
		User a = new User();
		a.setName("Srikanth Janardhan");
		a.setEmail("srikj@cisco.com");
		a.setInterest(Interest.MOVIES);
		a.setPassword("hello");
		a.setPhoneNumber(new Long(9731938514l));
		a.setUsername("srikanthj");
		
		User b = new User();
		b.setName("Subransu Chhotaray");
		b.setEmail("subchhot@cisco.com");
		b.setInterest(Interest.SPORTS);
		b.setPassword("hello");
		b.setPhoneNumber(new Long(9731938514l));
		b.setUsername("subchhot");
		
		Set<Tag> tags = new HashSet<>();
		tags.add(new Tag(1, "Movies", new HashSet<>()));
		tags.add(new Tag(1, "Tech", new HashSet<>()));
		
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		
		Post p1 = new Post();
		
		List<Comment> comments = new ArrayList<>();
		comments.add(new Comment(1, "Great post!", "srikanthj", dateobj, dateobj));
		comments.add(new Comment(2, "Thanks", "subchhot", dateobj, dateobj));
		
		p1.setAbstractText("Hello World");
		p1.setComments(comments);
		p1.setCreatedDate(dateobj);
		p1.setFavouritedUsers(new HashSet<>());
		p1.getFavouritedUsers().add(a);
		p1.getFavouritedUsers().add(b);
		p1.setInterest(Interest.MOVIES);
		p1.setPost_id(1);
		p1.setPostText("Hellow World, Welcome to our blog!");
		p1.setTags(tags);
		p1.setTitle("Hello");
		p1.setUpdatedDate(dateobj);
		p1.setUser("srikanthj");
		
		Post p2 = new Post();
		
		List<Comment> commentsByUserb = new ArrayList<>();
		commentsByUserb.add(new Comment(1, "Great post!", "srikanthj",dateobj, dateobj));
		commentsByUserb.add(new Comment(2, "Thanks", "subchhot", dateobj, dateobj));
		
		p2.setAbstractText("Hey Guys");
		p2.setComments(commentsByUserb);
		p2.setCreatedDate(dateobj);
		p2.setFavouritedUsers(new HashSet<>());
		p2.getFavouritedUsers().add(a);
		p2.setInterest(Interest.TECHNOLOGY);
		p2.setPost_id(1);
		p2.setPostText("Hey Guys, This is a new question to you");
		p2.setTags(tags);
		p2.setTitle("Hello");
		p2.setUpdatedDate(dateobj);
		p2.setUser("subchhot");
		
		List<Post> posts = new ArrayList<>();
		posts.add(p1);
		posts.add(p2);
		
		return Response.ok().entity(posts).build();
	}

}
