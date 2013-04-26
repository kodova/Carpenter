package com.kodova.carpenter.fixture;

import com.kodova.carpenter.example.entity.User;
import com.kodova.carpenter.progression.Sequence;
import com.kodova.carpenter.progression.Sequencer;

public class UserFixture extends Fixture<User>{

	Sequence<Integer> email = Sequencer.createInt();

	@Override
	public void configure(User entity) {
		entity.setFirstName("Duff");
		entity.setLastName("Mann");
		entity.setEmail(email.get("duff.mann%s@example.com"));
	}
}
