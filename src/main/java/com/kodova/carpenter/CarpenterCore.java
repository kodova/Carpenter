package com.kodova.carpenter;

import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;


public class CarpenterCore {

	private Optional<Persister> persister;
	private Table<Class<?>, String, Fixture<?>> fixtureTable = HashBasedTable.create();

	public CarpenterCore(){
		this.persister = Optional.absent();
	}

	public <E> E build(Class<E> entityClass, String name){
		Fixture<E> fixture = (Fixture<E>) fixtureTable.get(entityClass, name);
		if(fixture == null){
			throw new FixtureNotFound(entityClass, name);
		}

		E entity = fixture.newInstance();
		fixture.configure(entity);
		return entity;
	}

	public <E> E build(Class<E> entityClass, String name, Properties properties){
		E entity = build(entityClass, name);
		overrides(entity, properties);
		return entity;
	}

	public <E> E create(Class<E> entityClass, String name){
		E entity = build(entityClass, name);
		if(persister.isPresent()){
			persister.get().persist(entity);
		}
		return entity;
	}

	public <E> E create(Class<E> entityClass, String name, Properties properties){
		E entity = build(entityClass, name, properties);
		if(persister.isPresent()){
			persister.get().persist(entity);
		}
		return entity;
	}

	public void addFixture(Fixture<?> fixture){
		String name = fixture.getName();
		Class<?> entityType = fixture.getType();
		fixtureTable.put(entityType, name, fixture);
	}

	private void overrides(Object entity, Properties properties){
		for (Map.Entry<String, Object> entry : properties.getOverrides().entrySet()){
			override(entity, entry.getKey(), entry.getValue());
		}
	}

	private void override(Object entity, String property, Object value){
		try {
			String methodName = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
			Method method = entity.getClass().getMethod(methodName, value.getClass());
			method.invoke(entity, value);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
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
