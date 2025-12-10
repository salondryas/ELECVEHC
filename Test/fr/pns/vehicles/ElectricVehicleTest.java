
package fr.pns.vehicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;

class ElectricVehicleTest {

    Logger logger = Logger.getLogger(ElectricVehicle.class.getName());
    ElectricVehicle electricVehicle;
    double batteryCapacity = 30;
    static final double ENERGY_CONSUMPTION_PER_KM_DEFAULT = 0.2;

    /****** 
     * Setup method to initialize an ElectricVehicle instance before each test. 
     ******/
    @BeforeEach

    @org.junit.jupiter.api.Test
    void testInitialiseVE() {
        // Check initial values 
        assertEquals(batteryCapacity, electricVehicle.getBatteryCapacity());
        assertEquals(0, electricVehicle.getCurrentCharge());
        //logger.info("Just to see : " + electricVehicle);
    }


    @org.junit.jupiter.api.Test
    void testChargeValid() {
        assertTrue((electricVehicle.charge(10)));
        assertEquals(10, electricVehicle.getCurrentCharge());
        assertTrue((electricVehicle.charge(20)));
        assertEquals(30, electricVehicle.getCurrentCharge());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Test charge not valid")
    void testChargeNotValid() {
        //Initially the battery is empty 
        assertEquals(0, electricVehicle.getCurrentCharge());
        // Overcharge 
        assertFalse((electricVehicle.charge(100)));
        // Still empty 
        assertEquals(0, electricVehicle.getCurrentCharge());
        // Valid charge 
        assertTrue((electricVehicle.charge(10)));
        assertEquals(10, electricVehicle.getCurrentCharge());// Overcharge
        assertFalse((electricVehicle.charge(21)));
        assertEquals(10, electricVehicle.getCurrentCharge());
    }

    @Test
    void testChargeToFull() {
        electricVehicle.charge(10);
        double charge = electricVehicle.chargeToFull();
        assertEquals(batteryCapacity, electricVehicle.getCurrentCharge());
        assertEquals(batteryCapacity - 10, charge);
    }

    @Test
    void testChargeToFullOneTime() {
        double charge = electricVehicle.chargeToFull();
        assertEquals(30, electricVehicle.getCurrentCharge());
        assertEquals(30, charge);
    }



}