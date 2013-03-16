package com.kodova.carpenter;

import com.kodova.carpenter.core.ConstructionContext;
import com.kodova.carpenter.entity.User;
import com.kodova.carpenter.entity.UserFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.kodova.carpenter.Carpenter.props;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ConstructionContextTest {

	@Mock
	Persister persister;

	@Before
	public void setUp() throws Exception {
		Carpenter.persister(persister);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void shouldCreated() throws Exception {
		ConstructionContext context = new ConstructionContext();
		context.startCreate();
		assertTrue(context.isCreate());
	}

	@Test
	public void shouldBuild() throws Exception {
		ConstructionContext context = new ConstructionContext();
		context.startBuild();
		assertFalse(context.isCreate());
	}

	@Test
	public void shouldCreateWithEqualBuildsAndCreates() throws Exception {
		ConstructionContext context = new ConstructionContext();
		context.startCreate();
		context.startBuild();
		assertTrue(context.isCreate());
	}

	@Test
	public void shouldGetEntityThroughBuild() throws Exception {
		Carpenter.addFixture(new UserFixture());
		ConstructionContext context = new ConstructionContext();
		context.startBuild();
		context.get(User.class);

		verifyZeroInteractions(persister);
	}

	@Test
	public void shouldGetEntityThroughCreate() throws Exception {
		Carpenter.addFixture(new UserFixture());
		ConstructionContext context = new ConstructionContext();
		context.startCreate();
		User user = context.get(User.class);

		verify(persister).persist(user);
	}

	@Test
	public void shouldGetEntityThroughBuildWithOverrides() throws Exception {
		Carpenter.addFixture(new UserFixture());
		ConstructionContext context = new ConstructionContext();
		context.startBuild();

		String override = "fooOverride";
		User user = context.get(User.class, props().set("firstName", override));

		verifyZeroInteractions(persister);
		assertEquals(override, user.getFirstName());
	}

	@Test
	public void shouldGetEntityThroughCreateWithOverride() throws Exception {
		Carpenter.addFixture(new UserFixture());
		ConstructionContext context = new ConstructionContext();
		context.startCreate();

		String override = "fooOverride";
		User user = context.get(User.class, props().set("firstName", override));

		verify(persister).persist(user);
		assertEquals(override, user.getFirstName());
	}
}
