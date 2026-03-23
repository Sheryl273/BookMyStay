/**
 * Book My Stay App
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * This program demonstrates how a centralized inventory system
 * can be implemented using a HashMap to manage room availability.
 *
 * @author Sheryl
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

/* ---------- RoomInventory Class ---------- */

class RoomInventory {

    // HashMap to store room type and available count
    private HashMap<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Register room types and availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Method to get availability of a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Method to display full inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        System.out.println("------------------------");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " available");
        }
    }
}

/* ---------- Application Entry Class ---------- */

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay");
        System.out.println("Hotel Booking System v3.1");
        System.out.println("----------------------------");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Example availability lookup
        System.out.println("\nChecking availability for Double Room:");
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        // Update availability example
        System.out.println("\nUpdating availability for Double Room...");
        inventory.updateAvailability("Double Room", 4);

        // Display updated inventory
        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();

        System.out.println("\nApplication finished.");
    }
}
