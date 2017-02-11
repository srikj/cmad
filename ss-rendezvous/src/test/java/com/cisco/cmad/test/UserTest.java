package com.cisco.cmad.test;

import org.junit.Assert;
import org.junit.Test;

import com.cisco.cmad.api.Interest;
import com.cisco.cmad.api.InvalidDataException;
import com.cisco.cmad.api.Rendezvous;
import com.cisco.cmad.api.RendezvousException;
import com.cisco.cmad.api.User;
import com.cisco.cmad.api.UserAlreadyExistsException;
import com.cisco.cmad.api.UserInfo;
import com.cisco.cmad.biz.SimpleRendezvous;

public class UserTest {
	
	@Test
	public void addUser() throws UserAlreadyExistsException {
		Rendezvous rend = new SimpleRendezvous();
		User user = new User();
		UserInfo userInf = new UserInfo();
		userInf.setEmail("subh@cisco.com");
		userInf.setInterest(Interest.MOVIES);
		userInf.setName("Subhransu");
		userInf.setPhoneNumber("9986459679");
		
		user.setUsername("subchhot");
		user.setPassword("1234");
		user.setUserInfo(userInf);
		
		try {
			rend.register(user);
			System.out.println("Successfully registerd the user");
			return;
		}catch (InvalidDataException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (RendezvousException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	@Test
	public void updateUser() {
		
	}
	

}
