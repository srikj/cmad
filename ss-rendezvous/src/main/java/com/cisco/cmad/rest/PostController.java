package com.cisco.cmad.rest;

import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
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

import org.bson.Document;

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
import com.mongodb.client.FindIterable;

@Path("/post")
public class PostController {
	
	static Rendezvous rendezvous = new SimpleRendezvous();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/create")
	public Response createPost(Post post) {
		try {
			rendezvous.createPost(post);
		} catch (InvalidDataException e) {
			e.printStackTrace();
			return Response.status(404).entity("Invalid post title/content").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(400).entity("New post could not be created").build();
		}
		return Response.ok().build();
	}

	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{postid}/comments")
	public Response getComments(@PathParam(value = "postid") int post_id) {
		List<Comment> comments = null;
		try {
			comments = rendezvous.getComments(post_id);
		} catch (PostNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).entity("No such post found").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(404).entity("No comments found for this post").build();
		}
		return Response.ok().entity(comments).build();
	}

	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public FindIterable<Document> getPosts() {
		
		FindIterable<Document> posts = (FindIterable<Document>) rendezvous.getPosts();
		
		return posts;
	}
	
	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/paged")
	public Response getPosts(@QueryParam(value = "start") int offset, @QueryParam(value = "size") int size) {
		
		List<Post> posts = rendezvous.getPosts(offset,size);
		
		return Response.ok().entity(posts).build();
	}
	
	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/postsByTag")
	public Response getPostsByTag(@QueryParam(value = "tag")int tag_id) {
		
		Set<Post> posts = null;
		try {
			posts = rendezvous.getPostsByTag(tag_id);
		} catch (TagNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).entity("Invalid tag").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(404).entity("Post not found for this tag").build();
		}
		return Response.ok().entity(posts).build();
	}

	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/postsByTopic")
	public Response getPostsByInterest(@QueryParam(value = "topic") Interest interest) {
		List<Post> posts = null;
		try {
			posts = rendezvous.getPostsByInterest(interest);
		} catch (InvalidInterestException e) {
			e.printStackTrace();
			return Response.status(404).entity("Interest Entered is not valid").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(500).entity("No post found for this interest").build();

		}
		return Response.ok().entity(posts).build();
	}

	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{postid}")
	public Response getPost(@PathParam(value = "postid") int post_id) {
		Post post = null;
		try {
			post = rendezvous.getPost(post_id);
		} catch (PostNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).entity("No such post found").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(500).entity("Invalid post title/content").build();
		}
		return Response.ok().entity(post).build();
	}

	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/favouritedUsers/{postid}")
	public Response getFavouritePostCount(@PathParam(value = "postid") int post_id) {
		int count = 0;
		try {
			count = rendezvous.getFavouritePostCount(post_id);
		} catch (PostNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).entity("Invalid post title/content").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(500).entity("Invalid operation").build();
		}
		return Response.ok().entity(count).build();
	}

	@POST
	@Path("/markFav/{post_id}/{username}")
	public Response markFavourite(@PathParam(value="post_id") int post_id,@PathParam(value = "username")String username) {
		try {
			rendezvous.markFavourite(post_id, username);
		} catch (PostNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).entity(" No such Post").build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return Response.status(401).entity("This user doesn't exist").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(500).entity("Invalid server error").build();
		}
		return Response.ok().build();
	}
	@POST
	@Path("/unmarkFav/{post_id}/{username}")
	public Response unMarkFavourite(@PathParam(value="post_id") int post_id,@PathParam(value = "username")String username) {
		try {
			rendezvous.unMarkFavourite(post_id, username);
		} catch (PostNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).entity(" No such Post").build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return Response.status(401).entity("This user doesn't exist").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(500).entity("Invalid Server Error").build();
		}
		return Response.ok().build();
	}

	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public Response search(@QueryParam(value = "search") String key) {
		
		List<Post> posts = rendezvous.search(key);
		return Response.ok().entity(posts).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{postid}/comment")
	public Response createComment(@PathParam(value = "postid") int post_id,Comment comment) {
		
		try {
			rendezvous.createComment(post_id, comment);
		} catch (PostNotFoundException e) {
			e.printStackTrace();
			return Response.status(404).entity(" No such Post").build();
		} catch (InvalidDataException e) {
			e.printStackTrace();
			return Response.status(400).entity("Entered Data is invalid").build();
		} catch (RendezvousException e) {
			e.printStackTrace();
			return Response.status(500).entity("Invalid Server Error").build();
		}
		
		return Response.ok().build();
	}

}
