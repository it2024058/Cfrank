/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.it2024058;

import java.util.Scanner;


public class ParkingManager {
    
    private final Scanner scanner;
    private final ParkingLot parkingLot;
    
    public ParkingManager(Scanner scanner) {
        this.scanner = scanner;
        this.parkingLot = new ParkingLot();
    }
    
    
    public void startApplication() {
        System.out.println("[P]️  Welcome to Smart Parking Management System");
        System.out.println("=".repeat(50));
        
        while (true) {
            try {
                displayMenu();
                int choice = getMenuChoice();
                
                if (!handleMenuChoice(choice)) {
                    break; // Exit application
                }
                
                System.out.println(); // Add spacing between operations
                
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
        
        System.out.println("Thank you for using Smart Parking Management System!");
    }
    
   
    private void displayMenu() {
        System.out.println("=== MAIN MENU ===");
        System.out.println("1. Park Vehicle");
        System.out.println("2. Exit Vehicle"); 
        System.out.println("3. View Spot Availability");
        System.out.println("4. Search History by Phone");
        System.out.println("5. Search History by License Plate");
        System.out.println("6. Generate Status Report");
        System.out.println("7. View Statistics");
        System.out.println("0. Exit Application");
        System.out.print("Enter your choice (0-7): ");
    }
    
    
    private int getMenuChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice < 0 || choice > 7) {
                System.out.println("Invalid choice. Please enter a number between 0-7.");
                return -1;
            }
            return choice;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return -1;
        }
    }
    
    /**
     * Χειρισμός επιλογής μενού
     */
    private boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> handleParkVehicle();
            case 2 -> handleExitVehicle();
            case 3 -> handleViewAvailability();
            case 4 -> handleSearchByPhone();
            case 5 -> handleSearchByLicense();
            case 6 -> handleGenerateReport();
            case 7 -> handleViewStatistics();
            case 0 -> {
                return false; // Exit application
            }
            case -1 -> {
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
        // Invalid input, stay in loop
        return true;
    }
    
    /**
     * Χειρισμός στάθμευσης οχήματος
     */
    private void handleParkVehicle() {
        System.out.println("\n=== PARK VEHICLE ===");
        
        boolean success = parkingLot.parkVehicle(scanner);
        
        if (success) {
            System.out.println("✅ Vehicle parked successfully!");
            if (askYesOrNo("Would you like to view current availability?")) {
                parkingLot.printAvailability();
            }
        } else {
            System.out.println("❌ Failed to park vehicle.");
            if (askYesOrNo("Would you like to view current availability?")) {
                parkingLot.printAvailability();
            }
        }
    }
    
    /**
     * Χειρισμός εξόδου οχήματος
     */
    private void handleExitVehicle() {
        System.out.println("\n=== EXIT VEHICLE ===");
        
        boolean success = parkingLot.exitVehicle(scanner);
        
        if (success) {
            System.out.println(":) Vehicle exited successfully!");
        } else {
            System.out.println(":( Failed to exit vehicle.");
        }
    }
    
    /**
     * Εμφάνιση διαθεσιμότητας
     */
    private void handleViewAvailability() {
        System.out.println("\n=== PARKING AVAILABILITY ===");
        parkingLot.printAvailability();
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Αναζήτηση ιστορικού ανά τηλέφωνο
     */
    private void handleSearchByPhone() {
        System.out.println("\n=== SEARCH HISTORY BY PHONE ===");
        parkingLot.printHistoryByPhone(scanner);
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Αναζήτηση ιστορικού ανά πινακίδα
     */
    private void handleSearchByLicense() {
        System.out.println("\n=== SEARCH HISTORY BY LICENSE PLATE ===");
        parkingLot.printHistoryByLicense(scanner);
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Δημιουργία αναφοράς κατάστασης
     */
    private void handleGenerateReport() {
        System.out.println("\n=== GENERATE STATUS REPORT ===");
        
        System.out.print("Enter filename (or press Enter for default 'parking_report.txt'): ");
        String filename = scanner.nextLine().trim();
        
        if (filename.isEmpty()) {
            filename = "parking_report.txt";
        }
        
        if (!filename.endsWith(".txt")) {
            filename += ".txt";
        }
        
        parkingLot.saveToFile(filename);
        System.out.println(":) Report generated successfully!");
    }
    
    private void handleViewStatistics() {
        System.out.println("\n=== PARKING STATISTICS ===");
        
         
        
        long totalSpots = parkingLot.getSpots().size();
        long occupiedSpots = parkingLot.getSpots().stream().filter(spot -> spot.isOccupied()).count();
        long availableSpots = totalSpots - occupiedSpots;

        long electricSpots = parkingLot.getSpots().stream().filter(spot -> spot.isForElectric()).count();
        long occupiedElectricSpots = parkingLot.getSpots().stream()
            .filter(spot -> spot.isForElectric() && spot.isOccupied()).count();

long totalSessions = parkingLot.getSessions().size();
long activeSessions = parkingLot.getSessions().stream()
    .filter(session -> !session.isEnded()).count();
    
long regularSpots = totalSpots - electricSpots;
long occupiedRegularSpots = occupiedSpots - occupiedElectricSpots;

        double occupancyRate = ((double) occupiedSpots / totalSpots) * 100;
        double electricOccupancyRate = electricSpots > 0 ? 
        ((double) occupiedElectricSpots / electricSpots) * 100 : 0;
        double regularOccupancyRate = regularSpots > 0 ? 
        ((double) occupiedRegularSpots / regularSpots) * 100 : 0;
        
        System.out.printf("📊 OVERALL STATISTICS\n");
        System.out.printf("Total Parking Spots: %d\n", totalSpots);
        System.out.printf("Occupied Spots: %d\n", occupiedSpots);
        System.out.printf("Available Spots: %d\n", availableSpots);
        System.out.printf("Overall Occupancy Rate: %.1f%%\n\n", occupancyRate);
        
        System.out.printf("⚡ ELECTRIC SPOTS\n");
        System.out.printf("Total Electric Spots: %d\n", electricSpots);
        System.out.printf("Occupied Electric Spots: %d\n", occupiedElectricSpots);
        System.out.printf("Electric Occupancy Rate: %.1f%%\n\n", electricOccupancyRate);
        
        System.out.printf("🚗 REGULAR SPOTS\n");
        System.out.printf("Total Regular Spots: %d\n", regularSpots);
        System.out.printf("Occupied Regular Spots: %d\n", occupiedRegularSpots);
        System.out.printf("Regular Occupancy Rate: %.1f%%\n\n", regularOccupancyRate);
        
        System.out.printf("📈 SESSION STATISTICS\n");
        System.out.printf("Total Parking Sessions: %d\n", totalSessions);
        System.out.printf("Active Sessions: %d\n", activeSessions);
        System.out.printf("Completed Sessions: %d\n", totalSessions - activeSessions);
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    

    private boolean askYesOrNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (yes/no): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            
            switch (answer) {
                case "yes", "y" -> {
                    return true;
                }
                case "no", "n" -> {
                    return false;
                }
                default -> System.out.println("Please enter 'yes' or 'no'.");
            }
        }
    }
    

    public void handleBatchOperations() {
        System.out.println("\n=== BATCH OPERATIONS ===");
        System.out.println("1. Add Multiple Vehicles");
        System.out.println("2. Remove Multiple Vehicles");
        System.out.println("3. Clear All Parking Spots");
        System.out.print("Enter your choice (1-3): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1 -> handleBatchParkVehicles();
                case 2 -> handleBatchRemoveVehicles();
                case 3 -> handleClearAllSpots();
                default -> System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    /**
     * Προσθήκη πολλών οχημάτων διαδοχικά
     */
    private void handleBatchParkVehicles() {
        System.out.println("\n=== BATCH PARK VEHICLES ===");
        System.out.print("How many vehicles do you want to park? ");
        
        try {
            int count = Integer.parseInt(scanner.nextLine().trim());
            if (count <= 0) {
                System.out.println("Invalid count.");
                return;
            }
            
            int successful = 0;
            int failed = 0;
            
            for (int i = 1; i <= count; i++) {
                System.out.println("\n--- Vehicle " + i + " of " + count + " ---");
                
                if (parkingLot.parkVehicle(scanner)) {
                    successful++;
                    System.out.println(":) Vehicle " + i + " parked successfully!");
                } else {
                    failed++;
                    System.out.println(":( Failed to park vehicle " + i);
                }
                
                // Ask if user wants to continue after failures
                if (failed > 0 && i < count) {
                    if (!askYesOrNo("Continue with remaining vehicles?")) {
                        break;
                    }
                }
            }
            
            System.out.printf("\n[+] Batch Operation Summary:\n");
            System.out.printf("Successfully parked: %d\n", successful);
            System.out.printf("Failed to park: %d\n", failed);
            
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    /**
     * Αφαίρεση πολλών οχημάτων διαδοχικά
     */
    private void handleBatchRemoveVehicles() {
        System.out.println("\n=== BATCH REMOVE VEHICLES ===");
        
        // Show currently parked vehicles
        var occupiedSpots = parkingLot.getSpots().stream()
            .filter(spot -> spot.isOccupied())
            .toList();
        
        if (occupiedSpots.isEmpty()) {
            System.out.println("No vehicles are currently parked.");
            return;
        }
        
        System.out.println("Currently parked vehicles:");
        occupiedSpots.forEach(spot -> {
            if (spot.getVehicle() != null) {
                System.out.printf("Spot %d: %s\n", 
                    spot.getSpotId(), 
                    spot.getVehicle().getLicensePlate());
            }
        });
        
        System.out.println("\nEnter license plates to remove (enter 'done' when finished):");
        
        int successful = 0;
        int failed = 0;
        
        while (true) {
            System.out.print("License plate (or 'done'): ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            
            if (input.isEmpty()) {
                System.out.println("Please enter a license plate or 'done'.");
                continue;
            }
            
            // Simulate exit vehicle operation
            boolean removed = simulateExitVehicle(input);
            
            if (removed) {
                successful++;
                System.out.println("✅ Vehicle " + input + " removed successfully!");
            } else {
                failed++;
                System.out.println("❌ Failed to remove vehicle " + input);
            }
        }
        
        System.out.printf("\n📊 Batch Removal Summary:\n");
        System.out.printf("Successfully removed: %d\n", successful);
        System.out.printf("Failed to remove: %d\n", failed);
    }
    
    /**
     * Καθαρισμός όλων των parking spots (για testing/reset)
     */
    private void handleClearAllSpots() {
        System.out.println("\n=== CLEAR ALL PARKING SPOTS ===");
        System.out.println("⚠️  WARNING: This will remove all parked vehicles!");
        
        if (!askYesOrNo("Are you sure you want to clear all spots?")) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        if (!askYesOrNo("This action cannot be undone. Continue?")) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        var occupiedSpots = parkingLot.getSpots().stream()
            .filter(spot -> spot.isOccupied())
            .toList();
        
        int clearedCount = 0;
        for (var spot : occupiedSpots) {
            spot.vacate();
            clearedCount++;
        }
        
        // End all active sessions
        parkingLot.getSessions().stream()
            .filter(session -> !session.isEnded())
            .forEach(session -> session.completeSession());
        
        System.out.printf("✅ Cleared %d parking spots successfully!\n", clearedCount);
        System.out.println("All vehicles have been removed and sessions ended.");
    }
    
   
    private boolean simulateExitVehicle(String licensePlate) {
        var sessions = parkingLot.getSessions();
        
        var activeSession = sessions.stream()
            .filter(session -> session.getVehicle().getLicensePlate().equalsIgnoreCase(licensePlate) 
                           && !session.isEnded())
            .findFirst();
        
        if (activeSession.isEmpty()) {
            return false;
        }
        
        var session = activeSession.get();
        session.completeSession();
        
        // Free the spots
        session.getSpots().forEach(spot -> spot.vacate());
        
        return true;
    }
    
    /**
     * Εμφάνιση βοήθειας και οδηγιών
     */
    public void showHelp() {
        System.out.println("\n=== HELP & INSTRUCTIONS ===");
        System.out.println("[P]  Smart Parking Management System Guide\n");
        
        System.out.println("VEHICLE TYPES:");
        System.out.println("• Car: Takes 1 parking spot");
        System.out.println("• Motorcycle: Takes 1 parking spot");
        System.out.println("• Truck: Takes 2 consecutive parking spots\n");
        
        System.out.println("FUEL TYPES:");
        System.out.println("• Gas: Can park in regular spots (1-80)");
        System.out.println("• Electric: Can park in both regular and electric spots (81-100)\n");
        
        System.out.println("PRICING:");
        System.out.println("• Regular spots: €2.0 per hour per spot");
        System.out.println("• Electric spots: €1.5 per hour per spot\n");
        
        System.out.println("FEATURES:");
        System.out.println("• Automatic spot assignment based on vehicle type");
        System.out.println("• Duplicate vehicle detection");
        System.out.println("• Parking history tracking");
        System.out.println("• Real-time availability status");
        System.out.println("• Detailed reports and statistics\n");
        
        System.out.println("TIPS:");
        System.out.println("• License plates are case-insensitive");
        System.out.println("• Phone numbers should be 10-15 digits");
        System.out.println("• Maximum parking duration is 24 hours");
        System.out.println("• Electric vehicles get priority on electric spots\n");
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}
