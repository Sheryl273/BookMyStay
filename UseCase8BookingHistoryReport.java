import java.util.*;

public class UseCase8BookingHistoryReport {

    // -------- Reservation (Confirmed Booking) --------
    static class Reservation {
        private String reservationId;
        private String guestName;
        private String roomType;

        public Reservation(String reservationId, String guestName, String roomType) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }

        public void display() {
            System.out.println("Reservation ID: " + reservationId +
                    " | Guest: " + guestName +
                    " | Room: " + roomType);
        }
    }

    // -------- Booking History (State Holder) --------
    static class BookingHistory {
        private List<Reservation> history = new ArrayList<>();

        // Add confirmed reservation
        public void addReservation(Reservation reservation) {
            history.add(reservation);
            System.out.println("Reservation stored: " + reservation.getReservationId());
        }

        // Retrieve all bookings (read-only style)
        public List<Reservation> getAllReservations() {
            return history;
        }
    }

    // -------- Booking Report Service --------
    static class BookingReportService {

        private BookingHistory bookingHistory;

        public BookingReportService(BookingHistory bookingHistory) {
            this.bookingHistory = bookingHistory;
        }

        // Display all bookings
        public void showAllBookings() {
            System.out.println("\n===== Booking History =====");

            List<Reservation> list = bookingHistory.getAllReservations();

            if (list.isEmpty()) {
                System.out.println("No bookings found.");
                return;
            }

            for (Reservation r : list) {
                r.display();
            }
        }

        // Generate summary report
        public void generateSummaryReport() {
            System.out.println("\n===== Booking Summary Report =====");

            List<Reservation> list = bookingHistory.getAllReservations();

            Map<String, Integer> roomCount = new HashMap<>();

            for (Reservation r : list) {
                roomCount.put(
                    r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1
                );
            }

            System.out.println("Total Bookings: " + list.size());

            for (String roomType : roomCount.keySet()) {
                System.out.println(roomType + " Bookings: " + roomCount.get(roomType));
            }
        }
    }

    // -------- Main Method --------
    public static void main(String[] args) {

        // Step 1: Booking History
        BookingHistory history = new BookingHistory();

        // Step 2: Simulate confirmed bookings (from Use Case 6)
        Reservation r1 = new Reservation("RES101", "Alice", "Deluxe");
        Reservation r2 = new Reservation("RES102", "Bob", "Suite");
        Reservation r3 = new Reservation("RES103", "Charlie", "Deluxe");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Step 3: Reporting Service
        BookingReportService reportService = new BookingReportService(history);

        // Step 4: Admin views all bookings
        reportService.showAllBookings();

        // Step 5: Admin generates summary report
        reportService.generateSummaryReport();
    }
}