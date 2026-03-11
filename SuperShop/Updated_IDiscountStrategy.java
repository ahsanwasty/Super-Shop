/**
 * Abstraction for discount strategies.
 * Fixes OCP: new discount types are added by implementing this interface,
 * not by modifying existing code.
 * Fixes DIP: CheckoutService depends on this abstraction, not a concrete class.
 */
public interface IDiscountStrategy {
    /**
     * Returns the discount amount for the given subtotal and coupon code.
     * Returns 0 if the code does not match or is not applicable.
     */
    double calculate(double subtotal, String code);
}
