package fr.pns.vehicles;

import fr.pns.charging.ChargeableItem;

public abstract class ElectricVehicle implements ChargeableItem {

    private double batteryCapacity;
    private double currentCharge;
    private double energyConsumptionPerKilometer;
    private boolean connected;

    // Constructeur complet
    public ElectricVehicle(double batteryCapacity, double energyConsumptionPerKilometer) {
        this.batteryCapacity = batteryCapacity;
        this.energyConsumptionPerKilometer = energyConsumptionPerKilometer;
        this.currentCharge = 0;
        this.connected = false;
    }

    // Constructeur avec consommation par défaut
    public ElectricVehicle(double batteryCapacity) {
        this(batteryCapacity, 0.2);
    }

    // --- Implémentation de ChargeableItem ---

    @Override
    public boolean charge(double chargeAmount) {
        if (chargeAmount <= 0) {
            throw new IllegalArgumentException("La charge doit être positive !");
        }
        // On vérifie de ne pas dépasser la capacité (optionnel mais logique)
        if (currentCharge + chargeAmount > batteryCapacity) {
            return false;
        }
        currentCharge += chargeAmount;
        return true;
    }

    @Override
    public double chargeToFull() {
        double amountNeeded = batteryCapacity - currentCharge;
        currentCharge = batteryCapacity;
        return amountNeeded;
    }

    @Override
    public boolean connect() {
        if (!connected) {
            connected = true;
            return true;
        }
        return false;
    }

    @Override
    public void disconnect() {
        connected = false;
    }

    public boolean isConnected() {
        return connected;
    }

    // --- Méthodes Métier Véhicule ---

    public boolean drive(double distance) {
        double energyNeeded = distance * energyConsumptionPerKilometer;
        if (currentCharge >= energyNeeded) {
            currentCharge -= energyNeeded;
            return true;
        }
        return false;
    }

    // --- Getters ---

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public double getCurrentCharge() {
        return currentCharge;
    }

    // Important pour la surcharge dans les classes filles (ex: Vélos)
    public double getEnergyConsumptionPerKilometer() {
        return energyConsumptionPerKilometer;
    }

    // Méthodes utilitaires (vues dans le TD1)
    public double getRemainingRange() {
        if(getEnergyConsumptionPerKilometer() == 0) return 0;
        return currentCharge / getEnergyConsumptionPerKilometer();
    }

    public double calculateMaxRange() {
        if(getEnergyConsumptionPerKilometer() == 0) return 0;
        return batteryCapacity / getEnergyConsumptionPerKilometer();
    }
}