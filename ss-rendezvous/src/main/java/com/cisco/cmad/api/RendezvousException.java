package com.cisco.cmad.api;

public class RendezvousException extends Exception {
	
	public RendezvousException(){
		super("Rendezvous exception occured");
	}
	
	public RendezvousException(String errMsg) {
		super(errMsg);
	}
	
}
