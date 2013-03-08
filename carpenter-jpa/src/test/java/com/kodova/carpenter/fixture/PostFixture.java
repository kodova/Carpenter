package com.kodova.carpenter.fixture;

import com.kodova.carpenter.Fixture;
import com.kodova.carpenter.example.entity.Post;
import com.kodova.carpenter.example.entity.User;

public class PostFixture extends Fixture<Post> {
	@Override
	public void configure(Post entity) {
		entity.setTitle("Foo");
		entity.setContent("Bar");
		entity.setSlug("foo");
		entity.setUser(get(User.class));
	}
}
