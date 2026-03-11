/**
 * Handles user interactions for cart operations.
 *
 * Fixes SRP: cart flow logic was previously embedded in App, giving App too
 * many reasons to change. CartFlow has exactly one reason to change:
 * how the user interacts with the cart.
 */
public class CartFlow {
    private final ICustomerShopService shopService;
    private final Input input;

    public CartFlow(ICustomerShopService shopService, Input input) {
        this.shopService = shopService;
        this.input       = input;
    }

    public void addToCart(Cart cart) {
        shopService.viewProducts();
        int id  = input.readInt("Enter product ID: ");
        int qty = input.readInt("Enter quantity: ");
        boolean ok = shopService.addToCart(cart, id, qty);
        System.out.println(ok ? "Added to cart." : "Failed to add. Check ID/qty/stock.");
    }

    public void removeFromCart(Cart cart) {
        shopService.viewCart(cart);
        if (cart.isEmpty()) return;
        int id  = input.readInt("Enter product ID to remove: ");
        int qty = input.readInt("Enter quantity to remove: ");
        boolean ok = shopService.removeFromCart(cart, id, qty);
        System.out.println(ok ? "Removed from cart." : "Failed to remove. Check ID/qty.");
    }
}
