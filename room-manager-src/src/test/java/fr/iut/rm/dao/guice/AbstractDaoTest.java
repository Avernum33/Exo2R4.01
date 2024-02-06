package fr.iut.rm.dao.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.UnitOfWork;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.util.Modules;
import fr.iut.rm.MainModule;
import fr.iut.rm.dao.PersistenceStarter;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;


/**
 * Abstract test class which starts a transaction at startup and rollbacks it at the end
 */
public abstract class AbstractDaoTest {

    protected static Injector injector;

    @BeforeAll
    public static void setUp() {
        //Initializes injector
        injector = Guice.createInjector(new JpaPersistModule("room-manager-test"), Modules.override(new MainModule()).with(new TestModule()));

        //Init persistence layer
        PersistenceStarter persistenceStarter = injector.getInstance(PersistenceStarter.class);
        try {
            persistenceStarter.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        injector.getInstance(UnitOfWork.class).begin();
    }

    @AfterAll
    public static void tearDown() {
        injector.getInstance(UnitOfWork.class).end();
        PersistenceStarter persistenceStarter = injector.getInstance(PersistenceStarter.class);
        persistenceStarter.stop();
    }

    /**
     * Persist object
     *
     * @param entity entity to persists
     */
    protected void persist(Object entity) {
        EntityManager em = injector.getProvider(EntityManager.class).get();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    /**
     * Loads entity from its identifier
     *
     * @param type entity type
     * @param id   object id
     * @return jpa managed object
     */
    protected <T> T reload(Class<T> type, Object id) {
        EntityManager em = injector.getProvider(EntityManager.class).get();
        return em.find(type, id);
    }
}
