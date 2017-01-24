package com.cisco.cmad.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cisco.cmad.api.Comment;
import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.InvalidDataException;
import com.cisco.cmad.api.InvalidInterestException;
import com.cisco.cmad.api.Post;
import com.cisco.cmad.api.PostNotFoundException;
import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.RendezvousException;
import com.cisco.cmad.api.TagNotFoundException;
import com.cisco.cmad.api.UserNotFoundException;
import com.cisco.cmad.biz.SimpleRendezvous;

@Path("/rendezvous")
public class PostController {
	
	static Rendezvous rendezvous = new SimpleRendezvous();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/post")
	public Response createPost(Post post) {
		try {
			rendezvous.createPost(post);
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
	@Path("/post/{postid}/comments")
	public Response getComments(@PathParam(value = "postid") int post_id) {
		List<Comment> comments = null;
		try {
			comments = rendezvous.getComments(post_id);
		} catch (PostNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().entity(comments).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/posts")
	public Response getPosts() {
		
		List<Post> posts = rendezvous.getPosts();
		
		return Response.ok().entity(posts).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/posts")
	public Response getPosts(@QueryParam(value = "start") int offset, @QueryParam(value = "size") int size) {
		
		List<Post> posts = rendezvous.getPosts(offset,size);
		
		return Response.ok().entity(posts).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/posts")
	public Response getPostsByTag(@QueryParam(value = "tag")int tag_id) {
		
		List<Post> posts = null;
		try {
			posts = rendezvous.getPostsByTag(tag_id);
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().entity(posts).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/posts")
	public Response getPostsByInterest(@QueryParam(value = "interest") Interest interest) {
		List<Post> posts = null;
		try {
			posts = rendezvous.getPostsByInterest(interest);
		} catch (InvalidInterestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().entity(posts).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/post/{postid}")
	public Response getPost(@PathParam(value = "postid") int post_id) {
		Post post = null;
		try {
			post = rendezvous.getPost(post_id);
		} catch (PostNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().entity(post).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/post/favouritedUsers/{postid}")
	public Response getFavouritePostCount(@PathParam(value = "postid") int post_id) {
		int count = 0;
		try {
			count = rendezvous.getFavouritePostCount(post_id);
		} catch (PostNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().entity(count).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/post/markFav/")
	public Response markFavourite(@FormParam(value = "postid") int post_id,@FormParam(value = "username") String username) {
		try {
			rendezvous.markFavourite(post_id, username);
		} catch (PostNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok().build();
	}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/post/unmarkFav/")
	public Response unMarkFavourite(@FormParam (value = "postid") int post_id,@FormParam(value = "username") String username) {
		try {
			rendezvous.unMarkFavourite(post_id, username);
		} catch (PostNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
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
	@Path("/posts")
	public Response search(@QueryParam(value = "search") String key) {
		
		List<Post> posts = rendezvous.search(key);
		return Response.ok().entity(posts).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/post/{postid}/comment")
	public Response createComment(@PathParam(value = "postid") int post_id,Comment comment) {
		
		try {
			rendezvous.createComment(post_id, comment);
		} catch (PostNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendezvousException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.ok().build();
	}

}
