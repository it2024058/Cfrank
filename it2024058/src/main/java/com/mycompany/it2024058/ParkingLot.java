/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.it2024058;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ParkingLot {

    private static final int TOTAL_SPOTS = 100;
    private static final int ELECTRIC_SPOT_START = 80; // Spots 81-100 are electric
    private static final double HOURLY_RATE_REGULAR = 2.0;
    private static final double HOURLY_RATE_ELECTRIC = 1.5;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final List<ParkingSpot> spots;
    private final List<ParkingSession> sessions;

    public ParkingLot() {
        this.spots = new ArrayList<>();
        this.sessions = new ArrayList<>();
        initializeSpots();
    }

    private void initializeSpots() {
        for (int i = 0; i < TOTAL_SPOTS; i++) {
            boolean isElectric = i >= ELECTRIC_SPOT_START;
            spots.add(new ParkingSpot(i + 1, isElectric));
        }
    }
 
    public boolean parkVehicle(Scanner scanner) {
        try {
            System.out.print("Enter license plate: ");
            String plate = validateLicensePlate(scanner.nextLine().trim());

            Vehicle vehicle = VehicleRegistry.getVehicleByPlate(plate);
            Driver driver;

            if (vehicle == null) {
                vehicle = createNewVehicle(scanner, plate);
                if (vehicle == null) return false;

                driver = vehicle.getDriver();
                VehicleRegistry.addVehicle(vehicle);
                UserRegistry.addRecord(driver.getFullName(), plate);
            } else {
                driver = vehicle.getDriver();
                if (isVehicleCurrentlyParked(plate)) {
                    System.out.println("Vehicle is already parked!");
                    return false;
                }
            }

            int duration = getParkingDuration(scanner);
            if (duration == -1) return false;

            List<ParkingSpot> assignedSpots = findSuitableSpots(vehicle);
            if (assignedSpots.isEmpty()) {
                System.out.println("No suitable spots available for this vehicle type.");
                return false;
            }

            LocalDateTime now = LocalDateTime.now();
            for (ParkingSpot spot : assignedSpots) {
                spot.occupy(vehicle);
            }

            ParkingSession session = new ParkingSession(vehicle, driver, assignedSpots, now, duration);
            sessions.add(session);

            System.out.println("Vehicle parked successfully at spot(s): " +
                    assignedSpots.stream()
                            .map(spot -> String.valueOf(spot.getSpotId()))
                            .collect(Collectors.joining(", ")));

            return true;

        } catch (Exception e) {
            System.out.println("Error parking vehicle: " + e.getMessage());
            return false;
        }
    }

    public boolean exitVehicle(Scanner scanner) {
        try {
            System.out.print("Enter license plate: ");
            String plate = validateLicensePlate(scanner.nextLine().trim());

            Optional<ParkingSession> activeSession = sessions.stream()
                    .filter(session -> session.getVehicle().getLicensePlate().equalsIgnoreCase(plate)
                            && !session.isEnded())
                    .findFirst();

            if (activeSession.isEmpty()) {
                System.out.println("No active parking session found for this vehicle.");
                return false;
            }

            ParkingSession session = activeSession.get();
            session.isEnded();

            session.getSpots().forEach(ParkingSpot::vacate);

            double charge = calculateCharge(session);
            System.out.printf("Vehicle exited successfully. Total charge: €%.2f%n", charge);
            System.out.printf("Parking duration: %.1f hours%n", session.getDurationHours());

            return true;

        } catch (Exception e) {
            System.out.println("Error during vehicle exit: " + e.getMessage());
            return false;
        }
    }

    private List<ParkingSpot> findSuitableSpots(Vehicle vehicle) {
        int spotsNeeded = vehicle.getSize();
        List<ParkingSpot> result = new ArrayList<>();

        if ("electric".equalsIgnoreCase(vehicle.getFuelType())) {
            result = findConsecutiveSpots(spotsNeeded, true, vehicle);
            if (!result.isEmpty()) return result;
        }

        return findConsecutiveSpots(spotsNeeded, false, vehicle);
    }

    private List<ParkingSpot> findConsecutiveSpots(int needed, boolean preferElectric, Vehicle vehicle) {
        for (int i = 0; i <= spots.size() - needed; i++) {
            List<ParkingSpot> candidate = new ArrayList<>();
            boolean valid = true;

            for (int j = 0; j < needed; j++) {
                ParkingSpot spot = spots.get(i + j);

                if (!spot.canBeUsedBy(vehicle) ||
                        (preferElectric && !spot.isForElectric()) ||
                        (!preferElectric && spot.isForElectric())) {
                    valid = false;
                    break;
                }
                candidate.add(spot);
            }

            if (valid) return candidate;
        }
        return new ArrayList<>();
    }

    private double calculateCharge(ParkingSession session) {
        double hours = session.getDurationHours();
        boolean isElectricSpot = session.getSpots().get(0).isForElectric();
        double rate = isElectricSpot ? HOURLY_RATE_ELECTRIC : HOURLY_RATE_REGULAR;

        return hours * rate * session.getSpots().size();
    }

    public void printAvailability() {
        System.out.println("\n=== PARKING LOT AVAILABILITY ===");
        System.out.printf("Total spots: %d | Available: %d | Occupied: %d%n",
                spots.size(), getAvailableSpotCount(), getOccupiedSpotCount());

        System.out.println("\nRegular Spots (1-80):");
        printSpotRange(1, ELECTRIC_SPOT_START, false);

        System.out.println("\nElectric Spots (81-100):");
        printSpotRange(ELECTRIC_SPOT_START + 1, TOTAL_SPOTS, true);
    }

    private void printSpotRange(int start, int end, boolean isElectric) {
        for (int i = start; i <= end; i++) {
            ParkingSpot spot = spots.get(i - 1);
            String status = spot.isOccupied() ? "OCCUPIED" : "FREE";
            String type = isElectric ? "⚡" : "🚗";

            System.out.printf("Spot %3d %s [%s]", spot.getSpotId(), type, status);
            if (spot.isOccupied() && spot.getVehicle() != null) {
                System.out.printf(" - %s", spot.getVehicle().getLicensePlate());
            }
            System.out.println();
        }
    }

    public void printHistoryByPhone(Scanner scanner) {
        System.out.print("Enter driver's phone: ");
        String phone = scanner.nextLine().trim();

        List<ParkingSession> driverSessions = sessions.stream()
                .filter(session -> session.getDriver().getPhone().equals(phone))
                .sorted((s1, s2) -> s2.getStartTime().compareTo(s1.getStartTime()))
                .collect(Collectors.toList());

        if (driverSessions.isEmpty()) {
            System.out.println("No parking history found for this phone number.");
            return;
        }

        System.out.println("\n=== PARKING HISTORY FOR PHONE: " + phone + " ===");
        for (ParkingSession session : driverSessions) {
            System.out.printf("License: %s | Start: %s | Duration: %.1fh | Charge: €%.2f%n",
                    session.getVehicle().getLicensePlate(),
                    session.getStartTime().format(DATE_TIME_FORMATTER),
                    session.getDurationHours(),
                    calculateCharge(session));
        }
    }

    public void printHistoryByLicense(Scanner scanner) {
        System.out.print("Enter license plate: ");
        String plate = validateLicensePlate(scanner.nextLine().trim());

        List<ParkingSession> vehicleSessions = sessions.stream()
                .filter(session -> session.getVehicle().getLicensePlate().equalsIgnoreCase(plate))
                .sorted((s1, s2) -> s2.getStartTime().compareTo(s1.getStartTime()))
                .collect(Collectors.toList());

        if (vehicleSessions.isEmpty()) {
            System.out.println("No parking history found for this license plate.");
            return;
        }

        System.out.println("\n=== PARKING HISTORY FOR LICENSE: " + plate + " ===");
        for (ParkingSession session : vehicleSessions) {
            System.out.printf("Driver: %s | Start: %s | Duration: %.1fh | Charge: €%.2f%n",
                    session.getDriver().getFullName(),
                    session.getStartTime().format(DATE_TIME_FORMATTER),
                    session.getDurationHours(),
                    calculateCharge(session));
        }
    }

    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("=== PARKING LOT STATUS REPORT ===\n");
            writer.write("Generated: " + LocalDateTime.now().format(DATE_TIME_FORMATTER) + "\n\n");

            for (ParkingSpot spot : spots) {
                if (spot.isOccupied()) {
                    Optional<ParkingSession> activeSession = sessions.stream()
                            .filter(s -> s.getSpots().contains(spot) && !s.isEnded())
                            .findFirst();

                    if (activeSession.isPresent()) {
                        ParkingSession session = activeSession.get();
                        writer.write(String.format("Spot %d [%s]: %s | Driver: %s | Phone: %s | Start: %s\n",
                                spot.getSpotId(),
                                spot.isForElectric() ? "ELECTRIC" : "REGULAR",
                                session.getVehicle().getLicensePlate(),
                                session.getDriver().getFullName(),
                                session.getDriver().getPhone(),
                                session.getStartTime().format(DATE_TIME_FORMATTER)));
                    }
                } else {
                    writer.write(String.format("Spot %d [%s]: FREE\n",
                            spot.getSpotId(),
                            spot.isForElectric() ? "ELECTRIC" : "REGULAR"));
                }
            }

            System.out.println("Status report saved to: " + filename);

        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    // Helper methods
    private Vehicle createNewVehicle(Scanner scanner, String plate) {
        try {
            System.out.print("Enter vehicle type (car/motorcycle/truck): ");
            String type = scanner.nextLine().toLowerCase().trim();

            System.out.print("Enter fuel type (gas/electric): ");
            String fuel = scanner.nextLine().toLowerCase().trim();

            if (!fuel.equals("gas") && !fuel.equals("electric")) {
                System.out.println("Invalid fuel type. Use 'gas' or 'electric'.");
                return null;
            }

            Driver driver = createNewDriver(scanner);
            if (driver == null) return null;

            switch (type) {
                case "car":
                    return new Car(plate, fuel, driver);
                case "motorcycle":
                    return new Motorcycle(plate, fuel, driver);
                case "truck":
                    double length = getTruckLength(scanner);
                    if (length == -1) return null;
                    String usage = getTruckUsage(scanner);
                    return new Truck(plate, fuel, driver, length, usage);
                default:
                    System.out.println("Invalid vehicle type. Use 'car', 'motorcycle', or 'truck'.");
                    return null;
            }
        } catch (Exception e) {
            System.out.println("Error creating vehicle: " + e.getMessage());
            return null;
        }
    }

    private Driver createNewDriver(Scanner scanner) {
        try {
            System.out.print("Enter driver's first name: ");
            String firstName = scanner.nextLine().trim();
            if (firstName.isEmpty()) {
                System.out.println("First name cannot be empty.");
                return null;
            }

            System.out.print("Enter driver's last name: ");
            String lastName = scanner.nextLine().trim();
            if (lastName.isEmpty()) {
                System.out.println("Last name cannot be empty.");
                return null;
            }

            System.out.print("Enter driver's phone: ");
            String phone = scanner.nextLine().trim();
            if (!isValidPhone(phone)) {
                System.out.println("Invalid phone number format.");
                return null;
            }

            return new Driver(firstName, lastName, phone);
        } catch (Exception e) {
            System.out.println("Error creating driver: " + e.getMessage());
            return null;
        }
    }

    private String validateLicensePlate(String plate) {
        if (!plate.matches("[A-Za-z]{3}[0-9]{4}")) {
            throw new IllegalArgumentException("License plate must be exactly 3 letters followed by 3 digits (e.g. ABC123).");
        }
        return plate.toUpperCase();
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("\\+?[0-9]{7,15}");
    }

    private int getParkingDuration(Scanner scanner) {
        try {
            System.out.print("Enter parking duration (hours, 1-24): ");
            int duration = Integer.parseInt(scanner.nextLine().trim());
            if (duration < 1 || duration > 24) {
                System.out.println("Duration must be between 1 and 24 hours.");
                return -1;
            }
            return duration;
        } catch (NumberFormatException e) {
            System.out.println("Invalid duration input.");
            return -1;
        }
    }

    private boolean isVehicleCurrentlyParked(String plate) {
        return sessions.stream()
                .anyMatch(session -> session.getVehicle().getLicensePlate().equalsIgnoreCase(plate) && !session.isEnded());
    }

    private double getTruckLength(Scanner scanner) {
        try {
            System.out.print("Enter truck length in meters (e.g., 7.5): ");
            double length = Double.parseDouble(scanner.nextLine().trim());
            if (length <= 0) {
                System.out.println("Length must be positive.");
                return -1;
            }
            return length;
        } catch (NumberFormatException e) {
            System.out.println("Invalid length input.");
            return -1;
        }
    }

    private String getTruckUsage(Scanner scanner) {
        System.out.print("Enter truck usage (personal/commercial): ");
        String usage = scanner.nextLine().trim().toLowerCase();
        if (!usage.equals("personal") && !usage.equals("commercial")) {
            System.out.println("Usage must be 'personal' or 'commercial'.");
            return null;
        }
        return usage;
    }

    private int getAvailableSpotCount() {
        return (int) spots.stream().filter(spot -> !spot.isOccupied()).count();
    }

    private int getOccupiedSpotCount() {
        return (int) spots.stream().filter(ParkingSpot::isOccupied).count();
    }
    
    public List<ParkingSpot> getSpots(){
        return spots;
    }
    
    public List<ParkingSession> getSessions(){
        return sessions;
    }
}