package com.kodova.carpenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class CarpenterPersisterTest {

	CarpenterPersister persister;


	@Before
	public void setUp(){
		persister = new CarpenterPersister();
		persister.getSession().beginTransaction();
	}

	@After
	public void tearDown(){
		persister.getSession().getTransaction().commit();
	}

	@Test
	public void testGetEntityManager() throws Exception {

	}
}
