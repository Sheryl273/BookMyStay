import java.io.*;
import java.util.*;

public class UseCase12DataPersistenceRecovery {

    // -------- Inventory --------
    static class Inventory implements Serializable {
        Map<String, Integer> map = new HashMap<>();
    }

    // -------- Persistence Service --------
    static class PersistenceService {

        public static void save(Inventory inv, String file) {
            try (ObjectOutputStream out =
                         new ObjectOutputStream(new FileOutputStream(file))) {

                out.writeObject(inv);
                System.out.println("Data saved successfully.");

            } catch (Exception e) {
                System.out.println("Error saving data.");
            }
        }

        public static Inventory load(String file) {

            try (ObjectInputStream in =
                         new ObjectInputStream(new FileInputStream(file))) {

                System.out.println("Data loaded successfully.");
                return (Inventory) in.readObject();

            } catch (Exception e) {
                System.out.println("No previous data found. Starting fresh.");
                return new Inventory();
            }
        }
    }

    // -------- Main --------
    public static void main(String[] args) {

        String file = "data.ser";

        // Load previous state
        Inventory inv = PersistenceService.load(file);

        // Modify data
        inv.map.put("Deluxe", 5);

        // Save state
        PersistenceService.save(inv, file);

        // Reload to verify
        Inventory loaded = PersistenceService.load(file);

        System.out.println("Recovered Data: " + loaded.map);
    }
}
