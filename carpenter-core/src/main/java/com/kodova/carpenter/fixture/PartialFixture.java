package com.kodova.carpenter.fixture;

public abstract class PartialFixture<E> extends Fixture<E>{

	public abstract String[] requiredProperties();

	/**
	 * Returns a array of strings from arguments that are supplied
	 *
	 * @param args
	 * @return
	 */
	protected String[] requiredList(String... args) {
		return args;
	}
}
