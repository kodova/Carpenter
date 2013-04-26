package com.kodova.carpenter.fixture;

import com.google.common.base.Optional;
import com.google.common.reflect.TypeToken;
import com.kodova.carpenter.Properties;
import com.kodova.carpenter.core.CarpenterCore;
import com.kodova.carpenter.core.ConstructionContext;

import java.util.Collection;

/**
 *
 */
public abstract class Fixture<E> {

	private final TypeToken<E> typeToken = new TypeToken<E>(getClass()) {};
	private Optional<String> name = Optional.absent();
	private ConstructionContext context;
	private CarpenterCore carpenterCore;

	public Fixture(){}

	abstract public void configure(E entity);

	/**
	 * Creates a new instance of a entity of the type the fixture supports.
	 * @return
	 */
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

	/**
	 * Gets the class for the type of entity the fixture supports
	 * @return
	 */
	public Class<E> getType(){
		return (Class<E>) typeToken.getRawType();
	}

	/**
	 * Sets the name of the fixture. This can bee used to override the default name as well as the @Name if one
	 * exists on the fixture
	 * @param name
	 */
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

	public void setCarpenterCore(CarpenterCore carpenterCore) {
		this.carpenterCore = carpenterCore;
	}

	/**
	 * Gets an instance of another object from a fixture and will persist or build based on the original
	 * request. If the original request is a build this will simply build. If the original request was a
	 * create then this will create an instance of a persisted object from a fixture and persist it.
	 * @param type
	 * @param <T>
	 * @return
	 */
	protected <T> T get(Class<T> type) {
		return context.get(type);
	}

	/**
	 * Will get and instance of another object from a fixture and override properties.
	 * @see com.kodova.carpenter.fixture.Fixture#get(Class)
	 * @param type
	 * @param properties
	 * @param <T>
	 * @return
	 */
	protected <T> T get(Class<T> type, Properties properties) {
		return context.get(type, properties);
	}

	/**
	 * Configures a entity from a different fixture. Helpful for when you have two fixtures
	 * of the same type with different names. You can create base fixture which you can call
	 * from other fixtures using the composite method.
	 * @param entity The entity that needs to be built
	 * @param type The type fo the fixture you would like to configure onto the entity
	 * @param <T>
	 */
	protected <T extends E> void composite(E entity, Class<T> type) {
		composite(entity, type, type.getName());
	}

	/**
	 * @see Fixture#composite(Object, Class)
	 * @param entity The entity that needs to be built
	 * @param type The type fo the fixture you would like to configure onto the entity
	 * @param name The name of the fixture to call
	 * @param <T>
	 */
	protected <T extends E> void composite(E entity, Class<T> type, String name){
		Fixture<T> fixture = carpenterCore.getFixture(type, name);
		if(fixture.equals(this)){
			throw new RuntimeException("Can create composite from same fixture");
		}
		fixture.configure((T) entity);
	}

	/**
	 * Picks a random value from a enum
	 * @param enumType
	 * @param <T>
	 * @return
	 */
	public static <T extends Enum> T pick(Class<T> enumType){
		return Randoms.pick(enumType);
	}

	/**
	 * Picks a random value from a collection
	 * @param items
	 * @param <T>
	 * @return
	 */
	public static <T> T pick(Collection<T> items){
		return Randoms.pick(items);
	}

	/**
	 * Picks a random value from array or a list of arguments
	 * @param items
	 * @param <T>
	 * @return
	 */
	public static <T> T pick(T... items){
		return Randoms.pick(items);
	}
}
