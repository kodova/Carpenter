package com.kodova.carpenter;


import com.google.common.collect.Lists;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.lang.annotation.Annotation;
import java.util.List;

public class CarpenterPersister extends HibernatePersister{

	private SessionFactory sessionFactory;

	private List<Class<? extends Annotation>> types = Lists.newArrayList(
			Embeddable.class,
			MappedSuperclass.class,
			Entity.class,
			org.hibernate.annotations.Entity.class
	);
	private Session session;


	public CarpenterPersister(){
		Configuration config = new Configuration().configure();
		addEntities(config);
		sessionFactory = config.buildSessionFactory();
	}

	private void addEntities(Configuration config) {
		Reflections reflections = new Reflections("com.kodova.carpenterCore");
		for(Class<? extends Annotation> type : types){
			 for(Class<?> entityClass : reflections.getTypesAnnotatedWith(type)){
				config.addAnnotatedClass(entityClass);
			 }
		}
	}

	@Override
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
}
