package com.kodova.carpenter.fixture;

import com.kodova.carpenter.example.entity.User;

@Name("admin")
public class AdminUserFixture extends Fixture<User>{

	@Override
	public void configure(User entity) {
		composite(entity, User.class);
		entity.setAdmin(true);
	}

}
