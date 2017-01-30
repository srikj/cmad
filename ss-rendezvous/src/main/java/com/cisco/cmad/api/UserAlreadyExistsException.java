package com.cisco.cmad.api;

public class UserAlreadyExistsException extends RendezvousException {

	public UserAlreadyExistsException() {
		super("User already exist");
	}

	public UserAlreadyExistsException(String errMsg) {
		super(errMsg);
	}

}
