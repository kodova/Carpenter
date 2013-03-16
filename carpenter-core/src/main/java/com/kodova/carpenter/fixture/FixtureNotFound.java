package com.kodova.carpenter.fixture;

/**
 *
 */
public class FixtureNotFound extends RuntimeException {

	public <E> FixtureNotFound(Class<E> entityClass, String name) {
		super(String.format("No fixture was found for class [%s], with name [%s]", entityClass, name));
	}
}
