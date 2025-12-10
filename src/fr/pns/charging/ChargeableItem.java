package fr.pns.charging;

public interface ChargeableItem {

    // Méthodes requises par connectToChargingPoint
    boolean connect();
    double chargeToFull();

    // Méthodes requises par disconnectFromChargingPoint
    double getCurrentCharge();
    double getBatteryCapacity();
    void disconnect();
}