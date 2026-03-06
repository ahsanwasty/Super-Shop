public class Payment {
    private final double paid;
    private final double total;

    public Payment(double paid, double total) {
        this.paid = paid;
        this.total = total;
    }

    public double getPaid() { return paid; }

    public boolean isSufficient() {
        return paid >= total;
    }

    public double getChange() {
        double c = paid - total;
        return c < 0 ? 0 : c;
    }

    public double getDue() {
        double d = total - paid;
        return d < 0 ? 0 : d;
    }
}