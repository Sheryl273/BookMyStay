/**
 * Book My Stay App
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * This program demonstrates object-oriented domain modeling
 * using abstraction, inheritance, encapsulation and polymorphism.
 * Different room types are created and their availability
 * is displayed using simple variables.
 *
 * @author Sheryl
 * @version 2.1
 */

/* ---------- Abstract Room Class ---------- */
abstract class Room {

    private String roomType;
    private int beds;
    private int size;
    private double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price per night: ₹" + price);
    }
}

/* ---------- Single Room Class ---------- */
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 2000);
    }
}

/* ---------- Double Room Class ---------- */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 3500);
    }
}

/* ---------- Suite Room Class ---------- */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 500, 6000);
    }
}

/* ---------- Application Entry Class ---------- */
public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay");
        System.out.println("Hotel Booking System v2.1");
        System.out.println("----------------------------");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        // Display room details
        System.out.println("\n--- Single Room ---");
        single.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailable);

        System.out.println("\n--- Double Room ---");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailable);

        System.out.println("\n--- Suite Room ---");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailable);

        System.out.println("\nApplication finished.");
    }
}