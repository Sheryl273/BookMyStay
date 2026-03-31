import java.util.*;

public class UseCase9ErrorHandlingValidation {

    // -------- Custom Exception --------
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // -------- Inventory --------
    static class Inventory {
        private Map<String, Integer> rooms = new HashMap<>();

        public void addRoom(String type, int count) {
            rooms.put(type, count);
        }

        public int getAvailability(String type) {
            return rooms.getOrDefault(type, -1);
        }

        public void decrement(String type) {
            rooms.put(type, rooms.get(type) - 1);
        }
    }

    // -------- Validator --------
    static class Validator {

        public static void validate(String roomType, Inventory inv)
                throws InvalidBookingException {

            if (roomType == null || roomType.isEmpty())
                throw new InvalidBookingException("Room type cannot be empty");

            if (inv.getAvailability(roomType) == -1)
                throw new InvalidBookingException("Invalid room type");

            if (inv.getAvailability(roomType) <= 0)
                throw new InvalidBookingException("No rooms available");
        }
    }

    // -------- Main --------
    public static void main(String[] args) {

        Inventory inv = new Inventory();
        inv.addRoom("Deluxe", 1);

        String request = "Deluxe";

        try {
            Validator.validate(request, inv);
            inv.decrement(request);
            System.out.println("Booking successful!");
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Invalid case
        try {
            Validator.validate("Suite", inv);
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
