package com.mycompany.it2024058;

import java.util.Scanner;


public class it2024058 {
    
    private static final String VERSION = "2.0";
    private static final String DEVELOPER = "IT2024058";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Display welcome banner
            displayWelcomeBanner();
            
            // Initialize parking manager
            ParkingManager manager = new ParkingManager(scanner);
            
            // Check for command line arguments
            if (args.length > 0) {
                handleCommandLineArgs(args, manager);
            } else {
                // Start interactive mode
                manager.startApplication();
            }
            
        } catch (Exception e) {
            System.err.println("Critical error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\nShutting down system...");
            scanner.close();
        }
    }
    
    
    private static void displayWelcomeBanner() {
        System.out.println("+" + "-".repeat(52) + "+");
        System.out.println("|" + " ".repeat(52) + "|");
        System.out.println("|" + centerText("🅿️  SMART PARKING MANAGEMENT SYSTEM", 52) + "|");
        System.out.println("|" + centerText("Version " + VERSION + " - Developed by " + DEVELOPER, 52) + "|");
        System.out.println("|" + " ".repeat(52) + "|");
        System.out.println("|" + centerText("Advanced Parking Solution with Real-time", 52) + "|");
        System.out.println("|" + centerText("Tracking and Intelligent Spot Assignment", 52) + "|");
        System.out.println("|" + " ".repeat(52) + "|");
        System.out.println("+" + "-".repeat(52) + "+");
        System.out.println();
    }
    
    private static void handleCommandLineArgs(String[] args, ParkingManager manager) {
        String command = args[0].toLowerCase();
        
        switch (command) {
            case "--help", "-h":
                displayCommandLineHelp();
                break;
                
            case "--version", "-v":
                System.out.println("Smart Parking Management System v" + VERSION);
                System.out.println("Developed by: " + DEVELOPER);
                break;
                
            case "--demo":
                runDemoMode(manager);
                break;
                
            case "--batch":
                if (args.length > 1) {
                    processBatchFile(args[1], manager);
                } else {
                    System.out.println("Error: Batch file path required");
                    System.out.println("Usage: java ParkingApplication --batch <file_path>");
                }
                break;
                
            case "--interactive", "-i":
            default:
                manager.startApplication();
                break;
        }
    }
    
    /**
     * Εμφάνιση command line help
     */
    private static void displayCommandLineHelp() {
        System.out.println("Smart Parking Management System v" + VERSION);
        System.out.println("Usage: java ParkingApplication [OPTION]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  -h, --help         Show this help message");
        System.out.println("  -v, --version      Show version information");
        System.out.println("  -i, --interactive  Start interactive mode (default)");
        System.out.println("  --demo             Run demonstration mode");
        System.out.println("  --batch <file>     Process batch operations from file");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java ParkingApplication");
        System.out.println("  java ParkingApplication --demo");
        System.out.println("  java ParkingApplication --batch operations.txt");
        System.out.println();
    }
    
    /**
     * Demo mode για επίδειξη του συστήματος
     */
    private static void runDemoMode(ParkingManager manager) {
        System.out.println("🎯 DEMO MODE - Simulating parking operations...\n");
        
        // Simulate some demo data
        simulateDemoData();
        
        System.out.println("✅ Demo data loaded successfully!");
        System.out.println("The system now has sample vehicles and parking sessions.");
        System.out.println("You can explore the features using the interactive menu.\n");
        
        // Start interactive mode with demo data
        manager.startApplication();
    }
    
    /**
     * Προσομοίωση demo δεδομένων
     */
    private static void simulateDemoData() {
        System.out.println("Loading demo vehicles and sessions...");
        
        // Create sample drivers
        Driver[] demoDrivers = {
            new Driver("John", "Doe", "6944123456"),
            new Driver("Maria", "Smith", "6955234567"),
            new Driver("George", "Johnson", "6966345678"),
            new Driver("Anna", "Williams", "6977456789"),
            new Driver("Nick", "Brown", "6988567890")
        };
        
        // Create sample vehicles
        Vehicle[] demoVehicles = {
            new Car("ABC-1234", "gas", demoDrivers[0]),
            new Car("XYZ-5678", "electric", demoDrivers[1]),
            new Motorcycle("MOT-9999", "gas", demoDrivers[2]),
            new Truck("TRK-1111", "gas", demoDrivers[3], 8.5, "delivery"),
            new Truck("ELC-2222", "electric", demoDrivers[4], 7.2, "food")
        };
        
        // Register vehicles
        for (Vehicle vehicle : demoVehicles) {
            VehicleRegistry.addVehicle(vehicle);
            UserRegistry.addRecord(vehicle.getDriver().getFullName(), vehicle.getLicensePlate());
        }
        
        System.out.println("✓ Created " + demoVehicles.length + " demo vehicles");
        System.out.println("✓ Registered " + demoDrivers.length + " demo drivers");
    }
    
    /**
     * Επεξεργασία batch file με operations
     */
    private static void processBatchFile(String filePath, ParkingManager manager) {
        System.out.println("Processing batch file: " + filePath);
        
        try {
            // This would read operations from a file and execute them
            // For now, show what would be implemented
            System.out.println("Batch processing would include:");
            System.out.println("- PARK <license> <type> <fuel> <driver_info>");
            System.out.println("- EXIT <license>");
            System.out.println("- REPORT <filename>");
            System.out.println("- STATUS");
            System.out.println();
            System.out.println("Feature coming in future version!");
            
        } catch (Exception e) {
            System.err.println("Error processing batch file: " + e.getMessage());
        }
    }
    
    /**
     * Βοηθητική μέθοδος για κεντράρισμα κειμένου
     */
    private static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        
        int padding = (width - text.length()) / 2;
        StringBuilder sb = new StringBuilder();
        
        // Left padding
        sb.append(" ".repeat(padding));
        sb.append(text);
        
        // Right padding to fill remaining space
        while (sb.length() < width) {
            sb.append(" ");
        }
        
        return sb.toString();
    }
}

/**
 * Βοηθητική κλάση για system utilities
 */
class SystemUtils {
    
    /**
     * Έλεγχος αν το σύστημα τρέχει σε Windows
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
    
    /**
     * Καθαρισμός console (όπου υποστηρίζεται)
     */
    public static void clearConsole() {
        try {
            if (isWindows()) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // If clearing fails, just print some newlines
            System.out.println("\n".repeat(50));
        }
    }
    
    /**
     * Εμφάνιση system information
     */
    public static void displaySystemInfo() {
        System.out.println("=== SYSTEM INFORMATION ===");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Operating System: " + System.getProperty("os.name"));
        System.out.println("Architecture: " + System.getProperty("os.arch"));
        System.out.println("Available Processors: " + Runtime.getRuntime().availableProcessors());
        
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / (1024 * 1024);
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        
        System.out.println("Max Memory: " + maxMemory + " MB");
        System.out.println("Total Memory: " + totalMemory + " MB");
        System.out.println("Free Memory: " + freeMemory + " MB");
        System.out.println("Used Memory: " + (totalMemory - freeMemory) + " MB");
    }
    
    /**
     * Validation utilities
     */
//    public static boolean isValidEmail(String email) {
//        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
//    }
    
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }
    
    public static boolean isValidLicensePlate(String plate) {
        return plate != null && !plate.trim().isEmpty() && plate.length() >= 3;
    }
}