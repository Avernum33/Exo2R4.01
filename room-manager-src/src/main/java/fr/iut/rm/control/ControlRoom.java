package fr.iut.rm.control;

import com.google.inject.Inject;
import fr.iut.rm.persistence.dao.RoomDao;
import fr.iut.rm.persistence.domain.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ControlRoom {
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ControlRoom.class);

    /**
     * Data Access Object for rooms
     */
    @Inject
    private RoomDao roomDao;

    /**
     * * Displays all the rooms content in DB
     */
    public void showRooms() {
        List<Room> rooms = roomDao.findAll();
        if (rooms.isEmpty()) {
            System.out.println("No room");
        } else {
            System.out.println("Rooms :");
            System.out.println("--------");
            for (Room room : rooms) {
                System.out.println(String.format("   [%d], name '%s', description '%s'", room.getId(), room.getName(), room.getDescription()));
            }
        }
    }

    /**
     * Creates a room in DB
     *
     * @param name the name of the room
     */
    public void createRoom(final String name, final String description) {
        // TODO check unicity

        Room room = new Room();
        room.setName(name);
        room.setDescription(description);
        roomDao.saveOrUpdate(room);
    }

    /**
     * Delete a room in DB
     *
     * @param name the name of the room
     */
    public void deleteRoom(final String name) {
        roomDao.removeRoom(name);
    }

}