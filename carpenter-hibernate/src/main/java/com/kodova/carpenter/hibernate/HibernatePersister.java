package com.kodova.carpenter.hibernate;

import com.kodova.carpenter.Persister;
import org.hibernate.Session;

public class HibernatePersister implements Persister {

	private Session session;

	public HibernatePersister(Session session) {
		this.session = session;
	}

	@Override
	public void persist(Object entity) {
		session.saveOrUpdate(entity);
	}
}
