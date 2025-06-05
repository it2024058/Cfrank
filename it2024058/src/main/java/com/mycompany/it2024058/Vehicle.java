/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.it2024058;

/**
 * Αφηρημένη κλάση που αναπαριστά ένα όχημα
 * Βασική κλάση για όλα τα τύπα οχημάτων
 */
public abstract class Vehicle {
    protected String licensePlate;
    protected int size; // 1 για αυτοκίνητα/μηχανές, 2 για φορτηγά
    protected String fuelType; // "gas", "diesel", "electric"
    protected String vehicleType;
    protected Driver driver;
    
    /**
     * Constructor για δημιουργία οχήματος
     * @param licensePlate Πινακίδα κυκλοφορίας
     * @param size Μέγεθος οχήματος (θέσεις που καταλαμβάνει)
     * @param fuelType Τύπος καυσίμου
     * @param vehicleType Τύπος οχήματος
     * @param driver Οδηγός
     */
    public Vehicle(String licensePlate, int size, String fuelType, String vehicleType, Driver driver) {
        if (licensePlate == null || !isValidLicensePlate(licensePlate)) {
            throw new IllegalArgumentException("NON VIABLE LICENSE PLATE");
        }
        if (driver == null) {
            throw new IllegalArgumentException("DRIVER CANT BE null");
        }
        if (fuelType == null || !isValidFuelType(fuelType)) {
            throw new IllegalArgumentException("NON VIABLE GAS TYPE");
        }
        
        this.licensePlate = licensePlate.toUpperCase().trim();
        this.size = size;
        this.fuelType = fuelType.toLowerCase();
        this.vehicleType = vehicleType;
        this.driver = driver;
    }
    
    /**
     * Έλεγχος εγκυρότητας πινακίδας
     * @param plate Πινακίδα
     * @return true αν είναι έγκυρη
     */
    private boolean isValidLicensePlate(String plate) {
        // Έλεγχος για ελληνική πινακίδα (3 γράμματα + 4 αριθμοί)
        return plate != null && plate.trim().matches("[A-Z]{3}\\d{4}");
    }
    
    /**
     * Έλεγχος εγκυρότητας τύπου καυσίμου
     * @param fuel Τύπος καυσίμου
     * @return true αν είναι έγκυρος
     */
    private boolean isValidFuelType(String fuel) {
        return fuel != null && 
               (fuel.equalsIgnoreCase("gas") || 
                fuel.equalsIgnoreCase("diesel") || 
                fuel.equalsIgnoreCase("electric"));
    }
    
    /**
     * Ελέγχει αν το όχημα είναι ηλεκτρικό
     * @return true αν είναι ηλεκτρικό
     */
    public boolean isElectric() {
        return "electric".equalsIgnoreCase(fuelType);
    }
    
    /**
     * Αφηρημένη μέθοδος για επιστροφή περιγραφής του οχήματος
     * @return Περιγραφή οχήματος
     */
    public abstract String getDescription();
    
    // Getters and Setters
    public String getLicensePlate() {
        return licensePlate;
    }
    
    public void setLicensePlate(String licensePlate) {
        if (licensePlate == null || !isValidLicensePlate(licensePlate)) {
            throw new IllegalArgumentException("NON VIABLE LICENSE PLATE");
        }
        this.licensePlate = licensePlate.toUpperCase().trim();
    }
    
    public int getSize() {
        return size;
    }
    
    public String getFuelType() {
        return fuelType;
    }
    
    public void setFuelType(String fuelType) {
        if (fuelType == null || !isValidFuelType(fuelType)) {
            throw new IllegalArgumentException("Μη έγκυρος τύπος καυσίμου");
        }
        this.fuelType = fuelType.toLowerCase();
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public Driver getDriver() {
        return driver;
    }
    
    public void setDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Ο οδηγός δεν μπορεί να είναι null");
        }
        this.driver = driver;
    }
    
    @Override
    public String toString() {
        return vehicleType + " " + licensePlate + " (" + fuelType + ") - " + driver.getFullName();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehicle vehicle = (Vehicle) obj;
        return licensePlate.equals(vehicle.licensePlate);
    }
    
    @Override
    public int hashCode() {
        return licensePlate.hashCode();
    }
}
