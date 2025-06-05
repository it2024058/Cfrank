/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author frmel
 */
package com.mycompany.it2024058;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ParkingSession {
    private Vehicle vehicle;
    private Driver driver;
    private List<ParkingSpot> parkingSpots;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isCompleted;
    private double chargeAmount;

    public ParkingSession(Vehicle vehicle, Driver driver, List<ParkingSpot> assignedSpots, LocalDateTime startTime, int duration) {
        this.vehicle = vehicle;
        this.driver = driver;
        this.parkingSpots = assignedSpots;  
        this.startTime = startTime;         
        this.isCompleted = false;
        this.chargeAmount = 0.0;
}

    /**
     * Calculates total duration in hours (rounded up).
     * @return 
     */
    public int getDurationHours() {
        LocalDateTime end = isCompleted ? endTime : LocalDateTime.now();
        long minutes = Duration.between(startTime, end).toMinutes();
        return (int) Math.ceil(minutes / 60.0);
    }

    /**
     * Tiered charge based on actual duration in hours.
     * @return 
     */
    public double calculateCharge() {
        int hours = getDurationHours();
        if (hours <= 3) return 5.0;
        if (hours <= 8) return 8.0;
        if (hours < 24) return 12.0;
        return 15.0;
    }

    /**
     * Ends the session, frees up spots, records end time and charge.
     */
    public void completeSession() {
        this.endTime = LocalDateTime.now();
        this.chargeAmount = calculateCharge();
        this.isCompleted = true;

        for (ParkingSpot spot : parkingSpots) {
            spot.vacate();
        }
    }

    public boolean isEnded() {
        return isCompleted;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

    public List<ParkingSpot> getSpots() {
        return parkingSpots;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }

    @Override
    public String toString() {
        return String.format(
            "Plate: %s | Phone: %s | Start: %s | Duration: %dh | Charge: €%.2f | Status: %s",
            vehicle.getLicensePlate(),
            driver.getPhone(),
            startTime,
            getDurationHours(),
            isCompleted ? chargeAmount : calculateCharge(),
            isCompleted ? "Completed" : "Active"
        );
    }
}

