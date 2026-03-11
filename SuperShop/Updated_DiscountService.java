import java.util.List;

/**
 * Applies a list of discount strategies in order, returning the first match.
 *
 * Fixes OCP: new strategies are registered here without modifying existing logic.
 * Fixes DIP: depends on IDiscountStrategy abstraction, not concrete coupon classes.
 */
public class DiscountService {
    private final List<IDiscountStrategy> strategies;

    public DiscountService(List<IDiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    public double calculateDiscount(double subtotal, String code) {
        if (subtotal <= 0 || code == null || code.isBlank()) return 0;
        for (IDiscountStrategy strategy : strategies) {
            double amount = strategy.calculate(subtotal, code);
            if (amount > 0) return amount;
        }
        return 0;
    }
}
