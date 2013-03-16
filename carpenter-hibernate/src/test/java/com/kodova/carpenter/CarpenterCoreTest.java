package com.kodova.carpenter;

import com.kodova.carpenter.entity.FixturelessEntity;
import com.kodova.carpenter.entity.User;
import com.kodova.carpenter.entity.UserFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarpenterCoreTest {

	@Mock
	Persister persister;
	CarpenterCore carpenterCore;

	@Before
	public void setUp() throws Exception {
		carpenterCore = new CarpenterCore();
		carpenterCore.setPersister(persister);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(expected = FixtureNotFound.class)
	public void shouldNotBuildDueToMissingFixture() throws Exception {
		carpenterCore.build(FixturelessEntity.class, FixturelessEntity.class.getName());
	}

	@Test
	public void shouldBuildEntityWithDefaultName() throws Exception {
		carpenterCore.addFixture(new UserFixture());
		String name = User.class.getName();

		User user = carpenterCore.build(User.class, name);

		assertNotNull(user);
	}

	@Test
	public void shouldBuildEntityFromName() throws Exception {
		final String name = "foo";
		final String lastName = "bar";
		carpenterCore.addFixture(new Fixture<User>() {
			public void configure(User entity) {
				entity.setLastName(lastName);
			}

			public String getName() {
				return name;
			}
		});

		User user = carpenterCore.build(User.class, name);
		assertNotNull(user);
		assertEquals(lastName, user.getLastName());
	}

	@Test
	public void shouldAddMultipleFixturesOfSameTypeWithDifferentName() throws Exception {
		carpenterCore.addFixture(new UserFixture());
		carpenterCore.addFixture(new Fixture<User>(){
			public void configure(User entity) {
				entity.setLastName("foo");
			}
			public String getName() {
				return "bar";
			}
		});

		User user1 = carpenterCore.build(User.class, User.class.getName());
		User user2 = carpenterCore.build(User.class, "bar");
		assertFalse(user1.getLastName().equals(user2.getLastName()));
	}

	@Test
	public void shouldBuildEntityWithOverrideProperties() throws Exception {
		carpenterCore.addFixture(new Fixture<User>(){
			public void configure(User entity) {
				entity.setFirstName("Foo");
			}
		});

		String override = "Bar";
		User user = carpenterCore.build(User.class, User.class.getName(), new Properties().set("firstName", override));
		assertEquals(override, user.getFirstName());
	}

	@Test(expected = OverrideException.class)
	public void shouldNotBuildEntityDueToInvalidPropertyName() throws Exception {
		carpenterCore.addFixture(new Fixture<User>(){
			public void configure(User entity) {
				entity.setFirstName("Foo");
			}
		});

		User user = carpenterCore.build(User.class, User.class.getName(), new Properties().set("invalid", "foo"));
	}

	@Test
	public void shouldCreateEntity() throws Exception {
		carpenterCore.addFixture(new UserFixture());

		User user = carpenterCore.create(User.class, User.class.getName());

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(persister).persist(userCaptor.capture());

		assertSame(user, userCaptor.getValue());
	}

	@Test
	public void shouldCreateEntityWithProperties() throws Exception {
		carpenterCore.addFixture(new UserFixture());

		String override = "OverrideName";
		User user = carpenterCore.create(User.class, User.class.getName(), new Properties().set("firstName", override));

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(persister).persist(userCaptor.capture());

		assertSame(user, userCaptor.getValue());
		assertEquals(override, user.getFirstName());
	}
}
