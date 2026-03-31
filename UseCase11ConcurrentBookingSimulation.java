public class UseCase11ConcurrentBookingSimulation {

    static class Inventory {
        private int rooms = 2;

        public synchronized boolean bookRoom(String guest) {
            if (rooms > 0) {
                System.out.println(guest + " booked a room.");
                rooms--;
                return true;
            } else {
                System.out.println(guest + " failed (no rooms).");
                return false;
            }
        }
    }

    static class BookingTask implements Runnable {

        private Inventory inventory;
        private String guest;

        public BookingTask(Inventory inventory, String guest) {
            this.inventory = inventory;
            this.guest = guest;
        }

        public void run() {
            inventory.bookRoom(guest);
        }
    }

    public static void main(String[] args) {

        Inventory inventory = new Inventory();

        Thread t1 = new Thread(new BookingTask(inventory, "Alice"));
        Thread t2 = new Thread(new BookingTask(inventory, "Bob"));
        Thread t3 = new Thread(new BookingTask(inventory, "Charlie"));

        t1.start();
        t2.start();
        t3.start();
    }
}
