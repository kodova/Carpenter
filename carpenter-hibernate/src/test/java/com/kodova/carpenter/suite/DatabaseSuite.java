package com.kodova.carpenter.suite;

import com.google.common.io.Resources;
import com.kodova.carpenter.Carpenter;
import com.kodova.carpenter.HibernatePersisterTest;
import com.kodova.carpenter.hibernate.HibernatePersister;
import com.kodova.carpenter.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import javax.persistence.EntityManager;
import java.util.Properties;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		HibernatePersisterTest.class
})
public class DatabaseSuite {

	private static Session session;

	@ClassRule
	public static ExternalResource resource = new ExternalResource() {
		@Override
		protected void before() throws Throwable {
			Properties props = new Properties();
			props.load(Resources.getResource("hibernate.properties").openStream());

			Configuration configuration = new Configuration();
			configuration.addProperties(props);
			for(Class<?> entity : HibernateUtil.annotatedEntities("com.kodova.carpenter.example.entity")){
				configuration.addAnnotatedClass(entity);
			}
			SessionFactory sf = configuration.buildSessionFactory();
			session = sf.openSession();

			Carpenter.fixturePackage("com.kodova.carpenter.fixture");
			Carpenter.persister(new HibernatePersister(session));
		}

		@Override
		protected void after() {
			session.close();
		}
	};
	private static EntityManager entityManager;


	public static Session getSession() {
		return session;
	}
}
