/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.it2024058;

/**
 *
 * @author frmel
 */

public class Motorcycle extends Vehicle {
    
    /**
     * Constructor για δημιουργία μηχανής
     * @param licensePlate Πινακίδα κυκλοφορίας
     * @param fuelType Τύπος καυσίμου
     * @param driver Οδηγός
     */
    public Motorcycle(String licensePlate, String fuelType, Driver driver) {
        super(licensePlate, 1, fuelType, "Motorcycle", driver);
    }
    
    /**
     * Εναλλακτικός constructor με size παράμετρο (για συμβατότητα)
     * @param licensePlate Πινακίδα κυκλοφορίας
     * @param size Μέγεθος (θα είναι πάντα 1)
     * @param fuelType Τύπος καυσίμου
     * @param driver Οδηγός
     */
    public Motorcycle(String licensePlate, int size, String fuelType, Driver driver) {
        super(licensePlate, 1, fuelType, "Motorcycle", driver); // Μηχανές πάντα καταλαμβάνουν 1 θέση
    }
    
    @Override
    public String getDescription() {
        String fuelDesc = isElectric() ? "ηλεκτρική" : 
                         (fuelType.equals("diesel") ? "ντιζελοκίνητη" : "βενζινοκίνητη");
        return "Μηχανή " + licensePlate + " (" + fuelDesc + ")";
    }
    
    /**
     * Επιστρέφει τις ειδικές απαιτήσεις της μηχανής για στάθμευση
     * @return Περιγραφή απαιτήσεων στάθμευσης
     */
    public String getParkingRequirements() {
        if (isElectric()) {
            return "Μπορεί να σταθμεύσει σε κανονική ή ηλεκτρική θέση";
        } else {
            return "Μπορεί να σταθμεύσει μόνο σε κανονική θέση";
        }
    }
    
    /**
     * Ειδική μέθοδος για μηχανές - έλεγχος ασφάλειας κράνους
     * @return Μήνυμα υπενθύμισης για κράνος
     */
    public String getHelmetReminder() {
        return "Υπενθύμιση: Μην ξεχάσετε το κράνος σας!";
    }
    
    @Override
    public String toString() {
        return getDescription() + " - Οδηγός: " + driver.getFullName();
    }
}
