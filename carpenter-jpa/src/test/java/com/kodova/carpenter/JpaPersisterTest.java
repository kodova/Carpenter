package com.kodova.carpenter;

import com.kodova.carpenter.example.entity.Post;
import com.kodova.carpenter.example.entity.User;
import com.kodova.carpenter.suite.DatabaseSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import static com.kodova.carpenter.Carpenter.props;
import static org.junit.Assert.*;

public class JpaPersisterTest {

	private EntityManager em;

	@Before
	public void setUp() throws Exception {
		em = DatabaseSuite.getEntityManager();
		em.getTransaction().begin();
		Carpenter.fixturePackage("com.kodova.carpenter.fixture");
		Carpenter.persister(new JpaPersister(em));
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().rollback();
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

}
