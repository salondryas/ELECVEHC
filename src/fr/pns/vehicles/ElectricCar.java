package fr.pns.vehicles;

import fr.pns.rentals.RentableItem;

public class ElectricCar extends ElectricVehicle implements RentableItem {

    private boolean coolingSystemActive;
    private static final double COOLING_SYSTEM_FACTOR = 1.2;
    private String licensePlate;
    private String model;
    private boolean isAvailable;

    public ElectricCar(double batteryCapacity, String licensePlate, String model) {
        super(batteryCapacity);
        this.licensePlate = licensePlate;
        this.model = (model != null && !model.isEmpty()) ? model : "undefined";
        this.coolingSystemActive = false;
        this.isAvailable = true;
    }

    public ElectricCar(double batteryCapacity, String licensePlate) {
        this(batteryCapacity, licensePlate, "undefined");
    }

    public void turnOnCoolingSystem() {
        this.coolingSystemActive = true;
    }

    public void turnOffCoolingSystem() {
        this.coolingSystemActive = false;
    }

    public boolean isOnCoolingSystem() {
        return coolingSystemActive;
    }

    @Override
    public double getEnergyConsumptionPerKilometer() {
        double baseConsumption = super.getEnergyConsumptionPerKilometer();
        if (coolingSystemActive) {
            return baseConsumption * COOLING_SYSTEM_FACTOR; // +20%
        } else {
            return baseConsumption;
        }
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return "ElectricCar{" +
                "licensePlate='" + licensePlate + '\'' +
                ", model='" + model + '\'' +
                ", coolingSystemActive=" + coolingSystemActive +
                ", currentCharge=" + getCurrentCharge() +
                ", batteryCapacity=" + getBatteryCapacity() +
                '}';
    }
    @Override
    public boolean isAvailable() {
        return this.isAvailable;
    }

    @Override
    public String getName() {
        return this.licensePlate;
    }

    @Override
    public boolean match(String description) {
        final String cleanDescription = description.trim().toLowerCase();
        final String NOT_PREFIX = "not:";
        if (cleanDescription.startsWith(NOT_PREFIX)) {
            String criterion = cleanDescription.substring(NOT_PREFIX.length()).trim();
            boolean baseMatch = this.model.toLowerCase().contains(criterion)
                    || this.licensePlate.toLowerCase().contains(criterion);
            return !baseMatch;

        } else {
            return this.model.toLowerCase().contains(cleanDescription)
                    || this.licensePlate.toLowerCase().contains(cleanDescription);
        }
    }
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
    public void markAsDamaged() {
        this.setAvailable(false);
    }
    public void markAsAvailable() {
        this.setAvailable(true);
    }
}
