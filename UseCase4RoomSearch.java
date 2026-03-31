import java.util.*;

public class UseCase4RoomSearch {

    // -------- Room Domain Model --------
    static class Room {
        private String roomType;
        private double price;
        private List<String> amenities;

        public Room(String roomType, double price, List<String> amenities) {
            this.roomType = roomType;
            this.price = price;
            this.amenities = amenities;
        }

        public String getRoomType() {
            return roomType;
        }

        public double getPrice() {
            return price;
        }

        public List<String> getAmenities() {
            return amenities;
        }

        public void displayDetails() {
            System.out.println("Room Type: " + roomType);
            System.out.println("Price: ₹" + price);
            System.out.println("Amenities: " + amenities);
        }
    }

    // -------- Inventory (State Holder) --------
    static class Inventory {
        private Map<String, Integer> availabilityMap = new HashMap<>();

        public void addRoom(String roomType, int count) {
            availabilityMap.put(roomType, count);
        }

        // Read-only access
        public int getAvailability(String roomType) {
            return availabilityMap.getOrDefault(roomType, 0);
        }

        public Set<String> getAllRoomTypes() {
            return availabilityMap.keySet();
        }
    }

    // -------- Search Service (Read-Only Logic) --------
    static class SearchService {
        private Inventory inventory;
        private Map<String, Room> roomMap;

        public SearchService(Inventory inventory, Map<String, Room> roomMap) {
            this.inventory = inventory;
            this.roomMap = roomMap;
        }

        public void searchAvailableRooms() {
            System.out.println("\n===== Available Rooms =====\n");

            boolean found = false;

            for (String roomType : inventory.getAllRoomTypes()) {

                int available = inventory.getAvailability(roomType);

                // Validation: only show available rooms
                if (available > 0) {
                    Room room = roomMap.get(roomType);

                    // Defensive Programming
                    if (room != null) {
                        room.displayDetails();
                        System.out.println("Available Count: " + available);
                        System.out.println("---------------------------");
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("No rooms available.");
            }
        }
    }

    // -------- Main Method --------
    public static void main(String[] args) {

        // Create Room Objects
        Room deluxe = new Room("Deluxe", 3000,
                Arrays.asList("WiFi", "AC", "TV"));

        Room suite = new Room("Suite", 5000,
                Arrays.asList("WiFi", "AC", "TV", "Mini Bar"));

        Room standard = new Room("Standard", 2000,
                Arrays.asList("WiFi", "Fan"));

        // Store Room Data
        Map<String, Room> roomMap = new HashMap<>();
        roomMap.put("Deluxe", deluxe);
        roomMap.put("Suite", suite);
        roomMap.put("Standard", standard);

        // Initialize Inventory
        Inventory inventory = new Inventory();
        inventory.addRoom("Deluxe", 3);
        inventory.addRoom("Suite", 0);   // filtered out
        inventory.addRoom("Standard", 5);

        // Search Service
        SearchService searchService = new SearchService(inventory, roomMap);

        // Guest performs search
        searchService.searchAvailableRooms();
    }
}
