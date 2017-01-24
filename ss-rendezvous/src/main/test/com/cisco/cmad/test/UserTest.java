package com.cisco.cmad.test;

import org.junit.Test;

import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.RendezvousException;
import com.cisco.cmad.api.User;
import com.cisco.cmad.biz.SimpleRendezvous;

public class UserTest {
	
	@Test
	public void addUser() {
		Rendezvous rend = new SimpleRendezvous();
		User user = new User();
		try {
			rend.register(user);
			System.out.println("Hello");
		} catch (RendezvousException e) {
			e.printStackTrace();
		}
	}

}
