package rental;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final RentalService service = new RentalService();
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        boolean running = true;
        while (running) {
            printMenu();
            try {
                switch (readInt("Choose: ")) {
                    case 1 -> service.getInventory().forEach(System.out::println);
                    case 2 -> addEquipment();
                    case 3 -> addCustomer();
                    case 4 -> service.getCustomers().forEach(System.out::println);
                    case 5 -> rentEquipment();
                    case 6 -> returnEquipment();
                    case 7 -> service.getRentals().forEach(System.out::println);
                    case 0 -> running = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n=== Construction Rental Platform ===");
        System.out.println("1. View equipment      2. Add equipment");
        System.out.println("3. Add customer        4. View customers");
        System.out.println("5. Rent equipment      6. Return equipment");
        System.out.println("7. View all rentals    0. Exit");
    }

    private static void addEquipment() {
        String name = readLine("Name: ");
        String category = readLine("Category: ");
        double rate = Double.parseDouble(readLine("Daily rate: "));
        System.out.println("Added: " + service.addEquipment(name, category, rate));
    }

    private static void addCustomer() {
        String name = readLine("Name: ");
        String phone = readLine("Phone: ");
        System.out.println("Added: " + service.addCustomer(name, phone));
    }

    private static void rentEquipment() {
        int eq = readInt("Equipment ID: ");
        int cu = readInt("Customer ID: ");
        int days = readInt("Number of days: ");
        System.out.println("Created: " + service.rent(eq, cu, days));
    }

    private static void returnEquipment() {
        int id = readInt("Rental ID: ");
        Rental r = service.returnEquipment(id, LocalDate.now());
        System.out.println("Returned: " + r);
    }

    private static int readInt(String prompt) {
        return Integer.parseInt(readLine(prompt));
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }

    private static void seedData() {
        service.addEquipment("Excavator", "Earthmoving", 8500);
        service.addEquipment("Concrete Mixer", "Concrete", 1800);
        service.addEquipment("Scaffolding Set", "Access", 900);
        service.addEquipment("Jackhammer", "Demolition", 1200);
        service.addCustomer("Ravi Constructions", "9876543210");
        service.addCustomer("Sri Builders", "9123456780");
    }
}
