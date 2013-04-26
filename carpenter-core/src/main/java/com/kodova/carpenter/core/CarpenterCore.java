package com.kodova.carpenter.core;

import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.reflect.Invokable;
import com.kodova.carpenter.Persister;
import com.kodova.carpenter.Properties;
import com.kodova.carpenter.fixture.Fixture;
import com.kodova.carpenter.fixture.FixtureNotFound;
import com.kodova.carpenter.fixture.PartialFixture;
import org.reflections.Reflections;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;


public class CarpenterCore {

	private Optional<Persister> persister;
	private final Table<Class<?>, String, Fixture<?>> fixtureTable = HashBasedTable.create();
	private final ConstructionContext constructionContext = new ConstructionContext();

	public CarpenterCore(){
		this.persister = Optional.absent();
	}

	public <E> E build(Class<E> entityClass, String name){
		return buildEntity(entityClass, name, new Properties());
	}

	public <E> E build(Class<E> entityClass, String name, Properties properties){
		return buildEntity(entityClass, name, properties);
	}

	public <E> E create(Class<E> entityClass, String name){
		return createEntity(entityClass, name, new Properties());
	}

	public <E> E create(Class<E> entityClass, String name, Properties properties){
		return createEntity(entityClass, name, properties);
	}

	public  <E> Fixture<E> getFixture(Class<E> entityClass, String name) {
		Fixture<E> fixture = (Fixture<E>) fixtureTable.get(entityClass, name);
		if(fixture == null){
			throw new FixtureNotFound(entityClass, name);
		}
		return fixture;
	}

	public <E> Fixture<E> getFixture(Class<E> entityClass) {
		return getFixture(entityClass, entityClass.getName());
	}

	private <E> E buildEntity(Class<E> entityClass, String name, Properties properties){
		constructionContext.startBuild();
		E entity;
		try {
			Fixture<E> fixture = getFixture(entityClass, name);
			verifyBuildRequest(fixture, properties);
			entity = fixture.newInstance();
			fixture.configure(entity);
			overrides(entity, properties);
		} finally {
			constructionContext.endBuild();
		}

		return entity;
	}

	private <E> void verifyBuildRequest(Fixture<E> fixture, Properties properties) {
		String[] required = new String[]{};
		if(fixture instanceof PartialFixture){
			required = ((PartialFixture) fixture).requiredProperties();
		}

		boolean isValid = properties.getOverrides().keySet().containsAll(Arrays.asList(required));
		if(!isValid){
			throw new RuntimeException("not all required properties were supplide for partial fixture");
		}
	}

	private <E> E createEntity(Class<E> entityClass, String name, Properties properties) {
		constructionContext.startCreate();
		E entity;
		try {
			entity = buildEntity(entityClass, name, properties);
			if(persister.isPresent()){
				persister.get().persist(entity);
			}
		} finally {
			constructionContext.endCreate();
		}
		return entity;
	}

	public void addFixture(Fixture<?> fixture){
		String name = fixture.getName();
		Class<?> entityType = fixture.getType();
		fixture.setContext(constructionContext);
		fixture.setCarpenterCore(this);
		fixtureTable.put(entityType, name, fixture);
	}

	public void setPersister(Persister persister) {
		this.persister = Optional.of(persister);
	}

	public void scanPackageForFixtures(String toScan) {
		Reflections reflections = new Reflections(toScan);
		Set<Class<? extends Fixture>> fixtureClasses = reflections.getSubTypesOf(Fixture.class);
		for (Class<? extends Fixture> fixtureClass : fixtureClasses){
			try {
				Fixture<?> fixture = fixtureClass.newInstance();
				addFixture(fixture);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	private void overrides(Object entity, Properties properties) {
		for (Map.Entry<String, Object> entry : properties.getOverrides().entrySet()){
			override(entity, entry.getKey(), entry.getValue());
		}
	}

	private void override(Object entity, String property, Object value) {
		PropertyDescriptor propertyDescriptor = null;
		try {
			propertyDescriptor = new PropertyDescriptor(property, entity.getClass());
			Invokable setter = Invokable.from(propertyDescriptor.getWriteMethod());
			setter.invoke(entity, value);
		} catch (IllegalArgumentException e) {
			throw new OverrideException(propertyDescriptor, e);
		} catch (InvocationTargetException e) {
			throw new OverrideException(propertyDescriptor, e);
		} catch (IllegalAccessException e) {
			throw new OverrideException(propertyDescriptor, e);
		} catch (IntrospectionException e) {
			throw new OverrideException("Failed to get instance of property " + property, e);
		}
	}
}
