package com.kodova.carpenter.fixture;

import com.kodova.carpenter.example.entity.Tag;

public class TagFixture extends PartialFixture<Tag> {
	@Override
	public void configure(Tag entity) {
		entity.setName("Tag foo");
		entity.setSlug("tag-foo");
	}

	@Override
	public String[] requiredProperties() {
		return requiredList("");
	}

}
