/**
 * Owns checkout business logic: VAT calculation, discount application,
 * payment validation, receipt creation, and sale commitment.
 *
 * Fixes SRP: this logic was previously spread across App.checkoutFlow().
 * Fixes DIP: depends on IDiscountStrategy, ICustomerShopService, ISalesRepository
 *            abstractions — not on concrete classes.
 */
public class CheckoutService {
    private static final double VAT_RATE = 0.05;

    private final ICustomerShopService shopService;
    private final DiscountService discountService;
    private final ISalesRepository salesRepository;

    public CheckoutService(ICustomerShopService shopService,
                           DiscountService discountService,
                           ISalesRepository salesRepository) {
        this.shopService      = shopService;
        this.discountService  = discountService;
        this.salesRepository  = salesRepository;
    }

    /**
     * Performs the full checkout flow.
     * @return true if the checkout completed successfully, false otherwise.
     */
    public boolean checkout(Customer customer, Cart cart, String couponCode, double paid) {
        if (cart.isEmpty()) return false;

        double subtotal = cart.subtotal();
        double vat      = subtotal * VAT_RATE;
        double disc     = discountService.calculateDiscount(subtotal, couponCode);
        double total    = Math.max(0, subtotal + vat - disc);

        Payment payment = new Payment(paid, total);
        if (!payment.isSufficient()) {
            System.out.println("Insufficient payment. Need: " + TextFormatter.money(payment.getDue()));
            return false;
        }

        Receipt receipt = new Receipt(customer, cart, subtotal, vat, disc, total,
                                      payment.getPaid(), payment.getChange());
        receipt.print();
        salesRepository.save(receipt.toRecord());

        shopService.commitSale(cart);
        cart.clear();
        return true;
    }

    /** Prints a summary of costs before asking for payment, so App can show it. */
    public CheckoutSummary summarise(Cart cart, String couponCode) {
        double subtotal = cart.subtotal();
        double vat      = subtotal * VAT_RATE;
        double disc     = discountService.calculateDiscount(subtotal, couponCode);
        double total    = Math.max(0, subtotal + vat - disc);
        return new CheckoutSummary(subtotal, vat, disc, total);
    }

    /** Simple value object returned by summarise(). */
    public static class CheckoutSummary {
        public final double subtotal, vat, discount, total;

        CheckoutSummary(double subtotal, double vat, double discount, double total) {
            this.subtotal = subtotal;
            this.vat      = vat;
            this.discount = discount;
            this.total    = total;
        }
    }
}
