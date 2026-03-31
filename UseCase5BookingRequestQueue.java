import java.util.*;

// Main Class
public class UseCase5BookingRequestQueue {

    // -------- Reservation (Represents Booking Intent) --------
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

        public void displayRequest() {
            System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
        }
    }

    // -------- Booking Request Queue --------
    static class BookingQueue {
        private Queue<Reservation> queue;

        public BookingQueue() {
            queue = new LinkedList<>();
        }

        // Add booking request (enqueue)
        public void addRequest(Reservation reservation) {
            queue.offer(reservation);
            System.out.println("Request added for " + reservation.getGuestName());
        }

        // View all queued requests (read-only)
        public void viewRequests() {
            System.out.println("\n===== Booking Request Queue =====");

            if (queue.isEmpty()) {
                System.out.println("No pending booking requests.");
                return;
            }

            for (Reservation r : queue) {
                r.displayRequest();
            }
        }

        // Peek next request (without removing)
        public Reservation peekNextRequest() {
            return queue.peek();
        }

        // Get queue size
        public int getQueueSize() {
            return queue.size();
        }
    }

    // -------- Main Method --------
    public static void main(String[] args) {

        // Step 1: Initialize Booking Queue
        BookingQueue bookingQueue = new BookingQueue();

        // Step 2: Simulate Guest Requests (Arrival Order Matters)
        Reservation r1 = new Reservation("Alice", "Deluxe");
        Reservation r2 = new Reservation("Bob", "Suite");
        Reservation r3 = new Reservation("Charlie", "Standard");

        // Step 3: Add requests to queue (FIFO)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Step 4: View all requests (No removal, read-only)
        bookingQueue.viewRequests();

        // Step 5: Show next request to be processed
        System.out.println("\nNext request to process:");
        Reservation next = bookingQueue.peekNextRequest();

        if (next != null) {
            next.displayRequest();
        }

        // Step 6: Show queue size
        System.out.println("\nTotal Requests in Queue: " + bookingQueue.getQueueSize());
    }
}
