package com.cisco.cmad.rest;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cisco.cmad.api.InvalidDataException;
import com.cisco.cmad.api.Message;
import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.RendezvousException;
import com.cisco.cmad.api.Tag;
import com.cisco.cmad.biz.SimpleRendezvous;


@Path("/tag")
public class TagController {
	
	static Rendezvous rendezvous = new SimpleRendezvous();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public Response getMessages() {
		List<Tag> tags = rendezvous.getTags();
		return Response.ok().entity(tags).build();
	}

}
