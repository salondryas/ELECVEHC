package fr.pns.vehicles;

import java.util.Scanner;

public class InteractionsWithDrone {

    /*****************
     * Test drone by user interaction
     * Only for demonstration purpose
     * and help for ps5 project
     * IT IS NOT A UNIT TEST
     ************ */

    public static void main(String[] args) {
        Drone drone = new Drone(30, 0.2);
        System.out.println("Drone battery capacity: " + drone.getBatteryCapacity() + " kWh");
        System.out.println("Drone current charge: " + drone.getCurrentCharge() + " kWh");

        try {
            Scanner sc = new Scanner(System.in);
            boolean playing = doYouWantToPlay(sc);

            while (playing) {
                playWithDrone(drone, sc);
                playing = doYouWantToPlay(sc);
            }
            System.out.println("Bye bye");
            sc.close();
        } catch (Exception e) {
            System.out.println("Error during interaction with user");
        }
    }

    private static void playWithDrone(Drone drone, Scanner sc) {

        askToChargeDrone(drone, sc);

        System.out.println("Enter the distance to fly (km): ");
        double distance = readDouble(sc);

        double max = drone.getRemainingRange();
        if (distance > max) {
            System.out.println("Distance exceeds maximum possible range : " + max + " km.");
            System.out.println("Do you want to fly the maximum range instead? (y/n)");
            if (readYes(sc)) {
                distance = max;
            } else {
                System.out.println("Flight cancelled.");
                return;
            }
        }
        driveDistance(drone, distance);
    }

    private static void askToChargeDrone(Drone drone, Scanner sc) {
        System.out.println("Do you want to charge the drone? (y/n)");
        if (readYes(sc)) {
            System.out.println("Enter the amount of charge to add (kWh): ");
            double charge = readDouble(sc);
            drone.charge(charge);
            System.out.println("Drone current charge: " + drone.getCurrentCharge() + " kWh");
        }
    }

    private static double readDouble(Scanner sc) {
        double value = sc.nextDouble();
        sc.nextLine(); // consume the newline
        return value;
    }

    private static boolean readYes(Scanner sc) {
        String answer = sc.nextLine();
        return (answer.equals("y") || answer.equals("Y"));
    }


    private static boolean doYouWantToPlay(Scanner sc) {
        System.out.println("Do you play with the drone? (y/n)");
        String answer = sc.nextLine();
        return (answer.equals("y") || answer.equals("Y"));
    }


    private static void driveDistance(Drone drone, double distance) {
        drone.drive(distance);
        System.out.println("Drone flew " + distance + " km.");
        System.out.println("Drone current charge: " + drone.getCurrentCharge() + " kWh");
    }
}