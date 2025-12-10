package fr.pns.vehicles;

import fr.pns.tracking.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DroneTest {
    @Test
    void testInitialiseDrone() {
        Drone drone = new Drone(30, 0.2);
        assertEquals(30, drone.getBatteryCapacity());
        assertEquals(0, drone.getCurrentCharge());
        assertEquals(0.2, drone.getEnergyConsumptionPerKilometer());
    }

    @Test
    void testGetPosition() {
        // 1. Définissez la position de base que vous attendez
        final Position basePosition = new Position(0, 0, 0);

        Drone drone = new Drone(30, 0.2);
        Position position = drone.getPosition();

        // 2. Utilisez votre variable locale pour la comparaison
        assertEquals(basePosition, position);

        drone.takeOff();
        position = drone.getPosition();
        assertNotEquals(basePosition, position);

        drone.returnToBase();
        position = drone.getPosition();
        assertEquals(basePosition, position);
    }
    @Test
    void testDriveTo() {
        final Position basePosition = new Position(0, 0, 0);
        Drone drone = new Drone(30, 0.2);
        Position position = drone.getPosition();
        assertEquals(basePosition, position);
        assertFalse(drone.driveTo(new Position(10,20)));
        position = drone.getPosition();
        assertEquals(basePosition, position);
        assertTrue(drone.charge(10));
        double charge = drone.getCurrentCharge();
        assertTrue(drone.driveTo(new Position(10,20)));
        assertEquals(new Position(10,20), drone.getPosition());
        assertTrue(drone.getCurrentCharge() < charge);
        double diff = charge - drone.getCurrentCharge();
        assertTrue(diff >0);
        charge = drone.getCurrentCharge();
        assertTrue(drone.driveTo(new Position(0,0)));
        position = drone.getPosition();
        assertEquals(basePosition, position);
        assertTrue(drone.getCurrentCharge() < charge);
        assertEquals(diff, charge - drone.getCurrentCharge());
        drone.charge(10);
        assertTrue(drone.driveTo(new Position(10,20)));
        assertTrue(drone.driveTo(new Position(20,30)));
        charge = drone.getCurrentCharge();
        assertTrue(drone.driveTo(new Position(20,30)));
        assertEquals(charge, drone.getCurrentCharge());
        assertEquals(new Position(20,30), drone.getPosition());
        assertFalse(drone.driveTo(new Position(100,200)));
    }
}