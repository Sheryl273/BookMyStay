import java.util.*;

public class UseCase10BookingCancellation {

    // -------- Inventory --------
    static class Inventory {
        private Map<String, Integer> map = new HashMap<>();

        void add(String type, int count) {
            map.put(type, count);
        }

        void increment(String type) {
            map.put(type, map.get(type) + 1);
        }
    }

    // -------- Cancellation Service --------
    static class CancellationService {

        private Stack<String> rollbackStack = new Stack<>();
        private Map<String, String> bookings = new HashMap<>();

        public void confirmBooking(String resId, String roomId) {
            bookings.put(resId, roomId);
        }

        public void cancel(String resId, Inventory inv, String roomType) {

            if (!bookings.containsKey(resId)) {
                System.out.println("Invalid cancellation request.");
                return;
            }

            String roomId = bookings.remove(resId);

            rollbackStack.push(roomId);

            inv.increment(roomType);

            System.out.println("Cancelled booking: " + resId +
                    " | Room Released: " + roomId);
        }
    }

    // -------- Main --------
    public static void main(String[] args) {

        Inventory inv = new Inventory();
        inv.add("Deluxe", 1);

        CancellationService service = new CancellationService();

        service.confirmBooking("RES101", "DLX-001");

        service.cancel("RES101", inv, "Deluxe");
        service.cancel("RES999", inv, "Deluxe"); // invalid
    }
}