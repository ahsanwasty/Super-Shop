/**
 * Handles user interactions for checkout.
 *
 * Fixes SRP: checkout interaction was previously embedded in App, and
 * business logic (VAT, discount, payment) was mixed in there too.
 * Now interaction lives here; logic lives in CheckoutService.
 */
public class CheckoutFlow {
    private final CheckoutService checkoutService;
    private final ICustomerShopService shopService;
    private final Input input;

    public CheckoutFlow(CheckoutService checkoutService,
                        ICustomerShopService shopService,
                        Input input) {
        this.checkoutService = checkoutService;
        this.shopService     = shopService;
        this.input           = input;
    }

    public void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        shopService.viewCart(cart);

        String code = input.readLine("Coupon code (press Enter to skip): ");

        CheckoutService.CheckoutSummary summary = checkoutService.summarise(cart, code);
        System.out.println("Subtotal: "    + TextFormatter.money(summary.subtotal));
        System.out.println("VAT (5%): "   + TextFormatter.money(summary.vat));
        System.out.println("Discount: "   + TextFormatter.money(summary.discount));
        System.out.println("Grand Total: " + TextFormatter.money(summary.total));

        double paid = input.readDouble("Payment amount: ");

        boolean ok = checkoutService.checkout(customer, cart, code, paid);
        if (ok) System.out.println("Checkout complete.");
    }
}
