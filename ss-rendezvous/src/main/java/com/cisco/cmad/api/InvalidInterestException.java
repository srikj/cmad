package com.cisco.cmad.api;

public class InvalidInterestException extends RendezvousException {

	public InvalidInterestException() {
		super("Invlid interest option ");
	}

	public InvalidInterestException(String errMsg) {
		super(errMsg);
	}

}
