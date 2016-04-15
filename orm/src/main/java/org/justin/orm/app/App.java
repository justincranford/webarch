package org.justin.orm.app;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Obtain EntityManager in a JavaSE environment and auto-close it with application is GC'd.
 */
public class App implements AutoCloseable {
	private static App APP = new App();	// singleton

	private EntityManagerFactory emf;

	public App getApp() {
		return APP;
	}
	private App() {
		this.emf = Persistence.createEntityManagerFactory("test-db-h2");	// TODO Change to production value, or allow dependency injection via container property
	}
	public void close() throws Exception {
		if (null != this.emf) {
			try {
				this.emf.close();
				System.out.println("App.close Closed EntityManagerFactory");
			} catch(Throwable t) {
				System.out.println("App.close Failed to close EntityManagerFactory");
				t.printStackTrace();
			} finally {
				System.out.println("App.close Done");
			}
		}
	}

	public static EntityManagerFactory getEMF() {
		return APP.emf;
	}
}