package fr.pns.tracking;

import fr.pns.vehicles.Drone; // Importez votre classe Drone
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrackingSystemTest {

    TrackingSystem trackingSystem;
    Drone drone1;
    Drone drone2;
    Drone drone3;

    // Position de base pour les drones
    final Position BASE_POSITION = new Position(0, 0, 0);

    @BeforeEach
    void setup() {
        // Initialise le système et les drones avant chaque test
        trackingSystem = new TrackingSystem();
        drone1 = new Drone(100, 10);
        drone2 = new Drone(40, 2);
        drone3 = new Drone(50, 5);

        // Charge les drones pour qu'ils puissent voler (utile pour des tests futurs)
        drone1.chargeToFull();
        drone2.chargeToFull();
        drone3.chargeToFull();
    }

    /**
     * Méthode utilitaire pour ajouter les drones au système,
     * utilisée par plusieurs tests.
     */
    void init() {
        trackingSystem.addTrackableObject(drone1);
        trackingSystem.addTrackableObject(drone2);
        trackingSystem.addTrackableObject(drone3);
    }

    @Test
    void testAddTrackableObject() {
        assertEquals(0, trackingSystem.getNumberOfTrackedObjects());
        init(); // Ajoute les 3 drones
        assertEquals(3, trackingSystem.getNumberOfTrackedObjects());
    }

    // --- Choisissez UN de ces deux tests ---

    /**
     * Testez CECI si votre méthode getTrackableObjectPosition
     * renvoie null en cas d'erreur.
     * [cite: 559-564]
     */
    @Test
    void testGetTrackableObjectPosition_WithNull() {
        init();
        assertNotNull(trackingSystem.getTrackableObjectPosition(0));
        assertNotNull(trackingSystem.getTrackableObjectPosition(1));
        assertNotNull(trackingSystem.getTrackableObjectPosition(2));

        // Vérifie la gestion des indices hors limites
        assertNull(trackingSystem.getTrackableObjectPosition(3));
        assertNull(trackingSystem.getTrackableObjectPosition(-1));
    }


    @Test
    void testGetAllTrackablePositions() {
        init();
        List<Position> positions = trackingSystem.getAllTrackablePositions();
        assertEquals(3, positions.size());

        // Vérifie que les positions sont correctes (tous à la base)
        assertEquals(BASE_POSITION, positions.get(0));
        assertEquals(BASE_POSITION, positions.get(1));
        assertEquals(BASE_POSITION, positions.get(2));
    }

    @Test
    void testGetAllTrackablePositionsWithFlyingDrone() {
        init();

        // Le drone 1 décolle
        drone1.takeOff();

        List<Position> positions = trackingSystem.getAllTrackablePositions();
        assertEquals(3, positions.size());

        // La position du drone1 ne doit pas être la base
        assertNotEquals(BASE_POSITION, positions.get(0));

        // Les autres doivent être à la base
        assertEquals(BASE_POSITION, positions.get(1));
        assertEquals(BASE_POSITION, positions.get(2));
    }
    @Test
    void testgetClosestTrackableObject() {
        init();
        Position targetPosition = new Position();
        List<Trackable> closestTO =
                trackingSystem.getClosestTrackableObject(targetPosition);
        assertFalse(closestTO.isEmpty());
        assertEquals(3, closestTO.size());
        drone1.chargeToFull();
        targetPosition = new Position(10, 0);
        assertTrue(drone1.driveTo(targetPosition));
        closestTO = trackingSystem.getClosestTrackableObject(targetPosition);
        assertEquals(1, closestTO.size());
        assertEquals(drone1, closestTO.get(0));
        closestTO = trackingSystem.getClosestTrackableObject(new Position(6, 0));
        assertFalse(closestTO.isEmpty());
        assertEquals(1, closestTO.size());
        assertEquals(drone1, closestTO.get(0));
        closestTO = trackingSystem.getClosestTrackableObject(new Position(5, 0));
        assertFalse(closestTO.isEmpty());
        assertEquals(3, closestTO.size());
        assertEquals(drone1, closestTO.get(0));
        closestTO = trackingSystem.getClosestTrackableObject(new Position(-5, 0));
        assertFalse(closestTO.isEmpty());
        assertEquals(2, closestTO.size());
        assertNotEquals(drone1, closestTO.get(0));
    }
}