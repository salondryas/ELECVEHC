package fr.pns.charging;

// 1. L'import de 'ElectricVehicle' a été supprimé

// 2. On retire "implements ChargeableItem"
public class ChargingStation {
    private String stationName;
    private int availableChargingPoints;

    // 3. On restaure le champ 'EnergyProvider'
    private EnergyProvider energyProvider;

    // 4. On restaure le constructeur avec 'EnergyProvider'
    public ChargingStation(String stationName, int availableChargingPoints, EnergyProvider energyProvider) {
        this.stationName = stationName;
        this.availableChargingPoints = availableChargingPoints;
        this.energyProvider = energyProvider;
    }

    public ChargingStation(String stationName, int availableChargingPoints) {
        this.stationName = stationName;
        this.availableChargingPoints = availableChargingPoints;
        // 5. On restaure 'new EnergyProvider'
        this.energyProvider = new EnergyProvider("EDF", "Solar");
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getAvailableChargingPoints() {
        return availableChargingPoints;
    }

    public void setAvailableChargingPoints(int availableChargingPoints) {
        this.availableChargingPoints = availableChargingPoints;
    }

    // 6. On restaure les méthodes pour 'EnergyProvider'
    public EnergyProvider getEnergyProvider() {
        return energyProvider;
    }

    public void setEnergyProvider(EnergyProvider energyProvider) {
        this.energyProvider = energyProvider;
    }


    public double connectToChargingPoint(ChargeableItem vehicle) {
        double charge = 0;
        if (availableChargingPoints > 0 && vehicle.connect()) {
            availableChargingPoints--;
            charge = vehicle.chargeToFull();
        }
        return charge;
    }

    // 8. ET CELLE-CI
    public void disconnectFromChargingPoint(ChargeableItem vehicle) {
        if (vehicle.getCurrentCharge() == vehicle.getBatteryCapacity()) {
            vehicle.disconnect();
            availableChargingPoints++;
        }
    }
}