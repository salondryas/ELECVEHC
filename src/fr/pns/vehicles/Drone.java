package fr.pns.vehicles;

import fr.pns.tracking.Position;
import fr.pns.tracking.Trackable;

public class Drone extends ElectricVehicle implements Trackable {

    private static final Position BASE_POSITION = new Position(0, 0, 0);
    private Position currentPosition;
    private boolean isFlying;

    public Drone(double batteryCapacity, double eCpK) {
        super(batteryCapacity, eCpK);
        this.currentPosition = BASE_POSITION;
        this.isFlying = false;
    }

    public Drone(double batteryCapacity) {
        super(batteryCapacity);
        this.currentPosition = BASE_POSITION;
        this.isFlying = false;
    }

    @Override
    public Position getPosition() {
        if (this.isFlying) {
            return new Position(Math.random(), Math.random(), Math.random());
        } else {
            return this.currentPosition;
        }
    }

    public void takeOff() {
        this.isFlying = true;
    }

    public void returnToBase() {
        this.isFlying = false;
        this.currentPosition = BASE_POSITION;
    }
    public boolean driveTo(Position targetPosition) {
        // 1. Calculer la distance
        double distance = this.currentPosition.distance(targetPosition);

        if (distance == 0) {
            return true;
        }
        boolean driveSuccess = this.drive(distance);

        if (driveSuccess) {
            this.currentPosition = targetPosition;
            return true;
        }
        return false;
    }
}