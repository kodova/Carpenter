package com.kodova.carpenter;

import org.hibernate.Session;

/**
 *
 */
public abstract class HibernatePersister implements Persister {

	abstract public Session getSession();

	public void persist(Object entity) {
		getSession().persist(entity);
	}
}
