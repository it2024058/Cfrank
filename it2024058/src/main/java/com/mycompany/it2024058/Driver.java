/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.it2024058;

/**
 * Κλάση που αναπαριστά έναν οδηγό
 * Περιέχει τα προσωπικά στοιχεία του οδηγού
 */
public class Driver {
    private String firstName;
    private String lastName;
    private String mobile;
    
    /**
     * Constructor για δημιουργία οδηγού
     * @param firstName Όνομα οδηγού
     * @param lastName Επώνυμο οδηγού
     * @param mobile Κινητό τηλέφωνο οδηγού
     */
    public Driver(String firstName, String lastName, String mobile) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("Το όνομα δεν μπορεί να είναι κενό");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Το επώνυμο δεν μπορεί να είναι κενό");
        }
        if (mobile == null || !isValidPhoneNumber(mobile)) {
            throw new IllegalArgumentException("Μη έγκυρος αριθμός τηλεφώνου");
        }
        
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.mobile = mobile.trim();
    }
    
    /**
     * Έλεγχος εγκυρότητας αριθμού τηλεφώνου
     * @param phone Αριθμός τηλεφώνου
     * @return true αν είναι έγκυρος
     */
    private boolean isValidPhoneNumber(String phone) {
        // Έλεγχος για ελληνικό κινητό τηλέφωνο (10 ψηφία που αρχίζει με 69)
        return phone != null && phone.matches("69\\d{8}");
    }
    
    /**
     * Επιστρέφει το πλήρες όνομα του οδηγού
     * @return Πλήρες όνομα
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("Το όνομα δεν μπορεί να είναι κενό");
        }
        this.firstName = firstName.trim();
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Το επώνυμο δεν μπορεί να είναι κενό");
        }
        this.lastName = lastName.trim();
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public String getPhone() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        if (mobile == null || !isValidPhoneNumber(mobile)) {
            throw new IllegalArgumentException("Μη έγκυρος αριθμός τηλεφώνου");
        }
        this.mobile = mobile.trim();
    }
    
    @Override
    public String toString() {
        return getFullName() + " (" + mobile + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Driver driver = (Driver) obj;
        return mobile.equals(driver.mobile);
    }
    
    @Override
    public int hashCode() {
        return mobile.hashCode();
    }
}