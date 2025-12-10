package fr.pns.fleets;

import fr.pns.vehicles.Drone;
import fr.pns.tracking.TrackingSystem; // Import du TD3
import fr.pns.tracking.Position;     // Import du TD3
import java.util.List;

// Elle hérite de votre flotte générique, mais force le type <Drone>
public class DroneFleet extends FleetOfVehicules<Drone> {

    private TrackingSystem trackingSystem;

    public DroneFleet() {
        this.trackingSystem = new TrackingSystem();
    }

    // On surcharge add pour enregistrer le drone dans le tracking system en même temps
    @Override
    public boolean add(Drone drone) {
        boolean added = super.add(drone);
        if (added) {
            trackingSystem.addTrackableObject(drone);
        }
        return added;
    }

    // Nouvelle fonctionnalité spécifique aux drones
    public List<Position> getAllDronePositions() {
        return trackingSystem.getAllTrackablePositions();
    }
}