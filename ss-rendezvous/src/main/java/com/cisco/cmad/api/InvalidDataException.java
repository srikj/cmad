package com.cisco.cmad.api;

public class InvalidDataException extends RendezvousException {

	public InvalidDataException() {
		super("Invalid Data Enterered");
	}
	
	public InvalidDataException(String errMsg) {
		super(errMsg);
	}
}
