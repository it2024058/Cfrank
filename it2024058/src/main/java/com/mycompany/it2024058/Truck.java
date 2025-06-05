/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.it2024058;

/**
 *
 * @author frmel
 */

public class Truck extends Vehicle {
    private double lengthInMeters;
    private String usage; // "food", "furniture", "delivery"
    
    /**
     * Constructor για δημιουργία φορτηγού
     * @param licensePlate Πινακίδα κυκλοφορίας
     * @param fuelType Τύπος καυσίμου
     * @param driver Οδηγός
     * @param lengthInMeters Μήκος σε μέτρα
     * @param usage Είδος χρήσης
     */
    public Truck(String licensePlate, String fuelType, Driver driver, 
                 double lengthInMeters, String usage) {
        super(licensePlate, 2, fuelType, "Truck", driver); // Φορτηγά πάντα καταλαμβάνουν 2 θέσεις
        
        if (lengthInMeters <= 0 || lengthInMeters > 20) {
            throw new IllegalArgumentException("Μη έγκυρο μήκος φορτηγού (0-20 μέτρα)");
        }
        if (usage == null || !isValidUsage(usage)) {
            throw new IllegalArgumentException("Μη έγκυρος τύπος χρήσης φορτηγού");
        }
        
        this.lengthInMeters = lengthInMeters;
        this.usage = usage.toLowerCase();
    }
    
    /**
     * Εναλλακτικός constructor με size παράμετρο (για συμβατότητα)
     * @param licensePlate Πινακίδα κυκλοφορίας
     * @param size Μέγεθος (θα είναι πάντα 2)
     * @param fuelType Τύπος καυσίμου
     * @param driver Οδηγός
     * @param lengthInMeters Μήκος σε μέτρα
     * @param usage Είδος χρήσης
     */
    public Truck(String licensePlate, int size, String fuelType, Driver driver,
                 double lengthInMeters, String usage) {
        this(licensePlate, fuelType, driver, lengthInMeters, usage);
    }
    
    /**
     * Έλεγχος εγκυρότητας τύπου χρήσης
     * @param usage Τύπος χρήσης
     * @return true αν είναι έγκυρος
     */
    private boolean isValidUsage(String usage) {
        return usage != null && 
               (usage.equalsIgnoreCase("food") || 
                usage.equalsIgnoreCase("furniture") || 
                usage.equalsIgnoreCase("delivery"));
    }
    
    @Override
    public String getDescription() {
        String fuelDesc = isElectric() ? "ηλεκτρικό" : 
                         (fuelType.equals("diesel") ? "ντιζελοκίνητο" : "βενζινοκίνητο");
        String usageDesc = getUsageDescription();
        return "Φορτηγό " + licensePlate + " (" + fuelDesc + ", " + 
               lengthInMeters + "μ, " + usageDesc + ")";
    }
    
    /**
     * Επιστρέφει περιγραφή του τύπου χρήσης στα ελληνικά
     * @return Περιγραφή χρήσης
     */
    public String getUsageDescription() {
        switch (usage.toLowerCase()) {
            case "food":
                return "μεταφορά τροφίμων";
            case "furniture":
                return "μεταφορά επίπλων";
            case "delivery":
                return "διανομή παραγγελιών";
            default:
                return usage;
        }
    }
    
    /**
     * Επιστρέφει τις ειδικές απαιτήσεις του φορτηγού για στάθμευση
     * @return Περιγραφή απαιτήσεων στάθμευσης
     */
    public String getParkingRequirements() {
        String requirements = "Χρειάζεται 2 συνεχόμενες θέσεις. ";
        if (isElectric()) {
            requirements += "Μπορεί να σταθμεύσει σε κανονικές ή ηλεκτρικές θέσεις.";
        } else {
            requirements += "Μπορεί να σταθμεύσει μόνο σε κανονικές θέσεις.";
        }
        return requirements;
    }
    
    /**
     * Ελέγχει αν το φορτηγό χρειάζεται ειδική άδεια
     * @return true αν χρειάζεται ειδική άδεια
     */
    public boolean requiresSpecialLicense() {
        return lengthInMeters > 7.5; // Φορτηγά άνω των 7.5μ χρειάζονται ειδική άδεια
    }
    
    // Getters and Setters
    public double getLengthInMeters() {
        return lengthInMeters;
    }
    
    public void setLengthInMeters(double lengthInMeters) {
        if (lengthInMeters <= 0 || lengthInMeters > 20) {
            throw new IllegalArgumentException("Μη έγκυρο μήκος φορτηγού (0-20 μέτρα)");
        }
        this.lengthInMeters = lengthInMeters;
    }
    
    public String getUsage() {
        return usage;
    }
    
    public void setUsage(String usage) {
        if (usage == null || !isValidUsage(usage)) {
            throw new IllegalArgumentException("Μη έγκυρος τύπος χρήσης φορτηγού");
        }
        this.usage = usage.toLowerCase();
    }
    
    @Override
    public String toString() {
        return getDescription() + " - Οδηγός: " + driver.getFullName();
    }
}

