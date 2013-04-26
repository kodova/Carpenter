package com.kodova.carpenter.fixture;

import com.kodova.carpenter.example.entity.Comment;
import com.kodova.carpenter.example.entity.User;

import java.util.Date;

public class CommentFixture extends PartialFixture<Comment> {
	@Override
	public void configure(Comment entity) {
		entity.setContent("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt");
		entity.setUser(get(User.class));
		entity.setUpdatedAt(new Date());
	}

	@Override
	public String[] requiredProperties() {
		return requiredList("post");
	}
}
