package fr.pns.fleets;

import fr.pns.vehicles.ElectricBike;
import fr.pns.vehicles.ElectricCar;
import fr.pns.vehicles.ElectricVehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FleetOfVehiculesTest {
    @Test
    void testSimpleFleetOfVehicles() {
        FleetOfVehicules<ElectricVehicle>fleetOfEVehicles = new FleetOfVehicules();
        assertEquals(0, fleetOfEVehicles.size());
        ElectricVehicle electricVehicle = new ElectricCar(30, "AB-123-CD");
        fleetOfEVehicles.add(electricVehicle);
        assertEquals(1, fleetOfEVehicles.size());
        assertTrue(fleetOfEVehicles.contains(electricVehicle));
        //Without the equals method, we cannot check if the vehicle is already in the fleet
        assertFalse(fleetOfEVehicles.contains(new ElectricCar(30, "AB-123-CD")));
        fleetOfEVehicles.add(new ElectricCar(40, "AB-125-XS"));
        assertEquals(2, fleetOfEVehicles.size());
        ElectricVehicle electricVehicle2 = fleetOfEVehicles.get(1);
        assertEquals(40,electricVehicle2.getBatteryCapacity());
        assertEquals(30, fleetOfEVehicles.get(0).getBatteryCapacity());
    }
    @Test
    void testGenericOnASubclassBike(){
        FleetOfVehicules<ElectricBike>fleetOfBikes = new FleetOfVehicules();
        assertEquals(0, fleetOfBikes.size());
        // Changez ElectricVehicle en ElectricBike
        ElectricBike electricVehicle = new ElectricBike(30, new double[]{0.2, 0.5, 0.8});
        fleetOfBikes.add(electricVehicle);
        assertEquals(1, fleetOfBikes.size());
    }

}