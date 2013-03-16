package com.kodova.carpenter;


import com.kodova.carpenter.core.CarpenterCore;
import com.kodova.carpenter.fixture.Fixture;

public class Carpenter {

	private  static final CarpenterCore CARPENTER_CORE = new CarpenterCore();

	public static <T> T build(Class<T> entityClass){
		return CARPENTER_CORE.build(entityClass, entityClass.getName());
	}

	public static <T> T build(Class<T> entityClass, String name){
		return CARPENTER_CORE.build(entityClass, name);
	}

	public static <T> T build(Class<T> entityClass, String name, Properties properties){
		return CARPENTER_CORE.build(entityClass, name, properties);
	}

	public static <T> T build(Class<T> entityClass, Properties properties){
		return CARPENTER_CORE.build(entityClass, entityClass.getName(), properties);
	}

	public static <T> T create(Class<T> entityClass){
		return CARPENTER_CORE.create(entityClass, entityClass.getName());
	}

	public static <T> T create(Class<T> entityClass, String name){
		return CARPENTER_CORE.create(entityClass, name);
	}

	public static <T> T create(Class<T> entityClass, String name, Properties properties){
		return CARPENTER_CORE.create(entityClass, name, properties);
	}

	public static <T> T create(Class<T> entityClass, Properties properties){
		return CARPENTER_CORE.create(entityClass, entityClass.getName(), properties);
	}

	public static void persister(Persister persister){
		CARPENTER_CORE.setPersister(persister);
	}

	public static Properties props(){
		return new Properties();
	}

	public static Properties properties(){
		return new Properties();
	}

	public static void fixturePackage(String toScan){
		CARPENTER_CORE.scanPackageForFixtures(toScan);
	}

	public static void addFixture(Fixture<?> fixture) {
		CARPENTER_CORE.addFixture(fixture);
	}
}
