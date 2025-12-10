package fr.pns.vehicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectricBikeTest extends ElectricVehicleTest {
    ElectricBike bike;
    final int batteryCapacity = 30;

    @BeforeEach
    void setUp() {
        ElectricBike.resetIdentifier();
        bike = new ElectricBike(batteryCapacity, new double[]{0.1, 0.2, 0.3});
        electricVehicle = bike; // tests hérités exécutés sur le vélo
    }

    @Test
    void testAssistLevelLimits() {
        bike.setPedalAssistLevel(2);
        assertEquals(2, bike.getPedalAssistLevel());

        bike.setPedalAssistLevel(-1);
        assertEquals(0, bike.getPedalAssistLevel(), "Le niveau ne doit pas être inférieur à 0");

        bike.setPedalAssistLevel(10);
        assertEquals(2, bike.getPedalAssistLevel(), "Le niveau ne doit pas dépasser le maximum");
    }

    @Test
    void testEnergyConsumptionForAssistLevel() {
        assertEquals(0.2, bike.getEnergyConsumptionForAssistLevel(1));
        assertEquals(0.3, bike.getEnergyConsumptionForAssistLevel(5), 0.001);
        assertEquals(0.1, bike.getEnergyConsumptionForAssistLevel(-3), 0.001);
    }

    @Test
    void testGetEnergyConsumptionPerKilometerDynamic() {
        bike.setPedalAssistLevel(0);
        assertEquals(0.1, bike.getEnergyConsumptionPerKilometer(), 0.001);

        bike.setPedalAssistLevel(2);
        assertEquals(0.3, bike.getEnergyConsumptionPerKilometer(), 0.001);
    }
    @Test
    void testIdentifiers() {
        assertEquals("EB-1", bike.getName());
        ElectricBike bike2 = new ElectricBike(batteryCapacity, new double[]{0.1, 0.2, 0.3});
        assertEquals("EB-2", bike2.getName());
        ElectricBike bike3 = new ElectricBike(batteryCapacity, new double[]{0.1, 0.2, 0.3});
        assertEquals("EB-3", bike3.getName());
        assertEquals("EB-1", bike.getName());
    }
}

