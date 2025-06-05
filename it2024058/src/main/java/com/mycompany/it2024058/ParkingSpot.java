/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.it2024058;

/**
 * Κλάση που αναπαριστά μια θέση στάθμευσης.
 * Διαχειρίζεται τη διαθεσιμότητα και τον τύπο της θέσης.
 */
public class ParkingSpot {
    private final int spotId;
    private final boolean isForElectric;
    private Vehicle parkedVehicle; // null αν είναι κενή
    private boolean isOccupiedBySession;

    /**
     * Δημιουργεί μία θέση στάθμευσης.
     * @param spotId Αριθμός θέσης (πρέπει να είναι θετικός)
     * @param isForElectric true αν προορίζεται για ηλεκτρικά οχήματα
     */
    public ParkingSpot(int spotId, boolean isForElectric) {
        if (spotId <= 0) {
            throw new IllegalArgumentException("Ο αριθμός θέσης πρέπει να είναι θετικός.");
        }
        this.spotId = spotId;
        this.isForElectric = isForElectric;
        this.parkedVehicle = null;
        this.isOccupiedBySession = false;
    }

    /**
     * Ελέγχει αν η θέση μπορεί να χρησιμοποιηθεί από το δοσμένο όχημα.
     * @param vehicle Το όχημα
     * @return true αν είναι διαθέσιμη και συμβατή
     */
    public boolean canBeUsedBy(Vehicle vehicle) {
        if (vehicle == null) return false;
        if (isOccupied()) return false;
        return !isForElectric || vehicle.isElectric(); // Αν είναι ηλεκτρική, πρέπει να είναι και το όχημα
    }

    /**
     * Καταλαμβάνει τη θέση με το δοσμένο όχημα.
     * @param vehicle Το όχημα που θα σταθμεύσει
     */
    public void occupy(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Το όχημα δεν μπορεί να είναι null.");
        }
        if (!canBeUsedBy(vehicle)) {
            throw new IllegalArgumentException("Το όχημα δεν μπορεί να χρησιμοποιήσει αυτή τη θέση.");
        }
        this.parkedVehicle = vehicle;
        this.isOccupiedBySession = true;
    }

    /**
     * Απελευθερώνει τη θέση από τη στάθμευση.
     */
    public void vacate() {
        if (!isOccupied()) {
            throw new IllegalStateException("Η θέση " + spotId + " είναι ήδη ελεύθερη.");
        }
        this.parkedVehicle = null;
        this.isOccupiedBySession = false;
    }

    /**
     * Ελέγχει αν η θέση είναι κατειλημμένη.
     * @return true αν υπάρχει όχημα στη θέση
     */
    public boolean isOccupied() {
        return parkedVehicle != null;
    }

    /**
     * Επιστρέφει το όχημα που είναι σταθμευμένο.
     * @return Το όχημα ή null
     */
    public Vehicle getVehicle() {
        return parkedVehicle;
    }

    /**
     * Επιστρέφει true αν η θέση χρησιμοποιείται σε συνεδρία στάθμευσης.
     * @return true αν είναι μέρος ενεργής συνεδρίας
     */
    public boolean isOccupiedBySession() {
        return isOccupiedBySession;
    }

    /**
     * Άμεσο σετάρισμα για testing ή state restore.
     * @param vehicle Το όχημα
     */
    public void setVehicle(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
        this.isOccupiedBySession = vehicle != null;
    }

    /**
     * Επιστρέφει τον αριθμό της θέσης.
     * @return Spot ID
     */
    public int getSpotId() {
        return spotId;
    }

    /**
     * Επιστρέφει αν είναι για ηλεκτρικά οχήματα.
     * @return true αν η θέση είναι για ηλεκτρικά
     */
    public boolean isForElectric() {
        return isForElectric;
    }

    /**
     * Περιγραφή της θέσης (για εμφάνιση).
     */
    @Override
    public String toString() {
        return "Θέση #" + spotId +
               " (" + (isForElectric ? "Ηλεκτρική" : "Κανονική") + ")" +
               (isOccupied() ? " - Κατειλημμένη από: " + parkedVehicle.getLicensePlate() : " - Διαθέσιμη");
    }
}


