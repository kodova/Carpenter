package com.kodova.carpenter.suite;

import com.kodova.carpenter.Carpenter;
import com.kodova.carpenter.JpaPersister;
import com.kodova.carpenter.JpaPersisterTest;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		JpaPersisterTest.class
})
public class DatabaseSuite {

	private static EntityManager em;

	@ClassRule
	public static ExternalResource resource = new ExternalResource() {
		@Override
		protected void before() throws Throwable {
			em = Persistence.createEntityManagerFactory("CarpenterUnit").createEntityManager();

			Carpenter.fixturePackage("com.kodova.carpenter.fixture");
			Carpenter.persister(new JpaPersister(em));
		}

		@Override
		protected void after() {
			em.close();
		}
	};
	private static EntityManager entityManager;


	public static EntityManager getEntityManager() {
		return em;
	}
}
