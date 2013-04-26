package com.kodova.carpenter.hibernate;

import org.reflections.Reflections;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class HibernateUtil {

	public static Collection<Class<?>> annotatedEntities(String searchPackage){
		Reflections reflections = new Reflections(searchPackage);

		Set<Class<?>> entities = new LinkedHashSet<Class<?>>();
		entities.addAll(reflections.getTypesAnnotatedWith(Entity.class));
		entities.addAll(reflections.getTypesAnnotatedWith(Embeddable.class));
		entities.addAll(reflections.getTypesAnnotatedWith(MappedSuperclass.class));

		return entities;
	}

}
