package rental;

public class Equipment {
    private final int id;
    private final String name;
    private final String category;
    private final double dailyRate;
    private boolean available = true;

    public Equipment(int id, String name, String category, double dailyRate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.dailyRate = dailyRate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getDailyRate() { return dailyRate; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("#%d %-18s %-12s Rs.%8.2f/day  [%s]",
                id, name, category, dailyRate, available ? "Available" : "Rented");
    }
}
