public class Discount {
    public double calculateDiscount(double subtotal, String code) {
        if (subtotal <= 0) return 0;
        if (code == null) return 0;
        String c = code.trim().toUpperCase();
        if (c.isEmpty()) return 0;

        if (c.equals("SAVE10")) return Math.min(subtotal * 0.10, 200);
        if (c.equals("SAVE5")) return Math.min(subtotal * 0.05, 100);
        if (c.equals("FLAT50")) return Math.min(50, subtotal);

        return 0;
    }
}