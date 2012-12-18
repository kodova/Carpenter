package com.kodova.carpenter;

import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.Set;

/**
 *
 */
public abstract class AbstractPersister implements Persister {


	protected Set<Class<?>> loadAnnotations(){
		Reflections reflections = new Reflections("com.kodova");

		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Entity.class);
		return annotated;
	}
}
