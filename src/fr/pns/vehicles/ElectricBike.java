package fr.pns.vehicles;
import fr.pns.rentals.RentableItem;

public class ElectricBike extends ElectricVehicle implements RentableItem {
    private double[] energyConsumptionLevels = {0.1,0.2,0.3,0.4};
    private int pedalAssistLevel;
    private int maxAssistLevel;
    private boolean isAvailable;
    private final static String PREFIX = "EB-";
    private static int nextIdentifier = 1;
    private final String identifier;


    public ElectricBike(double batteryCapacity, double eCpK, String identifier) {
        super(batteryCapacity, eCpK);
        this.identifier = identifier;
    }

    public ElectricBike(double batteryCapacity,double[] energyConsumptionLevels) {
        super(batteryCapacity);
        this.energyConsumptionLevels = energyConsumptionLevels;
        this.pedalAssistLevel = 0;
        this.maxAssistLevel = energyConsumptionLevels.length - 1;
        this.isAvailable = true;
        this.identifier = PREFIX + nextIdentifier;
        nextIdentifier++;
    }
    public void setPedalAssistLevel(int level) {
        if (level > maxAssistLevel) {
            pedalAssistLevel = maxAssistLevel;
        } else pedalAssistLevel = Math.max(level, 0);
    }
    public int getPedalAssistLevel() {
        return pedalAssistLevel;
    }
    public double getEnergyConsumptionForAssistLevel(int level) {
        if (level < 0) return energyConsumptionLevels[0];
        if (level > maxAssistLevel) return energyConsumptionLevels[maxAssistLevel];
        return energyConsumptionLevels[level];
    }
    @Override
    public double getEnergyConsumptionPerKilometer() {
        return getEnergyConsumptionForAssistLevel(pedalAssistLevel);
    }

    public int getNumberOfAvailableLevels() {
        return energyConsumptionLevels.length;
    }
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void markAsDamaged() {
        setAvailable(false);
    }

    public void markAsAvailable() {
        setAvailable(true);
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String getName() {
        return this.identifier;
    }

    @Override
    public boolean match(String description) {
        String levelsStr = String.valueOf(this.getNumberOfAvailableLevels());
        // On vérifie une ÉGALITÉ exacte pour les niveaux
        return description.contains(this.identifier)
                || description.equals(levelsStr) // <-- CORRIGÉ
                || description.contains("*");
    }
    protected static void resetIdentifier() {
        nextIdentifier = 1;
    }


}
