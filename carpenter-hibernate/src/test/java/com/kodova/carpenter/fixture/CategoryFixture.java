package com.kodova.carpenter.fixture;

import com.kodova.carpenter.example.entity.Category;

public class CategoryFixture extends Fixture<Category> {
	@Override
	public void configure(Category entity) {
		entity.setName("Foo Bar");
		entity.setSlug("foo-bar");
	}
}
