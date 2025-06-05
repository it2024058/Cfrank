/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author frmel
 */
package com.mycompany.it2024058;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class VehicleRegistry {
    
    private static final Map<String, Vehicle> vehicles = new HashMap<>();
    
    /**
     * Adds a vehicle to the registry
     * @param vehicle The vehicle to add
     */
    public static void addVehicle(Vehicle vehicle) {
        if (vehicle != null && vehicle.getLicensePlate() != null) {
            String plate = vehicle.getLicensePlate().toUpperCase().trim();
            if (isValidLicensePlate(plate)) {
                vehicles.put(plate, vehicle);
            }
        }
    }
    
    /**
     * Retrieves a vehicle by its license plate
     * @param licensePlate The license plate to search for
     * @return The vehicle if found, null otherwise
     */
    public static Vehicle getVehicleByPlate(String licensePlate) {
        if (licensePlate == null || !isValidLicensePlate(licensePlate)) {
            return null;
        }
        return vehicles.get(licensePlate.toUpperCase().trim());
    }
    
    /**
     * Checks if a vehicle with the given license plate exists
     * @param licensePlate The license plate to check
     * @return true if vehicle exists, false otherwise
     */
    public static boolean vehicleExists(String licensePlate) {
        if (licensePlate == null) {
            return false;
        }
        return vehicles.containsKey(licensePlate.toUpperCase());
    }
    
    /**
     * Removes a vehicle from the registry
     * @param licensePlate The license plate of the vehicle to remove
     * @return The removed vehicle, or null if not found
     */
    public static Vehicle removeVehicle(String licensePlate) {
        if (licensePlate == null) {
            return null;
        }
        return vehicles.remove(licensePlate.toUpperCase());
    }
    
    /**
     * Gets all registered vehicles
     * @return Collection of all vehicles
     */
    public static Collection<Vehicle> getAllVehicles() {
        return vehicles.values();
    }
    
    /**
     * Gets the total number of registered vehicles
     * @return Number of vehicles in registry
     */
    public static int getVehicleCount() {
        return vehicles.size();
    }
    
    /**
     * Clears all vehicles from the registry
     */
    public static void clearRegistry() {
        vehicles.clear();
    }
    
    /**
     * Validates license plate format (3 letters + 4 digits)
     * @param licensePlate The license plate to validate
     * @return true if valid format
     */
    private static boolean isValidLicensePlate(String licensePlate) {
        return licensePlate != null && 
               licensePlate.trim().matches("[A-Z]{3}\\d{4}");
    }
}