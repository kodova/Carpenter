package com.kodova.carpenter.entity;

import com.kodova.carpenter.Fixture;

/**
 *
 */
public class UserFixture extends Fixture<User> {

	@Override
	public void configure(User entity) {
		entity.setEmail("duff.mann@example.com");
		entity.setFirstName("Duff");
		entity.setLastName("Mann");
		entity.setAdmin(false);
	}
}
