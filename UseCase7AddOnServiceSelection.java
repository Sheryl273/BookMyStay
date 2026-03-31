import java.util.*;

public class UseCase7AddOnServiceSelection {

    // -------- Reservation (Already Confirmed Booking) --------
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

        public void displayReservation() {
            System.out.println("Reservation ID: " + reservationId +
                    " | Guest: " + guestName +
                    " | Room: " + roomType);
        }
    }

    // -------- Add-On Service --------
    static class Service {
        private String serviceName;
        private double cost;

        public Service(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }

        public void displayService() {
            System.out.println(serviceName + " - ₹" + cost);
        }
    }

    // -------- Add-On Service Manager --------
    static class AddOnServiceManager {

        // Map<ReservationID, List of Services>
        private Map<String, List<Service>> serviceMap = new HashMap<>();

        // Add service to reservation
        public void addService(String reservationId, Service service) {

            serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

            System.out.println("Added service: " + service.getServiceName()
                    + " to Reservation ID: " + reservationId);
        }

        // View services for a reservation
        public void viewServices(String reservationId) {

            System.out.println("\nServices for Reservation ID: " + reservationId);

            List<Service> services = serviceMap.get(reservationId);

            if (services == null || services.isEmpty()) {
                System.out.println("No add-on services selected.");
                return;
            }

            for (Service s : services) {
                s.displayService();
            }
        }

        // Calculate total additional cost
        public double calculateTotalCost(String reservationId) {

            List<Service> services = serviceMap.get(reservationId);

            if (services == null) return 0;

            double total = 0;
            for (Service s : services) {
                total += s.getCost();
            }

            return total;
        }
    }

    // -------- Main Method --------
    public static void main(String[] args) {

        // Step 1: Existing Reservation (from Use Case 6)
        Reservation r1 = new Reservation("RES101", "Alice", "Deluxe");

        r1.displayReservation();

        // Step 2: Create Services
        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa Access", 1200);
        Service pickup = new Service("Airport Pickup", 800);

        // Step 3: Add-On Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Step 4: Guest selects services
        manager.addService("RES101", breakfast);
        manager.addService("RES101", spa);
        manager.addService("RES101", pickup);

        // Step 5: View selected services
        manager.viewServices("RES101");

        // Step 6: Calculate total add-on cost
        double totalCost = manager.calculateTotalCost("RES101");

        System.out.println("\nTotal Add-On Cost: ₹" + totalCost);
    }
}
