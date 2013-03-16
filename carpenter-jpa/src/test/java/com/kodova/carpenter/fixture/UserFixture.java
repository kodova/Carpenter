package com.kodova.carpenter.fixture;

import com.kodova.carpenter.example.entity.User;

public class UserFixture extends Fixture<User>{

	@Override
	public void configure(User entity) {
		entity.setFirstName("Duff");
		entity.setLastName("Mann");
		entity.setEmail("duff.mann01@example.com");
	}

}
