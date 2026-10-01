package rental;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentalService {
    private final List<Equipment> inventory = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Rental> rentals = new ArrayList<>();
    private int nextEquipmentId = 1, nextCustomerId = 1, nextRentalId = 1;

    public Equipment addEquipment(String name, String category, double dailyRate) {
        Equipment e = new Equipment(nextEquipmentId++, name, category, dailyRate);
        inventory.add(e);
        return e;
    }

    public Customer addCustomer(String name, String phone) {
        Customer c = new Customer(nextCustomerId++, name, phone);
        customers.add(c);
        return c;
    }

    public Optional<Equipment> findEquipment(int id) {
        return inventory.stream().filter(e -> e.getId() == id).findFirst();
    }

    public Optional<Customer> findCustomer(int id) {
        return customers.stream().filter(c -> c.getId() == id).findFirst();
    }

    public Rental rent(int equipmentId, int customerId, int days) {
        if (days <= 0) throw new IllegalArgumentException("Days must be at least 1.");
        Equipment e = findEquipment(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found."));
        Customer c = findCustomer(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
        if (!e.isAvailable()) throw new IllegalStateException("Equipment is already rented.");

        e.setAvailable(false);
        Rental r = new Rental(nextRentalId++, e, c, LocalDate.now(), days);
        rentals.add(r);
        return r;
    }

    public Rental returnEquipment(int rentalId, LocalDate returnDate) {
        Rental r = rentals.stream()
                .filter(x -> x.getId() == rentalId && x.isActive())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active rental with that ID."));
        r.close(returnDate);
        r.getEquipment().setAvailable(true);
        return r;
    }

    public List<Equipment> getInventory() { return inventory; }
    public List<Customer> getCustomers() { return customers; }
    public List<Rental> getRentals() { return rentals; }
}
