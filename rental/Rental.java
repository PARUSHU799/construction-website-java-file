package rental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rental {
    private static final double LATE_FEE_MULTIPLIER = 1.5; // late days cost 150%

    private final int id;
    private final Equipment equipment;
    private final Customer customer;
    private final LocalDate startDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private double totalCost;

    public Rental(int id, Equipment equipment, Customer customer, LocalDate startDate, int days) {
        this.id = id;
        this.equipment = equipment;
        this.customer = customer;
        this.startDate = startDate;
        this.dueDate = startDate.plusDays(days);
        this.totalCost = days * equipment.getDailyRate();
    }

    /** Closes the rental and adds a late fee if returned after the due date. */
    public void close(LocalDate returned) {
        this.returnDate = returned;
        long lateDays = ChronoUnit.DAYS.between(dueDate, returned);
        if (lateDays > 0) {
            totalCost += lateDays * equipment.getDailyRate() * LATE_FEE_MULTIPLIER;
        }
    }

    public boolean isActive() { return returnDate == null; }
    public int getId() { return id; }
    public Equipment getEquipment() { return equipment; }
    public Customer getCustomer() { return customer; }
    public double getTotalCost() { return totalCost; }

    @Override
    public String toString() {
        return String.format("Rental #%d | %s -> %s | %s to %s | Rs.%.2f | %s",
                id, equipment.getName(), customer.getName(), startDate, dueDate,
                totalCost, isActive() ? "ACTIVE" : "RETURNED " + returnDate);
    }
}
