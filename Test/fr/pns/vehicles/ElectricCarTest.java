package fr.pns.vehicles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectricCarTest {

    private double batteryCapacity;

    @Test
    void turnOnCoolingSystem() {
    }

    @Test
    void turnOffCoolingSystem() {
    }

    @Test
    void isOnCoolingSystem() {
    }

    @Test
    void getEnergyConsumptionPerKilometer() {
    }

    @Test
    void getLicensePlate() {
    }

    @Test
    void getModel() {
    }

    @Test
    void testToString() {
    }

    @Test
    void isAvailable() {
    }

    @Test
    void getName() {
    }

    @Test
    void match() {
    }

    @Test
    void setAvailable() {
    }

    @Test
    void markAsDamaged() {
    }

    @Test
    void markAsAvailable() {
    }
    final String licensePlate = "AB-123-CD";
    final String brand = "Tesla";
    final String model = "Model S";
    @Test
    void testMatch() {
        ElectricCar car = new ElectricCar(batteryCapacity, licensePlate, brand + " " + model);
        assertTrue(car.match("AB-123-CD"));
        assertFalse(car.match("AB-123-CE"));
        assertTrue(car.match("123"));
        assertTrue(car.match("Tesla"));
        assertTrue(car.match("S"));
    }
    @Test
    void testSetAvailable() {
        ElectricCar car = new ElectricCar(batteryCapacity, licensePlate, brand + " " + model);
        assertTrue(car.isAvailable());
        car.setAvailable(false);
        assertFalse(car.isAvailable());
        car.setAvailable(true);
        assertTrue(car.isAvailable());
        car.markAsDamaged();
        assertFalse(car.isAvailable());
        car.markAsAvailable();
        assertTrue(car.isAvailable());
    }
    @Test
    void testMatchIncludingNot(){
        ElectricCar car = new ElectricCar(batteryCapacity, licensePlate, brand + " " + model);
        assertFalse(car.match("Not: AB-123-CD"));
        assertTrue(car.match("NOT: AB-123-CE"));
        assertFalse(car.match("NOT: 123"));
        assertFalse(car.match("AB-123-CD") && car.match("Not : Tesla"));
    }
}