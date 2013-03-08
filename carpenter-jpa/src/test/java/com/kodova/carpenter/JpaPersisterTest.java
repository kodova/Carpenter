package com.kodova.carpenter;

import com.kodova.carpenter.example.entity.Post;
import com.kodova.carpenter.example.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static com.kodova.carpenter.Carpenter.props;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JpaPersisterTest {

	private EntityManager em;

	@Before
	public void setUp() throws Exception {
		em = Persistence.createEntityManagerFactory("CarpenterUnit").createEntityManager();
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
	@Ignore
	public void shouldPersistNested() throws Exception {
		Post post = Carpenter.create(Post.class);
		assertTrue(post.getId() > 0);
		assertNotNull(post.getUser());
		assertTrue(post.getUser().getId() > 0);
	}

}
