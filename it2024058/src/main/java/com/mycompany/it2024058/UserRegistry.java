/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.it2024058;

import java.util.ArrayList;
import java.util.List;

public class UserRegistry {
    public static List<String> driverRecords = new ArrayList<>();

    public static void addRecord(String name, String licensePlate) {
        String record = name + " - " + licensePlate;
        driverRecords.add(record);
    }

    public static void displayAllRecords() {
        System.out.println("Registered Users:");
        for (String record : driverRecords) {
            System.out.println(record);
        }
    }

    public static List<String> getDriverRecords() {
        return driverRecords;
    }

    public static void setDriverRecords(List<String> driverRecords) {
        UserRegistry.driverRecords = driverRecords;
    }    
    
}

