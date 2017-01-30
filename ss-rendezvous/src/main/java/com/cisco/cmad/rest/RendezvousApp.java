package com.cisco.cmad.rest;

import org.glassfish.jersey.server.ResourceConfig;


public class RendezvousApp extends ResourceConfig 
{
	public RendezvousApp() 
	{
		packages("com.cisco.cmad.rest");

		//Register Auth Filter here
		register(AuthenticationFilter.class);
	}
}