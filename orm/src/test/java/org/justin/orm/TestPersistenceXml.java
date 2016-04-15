package org.justin.orm;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;
import org.justin.orm.entity.Application;
import org.justin.orm.entity.NetworkInterface;
import org.justin.orm.entity.Server;
import org.justin.orm.entity.User;
import org.justin.util.CollectionUtil;
import org.justin.util.FileUtil;

@SuppressWarnings("static-method")
public class TestPersistenceXml {
	@BeforeClass
	public static void deleteDatabase() throws Exception {
		FileUtil.delete("D:/Eclipse/webarch/orm/target/test-db/h2");
	}

	@Test
    public void testH2PersistenceConfiguration(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test-db-h2");
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            {
                Server server1 = new Server();
                server1.setName("mailserver");

                NetworkInterface networkInterface = new NetworkInterface("127.0.0.1", null, null, null, server1);
				ArrayList<NetworkInterface> ni = (ArrayList<NetworkInterface>) CollectionUtil.create(ArrayList.class, networkInterface);
				server1.setNetworkInterfaces(ni);

                em.persist(server1);
            }
            em.getTransaction().commit();
        } catch(Exception e) {
        	if (null != em) {
            	em.getTransaction().rollback();
        	}
        	throw new RuntimeException(e);
        } finally {
        	if (null != em) {
                em.close();
                em = null;
        	}
        }

        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            {
                Application application1 = new Application();
                application1.setName("Exchange");
                application1.setType("IMAP");
                em.persist(application1);
                application1.setType("IMAP2");
                em.persist(application1);
            }
            em.getTransaction().commit();
        } catch(Exception e) {
        	if (null != em) {
            	em.getTransaction().rollback();
        	}
        	throw new RuntimeException(e);
        } finally {
        	if (null != em) {
                em.close();
                em = null;
        	}
        }

        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            {
                Application application1 = new Application();
                application1.setName("Exchange2");
                application1.setType("IMAP2");
                em.persist(application1);

                User user1 = new User();
                user1.setFirstName("Justin");
                user1.setLastName("Cranford");
                user1.setEmail("justincranford@org.justin");
                user1.setApplication(application1);
                em.persist(user1);
            }
            em.getTransaction().commit();
        } catch(Exception e) {
        	if (null != em) {
            	em.getTransaction().rollback();
        	}
        	throw new RuntimeException(e);
        } finally {
        	if (null != em) {
                em.close();
                em = null;
        	}
        }

        try {
            em = factory.createEntityManager();
            em.getTransaction().begin();
            {
                User user2 = new User();
                user2.setFirstName("Someone");
                user2.setLastName("Else");
                user2.setEmail("someoneelse@org.justin");
                em.persist(user2);
            }
            {
                User user3 = new User();
                user3.setFirstName("Another");
                user3.setLastName("User");
                user3.setEmail("anotheruser@org.justin");
                em.persist(user3);
            }
            em.getTransaction().commit();
        } catch(Exception e) {
        	if (null != em) {
            	em.getTransaction().rollback();
        	}
        	throw new RuntimeException(e);
        } finally {
        	if (null != em) {
                em.close();
                em = null;
        	}
        }
	}
}