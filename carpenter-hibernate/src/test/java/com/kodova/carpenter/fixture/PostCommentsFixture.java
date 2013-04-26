package com.kodova.carpenter.fixture;

import com.kodova.carpenter.Properties;
import com.kodova.carpenter.example.entity.Comment;
import com.kodova.carpenter.example.entity.Post;

import static com.kodova.carpenter.Carpenter.props;

@Name("with comment")
public class PostCommentsFixture extends Fixture<Post> {


	@Override
	public void configure(Post entity) {
		composite(entity, Post.class);

		Properties p = props().set("post", entity);
		Comment comment = get(Comment.class, p);
		entity.getComments().add(comment);
	}
}
