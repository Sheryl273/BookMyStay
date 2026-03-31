import java.util.*;

public class UseCase6RoomAllocationService {

    // -------- Reservation (Booking Request) --------
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // -------- Inventory Service --------
    static class Inventory {
        private Map<String, Integer> availabilityMap = new HashMap<>();

        public void addRoom(String roomType, int count) {
            availabilityMap.put(roomType, count);
        }

        public int getAvailability(String roomType) {
            return availabilityMap.getOrDefault(roomType, 0);
        }

        public void decrementRoom(String roomType) {
            availabilityMap.put(roomType, getAvailability(roomType) - 1);
        }
    }

    // -------- Booking Queue (FIFO) --------
    static class BookingQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public void addRequest(Reservation r) {
            queue.offer(r);
        }

        public Reservation getNextRequest() {
            return queue.poll(); // removes in FIFO order
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    // -------- Booking Service (Core Logic) --------
    static class BookingService {

        private Inventory inventory;

        // Track allocated room IDs globally
        private Set<String> allocatedRoomIds = new HashSet<>();

        // Map roomType -> allocated room IDs
        private Map<String, Set<String>> roomAllocations = new HashMap<>();

        public BookingService(Inventory inventory) {
            this.inventory = inventory;
        }

        public void processBookings(BookingQueue queue) {

            System.out.println("\n===== Processing Bookings =====\n");

            while (!queue.isEmpty()) {

                Reservation request = queue.getNextRequest();
                String roomType = request.getRoomType();

                System.out.println("Processing request for " + request.getGuestName());

                // Step 1: Check availability
                if (inventory.getAvailability(roomType) > 0) {

                    // Step 2: Generate unique room ID
                    String roomId = generateRoomId(roomType);

                    // Step 3: Ensure uniqueness (Set prevents duplicates)
                    if (!allocatedRoomIds.contains(roomId)) {

                        // Atomic operation begins
                        allocatedRoomIds.add(roomId);

                        roomAllocations
                            .computeIfAbsent(roomType, k -> new HashSet<>())
                            .add(roomId);

                        // Step 4: Update inventory immediately
                        inventory.decrementRoom(roomType);

                        // Step 5: Confirm booking
                        System.out.println("Booking Confirmed!");
                        System.out.println("Guest: " + request.getGuestName());
                        System.out.println("Room Type: " + roomType);
                        System.out.println("Allocated Room ID: " + roomId);
                        System.out.println("-----------------------------");

                    } else {
                        // Extremely rare due to UUID-like generation logic
                        System.out.println("Error: Duplicate Room ID detected!");
                    }

                } else {
                    System.out.println("Booking Failed for " + request.getGuestName()
                            + " (No availability)");
                    System.out.println("-----------------------------");
                }
            }
        }

        // Unique Room ID Generator
        private String generateRoomId(String roomType) {
            return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
        }
    }

    // -------- Main Method --------
    public static void main(String[] args) {

        // Step 1: Initialize Inventory
        Inventory inventory = new Inventory();
        inventory.addRoom("Deluxe", 2);
        inventory.addRoom("Suite", 1);
        inventory.addRoom("Standard", 1);

        // Step 2: Create Booking Queue
        BookingQueue queue = new BookingQueue();

        // Step 3: Add Requests (FIFO order)
        queue.addRequest(new Reservation("Alice", "Deluxe"));
        queue.addRequest(new Reservation("Bob", "Suite"));
        queue.addRequest(new Reservation("Charlie", "Deluxe"));
        queue.addRequest(new Reservation("David", "Deluxe")); // should fail
        queue.addRequest(new Reservation("Eve", "Standard"));

        // Step 4: Process Bookings
        BookingService bookingService = new BookingService(inventory);
        bookingService.processBookings(queue);
    }
}