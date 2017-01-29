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
import com.cisco.cmad.biz.SimpleRendezvous;


@Path("/message")
public class MessageController {
	
	static Rendezvous rendezvous = new SimpleRendezvous();
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/create/")
	public Response createMessage(Message message) {
		try {
			rendezvous.createMessage(message);
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
	@Path("/all")
	public Response getMessages(@QueryParam(value = "offset") int offset, @QueryParam(value = "size")int size) {
		List<Message> messages = rendezvous.getMessages(offset,size);
		return Response.ok().entity(messages).build();
	}

}
