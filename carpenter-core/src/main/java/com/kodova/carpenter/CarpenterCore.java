package com.kodova.carpenter;

import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.reflect.Invokable;
import org.reflections.Reflections;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;


public class CarpenterCore {

	private Optional<Persister> persister;
	private Table<Class<?>, String, Fixture<?>> fixtureTable = HashBasedTable.create();
	private ConstructionContext constructionContext = new ConstructionContext();

	public CarpenterCore(){
		this.persister = Optional.absent();
	}

	public <E> E build(Class<E> entityClass, String name){
		constructionContext.startBuild();
		Fixture<E> fixture = (Fixture<E>) fixtureTable.get(entityClass, name);
		if(fixture == null){
			throw new FixtureNotFound(entityClass, name);
		}

		E entity = fixture.newInstance();
		fixture.configure(entity);
		constructionContext.endBuild();
		return entity;
	}

	public <E> E build(Class<E> entityClass, String name, Properties properties){
		E entity = build(entityClass, name);
		try {
			overrides(entity, properties);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
		return entity;
	}

	public <E> E create(Class<E> entityClass, String name){
		constructionContext.startCreate();
		E entity = build(entityClass, name);
		if(persister.isPresent()){
			persister.get().persist(entity);
		}
		constructionContext.endCreate();
		return entity;
	}

	public <E> E create(Class<E> entityClass, String name, Properties properties){
		constructionContext.startCreate();
		E entity = build(entityClass, name, properties);
		if(persister.isPresent()){
			persister.get().persist(entity);
		}
		constructionContext.endCreate();
		return entity;
	}

	public void addFixture(Fixture<?> fixture){
		String name = fixture.getName();
		Class<?> entityType = fixture.getType();
		fixture.setContext(constructionContext);
		fixtureTable.put(entityType, name, fixture);
	}

	private void overrides(Object entity, Properties properties) throws IntrospectionException {
		for (Map.Entry<String, Object> entry : properties.getOverrides().entrySet()){
			override(entity, entry.getKey(), entry.getValue());
		}
	}

	private void override(Object entity, String property, Object value) throws IntrospectionException {
		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(property, entity.getClass());
			Invokable setter = Invokable.from(propertyDescriptor.getWriteMethod());
			setter.invoke(entity, value);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
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


}
