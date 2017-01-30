package com.cisco.cmad.api;

public class UserNotFoundException extends RendezvousException {

	public UserNotFoundException() {
		super("User doesn't exist");
	}

	public UserNotFoundException(String errMsg) {
		super(errMsg);
	}

}
