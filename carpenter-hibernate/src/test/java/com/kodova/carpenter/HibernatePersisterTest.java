package com.kodova.carpenter;

import com.kodova.carpenter.example.entity.Post;
import com.kodova.carpenter.example.entity.User;
import com.kodova.carpenter.suite.DatabaseSuite;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.kodova.carpenter.Carpenter.props;
import static org.junit.Assert.*;

public class HibernatePersisterTest {

	private Session session;

	@Before
	public void setUp() throws Exception {
		session = DatabaseSuite.getSession();
		session.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		session.getTransaction().rollback();
	}

	@Test
	public void shouldPersistUser() throws Exception {
		User user = Carpenter.create(User.class);
		assertFalse(user.isAdmin());
	}

	@Test
	public void shouldPersistUserWithOverrides() throws Exception {
		User user = Carpenter.create(User.class, props().set("admin", true));
		assertTrue(user.isAdmin());
	}

	@Test
	public void shouldPersistNested() throws Exception {
		Post post = Carpenter.create(Post.class);
		assertTrue(post.getId() > 0);
		assertNotNull(post.getUser());
		assertTrue(post.getUser().getId() > 0);
	}

	@Test
	public void shouldPersistByNameAdminUser() throws Exception {
		User user = Carpenter.create(User.class, "admin");
		assertTrue(user.isAdmin());
		assertTrue("The entity manager does not contain the persisted user", session.contains(user));
	}

	@Test
	public void shouldPersistPostWithComments() throws Exception {
		Post post = Carpenter.create(Post.class, "with comment");
		assertTrue("Post does not have a comment", post.getComments().size() > 0);
	}

}
