package fr.pns.fleets;

import fr.pns.vehicles.ElectricVehicle;

import java.util.ArrayList;

public class FleetOfVehicules<T extends ElectricVehicle> extends ArrayList<T> {
    public double chargeFullAllVehicles() {
        double energyNeeded = 0;
        for (T vehicle : this) {
            energyNeeded += vehicle.chargeToFull();
        }
        return energyNeeded;
    }
    public void driveAllVehicles(double distance) {
        for (T vehicle : this)
            vehicle.drive(distance);
    }

}
