package fr.iut.rm.persistence.dao.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import fr.iut.rm.persistence.dao.RoomDao;
import fr.iut.rm.persistence.domain.Room;
import fr.iut.rm.persistence.domain.Room_;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Implementation of {@link fr.iut.rm.persistence.dao.RoomDao}
 */
@Singleton
public class RoomDaoImpl implements RoomDao {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RoomDaoImpl.class);

    /**
     * Entity Manager used to perform database operations
     */
    @Inject
    private EntityManager em;

    /**
     * @param room room to persist
     */
    @Override
    @Transactional
    public void saveOrUpdate(final Room room) {
        em.persist(room);
        logger.debug("Room '{}' saved", room.getName());
    }

    /**
     * @return the entire db room list
     */
    @Override
    @Transactional
    public List<Room> findAll() {
        final String query = "from " + Room.class.getName();
        final List<Room> rooms = em.createQuery(query).getResultList();
        logger.debug("{} rooms found", rooms);
        return rooms;
    }


    /**
     * @param name of the room
     * @return the corresponding room or null if nothing found
     */
    @Override
    @Transactional
    public Room findByName(final String name) {
        final String query = "from " + Room.class.getName() + " as room"
            + " where room." + Room_.name.getName() + " = :name";

        final List<Room> resultList = em.createQuery(query)
            .setParameter("name", name)
            .getResultList();

        if (!resultList.isEmpty()) {
            logger.debug("Room  with name '{}' found", name);
            return resultList.get(0);
        }
        logger.debug("No room with name '{}' found", name);
        return null;
    }

    /**
     * Remove a room
     *
     * @param name name of the room
     */
    @Override
    @Transactional
    public void removeRoom(String name){
        final String deleteQuery = "DELETE FROM "+ Room.class.getName() + " as room"
                + " WHERE room." + Room_.name.getName() + " = :name";

        int deletedCount = em.createQuery(deleteQuery)
                .setParameter("name", name)
                .executeUpdate();

        if (deletedCount==0) {
            logger.debug("No room with name '{}' found", name);
        } else {
            logger.debug("'{}' room deleted", deletedCount);
        }
    }

}
