package com.kodova.carpenter;

import javax.persistence.EntityManager;

public class JpaPersister implements Persister {

	private EntityManager entityManager;

	public JpaPersister(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void persist(Object entity) {
		entityManager.persist(entity);
	}
}
