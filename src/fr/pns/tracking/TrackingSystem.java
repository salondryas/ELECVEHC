package fr.pns.tracking;

import java.util.ArrayList;
import java.util.List;

public class TrackingSystem {
    private List<Trackable> trackedObjects;

    public TrackingSystem() {
        this.trackedObjects = new ArrayList<>();
    }


    public void addTrackableObject(Trackable object) {
        if (object != null) {
            this.trackedObjects.add(object);
        }
    }
    public int getNumberOfTrackedObjects() {
        return this.trackedObjects.size();
    }

    public Position getTrackableObjectPosition(int index) {
        if (index < 0 || index >= this.trackedObjects.size()) {
            return null;
        }

        Trackable object = this.trackedObjects.get(index);
        if (object == null) {
            return null;
        }
        return object.getPosition(); // Renvoie la position, ou null si elle l'était
    }

    public List<Position> getAllTrackablePositions() {
        List<Position> allPositions = new ArrayList<>();
        for (Trackable object : this.trackedObjects) {
            if (object != null) {
                Position pos = object.getPosition();
                if (pos != null) {
                    allPositions.add(pos);
                }
            }
        }
        return allPositions;
    }
    public List<Trackable> getClosestTrackableObject(Position position) {
        if (trackedObjects.isEmpty())
            return null;
        List<Trackable> closestObjects = new ArrayList<>();
        double minDistance = Double.POSITIVE_INFINITY;

        for (Trackable object : trackedObjects) {
            Position objPos = object.getPosition();
            if (objPos != null) {
                double distance = position.distance(objPos);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestObjects.clear();
                    closestObjects.add(object);
                } else if (distance == minDistance) {
                    closestObjects.add(object);
                }
            }
        }
        return closestObjects;
    }
}