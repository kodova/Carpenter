package com.kodova.carpenter.fixture;

import com.google.common.base.Optional;
import com.google.common.reflect.TypeToken;
import com.kodova.carpenter.core.ConstructionContext;
import com.kodova.carpenter.Properties;

/**
 *
 */
public abstract class Fixture<E> {

	private final TypeToken<E> typeToken = new TypeToken<E>(getClass()) {};
	private Optional<String> name = Optional.absent();
	private ConstructionContext context;


	abstract public void configure(E entity);

	public E newInstance(){
		Class<E> entityClass = getType();
		try {
			return entityClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Unable to create new of entity, a zero constructor arg is required or override the newInstance method on the fixture", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Unable to create new of entity, a zero constructor arg is required or override the newInstance method on the fixture", e);
		}
	}

	public Class<E> getType(){
		return (Class<E>) typeToken.getRawType();
	}

	public void setName(String name){
		this.name = Optional.of(name);
	}

	public String getName(){
		if(name.isPresent()){
			return name.get();
		}

		if(this.getClass().isAnnotationPresent(Name.class)){
			return this.getClass().getAnnotation(Name.class).value();
		}

		return getType().getName();
	}

	public void setContext(ConstructionContext context) {
		this.context = context;
	}

	protected <T> T get(Class<T> type) {
		return context.get(type);
	}

	protected <T> T get(Class<T> type, Properties properties) {
		return context.get(type, properties);
	}
}
