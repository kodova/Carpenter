package com.kodova.carpenter.fixture;

import com.kodova.carpenter.Properties;
import com.kodova.carpenter.core.ConstructionContext;
import com.kodova.carpenter.entity.Post;
import com.kodova.carpenter.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FixtureTest {

	@Mock ConstructionContext constructionContext;

	@Name("anno")
	private class AnnotatedUserFixture extends Fixture<User> {
		@Override
		public void configure(User entity) {
		}
	}

	@Test
	public void shouldGetNameFromAnnotation() throws Exception {
		Fixture<User> userFixture = new AnnotatedUserFixture();
		assertEquals("anno", userFixture.getName());
	}

	@Test
	public void shouldGetNameFromField() throws Exception {
		final String name = "foo";
		Fixture<User> userFixture = new Fixture<User>() {
			{
				setName(name);
			}

			@Override
			public void configure(User entity) {

			}
		};
		assertEquals(name, userFixture.getName());
	}

	@Test
	public void shouldGetNameFromFieldAndOverrideAnnotation() throws Exception {
		String name = "override";
		Fixture<User> userFixture = new AnnotatedUserFixture();
		userFixture.setName(name);
		assertEquals(name, userFixture.getName());
	}

	@Test
	public void shouldGeNameOfClassName() throws Exception {
		Fixture<User> userFixture = new Fixture<User>() {
			@Override
			public void configure(User entity) {}
		};

		assertEquals(User.class.getName(), userFixture.getName());

	}

	@Test
	public void shouldGetObjectFromContext() throws Exception {
		Fixture<User> userFixture = new AnnotatedUserFixture();
		userFixture.setContext(constructionContext);

		Class<?> requestType = Post.class;
		userFixture.get(requestType);

		verify(constructionContext).get(requestType);
	}

	@Test
	public void shouldGetObjectFromContextWithProperties() throws Exception {
		Fixture<User> userFixture = new AnnotatedUserFixture();
		userFixture.setContext(constructionContext);

		Class<?> requestType = Post.class;
		Properties properties = new Properties();
		userFixture.get(requestType, properties);

		verify(constructionContext).get(requestType, properties);
	}

}
