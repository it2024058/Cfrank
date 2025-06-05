/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author frmel
 */
package com.mycompany.it2024058;

/**
 * Κλάση που αναπαριστά ένα αυτοκίνητο
 * Κληρονομεί από την κλάση Vehicle
 */
public class Car extends Vehicle {
    
    /**
     * Constructor για δημιουργία αυτοκινήτου
     * @param licensePlate Πινακίδα κυκλοφορίας
     * @param fuelType Τύπος καυσίμου
     * @param driver Οδηγός
     */
    public Car(String licensePlate, String fuelType, Driver driver) {
        super(licensePlate, 1, fuelType, "Car", driver);
    }
    
    /**
     * Εναλλακτικός constructor με size παράμετρο (για συμβατότητα)
     * @param licensePlate Πινακίδα κυκλοφορίας
     * @param size Μέγεθος (θα είναι πάντα 1)
     * @param fuelType Τύπος καυσίμου
     * @param driver Οδηγός
     */
    public Car(String licensePlate, int size, String fuelType, Driver driver) {
        super(licensePlate, 1, fuelType, "Car", driver); // Αυτοκίνητα πάντα καταλαμβάνουν 1 θέση
    }
    
    @Override
    public String getDescription() {
        String fuelDesc = isElectric() ? "ηλεκτρικό" : 
                         (fuelType.equals("diesel") ? "ντιζελοκίνητο" : "βενζινοκίνητο");
        return "Αυτοκίνητο " + licensePlate + " (" + fuelDesc + ")";
    }
    
    /**
     * Επιστρέφει τις ειδικές απαιτήσεις του αυτοκινήτου για στάθμευση
     * @return Περιγραφή απαιτήσεων στάθμευσης
     */
    public String getParkingRequirements() {
        if (isElectric()) {
            return "Μπορεί να σταθμεύσει σε κανονική ή ηλεκτρική θέση";
        } else {
            return "Μπορεί να σταθμεύσει μόνο σε κανονική θέση";
        }
    }
    
    @Override
    public String toString() {
        return getDescription() + " - Οδηγός: " + driver.getFullName();
    }
}
