package fr.pns.charging;

import fr.pns.vehicles.ElectricCar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChargingStationTest {

    @Test
    void getStationName() {
    }

    @Test
    void setStationName() {
    }

    @Test
    void getAvailableChargingPoints() {
    }

    @Test
    void setAvailableChargingPoints() {
    }

    @Test
    void getEnergyProvider() {
    }

    @Test
    void setEnergyProvider() {
    }

    @Test
    void connectToChargingPoint() {
    }

    @Test
    void disconnectFromChargingPoint() {
    }
    @Test
    void testConnect() {
        ChargingStation chargingStation = new ChargingStation("Charging Station 1", 10);

        // CORRECTION : Instanciez un objet concret
        // (J'utilise ElectricCar comme exemple, en supposant un constructeur simple)
        // Vous devrez peut-être adapter le constructeur (plaque, modèle)
        ElectricCar cityCar = new ElectricCar(30, "TEST-01");

        double charge = chargingStation.connectToChargingPoint(cityCar);

        assertEquals(9, chargingStation.getAvailableChargingPoints());
        assertEquals(30, cityCar.getCurrentCharge());
        assertEquals(30, charge);

        chargingStation.disconnectFromChargingPoint(cityCar);
        assertEquals(10, chargingStation.getAvailableChargingPoints());
    }

    @Test
    void testInitialiseChargingStation() {
        ChargingStation chargingStation =
                new ChargingStation("Charging Station 1", 10);
        assertEquals(10, chargingStation.getAvailableChargingPoints());
        EnergyProvider provider = chargingStation.getEnergyProvider();
        assertEquals("EDF", provider.getProviderName());
        assertEquals("Solar", provider.getEnergySource());
    }
}