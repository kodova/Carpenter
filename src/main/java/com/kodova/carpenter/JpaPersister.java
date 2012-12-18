package com.kodova.carpenter;

import javax.persistence.EntityManager;

public abstract class JpaPersister extends AbstractPersister implements Persister{

	public abstract EntityManager getEntityManager();
	
	public void persist(Object entity) {
		
	}

}