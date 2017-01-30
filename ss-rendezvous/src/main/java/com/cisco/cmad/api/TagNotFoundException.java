package com.cisco.cmad.api;

public class TagNotFoundException extends RendezvousException {

	public TagNotFoundException() {
		super("No such tag found");
		
	}

	public TagNotFoundException(String errMsg) {
		super(errMsg);
	}

	
}
