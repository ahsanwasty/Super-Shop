/**
 * Concrete discount strategies.
 *
 * Fixes OCP: adding a new coupon = adding a new class, zero modification of
 * existing classes.
 * Fixes DIP: callers depend on IDiscountStrategy, not on these classes.
 */

class Save10Discount implements IDiscountStrategy {
    @Override
    public double calculate(double subtotal, String code) {
        if ("SAVE10".equalsIgnoreCase(code == null ? "" : code.trim()))
            return Math.min(subtotal * 0.10, 200);
        return 0;
    }
}

class Save5Discount implements IDiscountStrategy {
    @Override
    public double calculate(double subtotal, String code) {
        if ("SAVE5".equalsIgnoreCase(code == null ? "" : code.trim()))
            return Math.min(subtotal * 0.05, 100);
        return 0;
    }
}

class Flat50Discount implements IDiscountStrategy {
    @Override
    public double calculate(double subtotal, String code) {
        if ("FLAT50".equalsIgnoreCase(code == null ? "" : code.trim()))
            return Math.min(50, subtotal);
        return 0;
    }
}
