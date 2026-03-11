/**
 * Application entry point / main loop.
 *
 * Fixes SRP: App is now a thin orchestrator. All business logic and flow details
 * have been extracted into dedicated service and flow classes.
 *
 * Fixes DIP: App receives all dependencies via constructor instead of
 * instantiating concrete classes itself. The wiring is done in AppFactory.
 */
public class App {
    private final Input input;
    private final Menu menu;
    private final ICustomerShopService shopService;
    private final CartFlow cartFlow;
    private final AuthFlow authFlow;
    private final CheckoutFlow checkoutFlow;
    private final AdminFlow adminFlow;
    private final SalesHistoryService salesHistoryService;

    private final Cart cart = new Cart();
    private Customer customer = new Customer("Walk-in", "0000");

    public App(Input input,
               Menu menu,
               ICustomerShopService shopService,
               CartFlow cartFlow,
               AuthFlow authFlow,
               CheckoutFlow checkoutFlow,
               AdminFlow adminFlow,
               SalesHistoryService salesHistoryService) {
        this.input               = input;
        this.menu                = menu;
        this.shopService         = shopService;
        this.cartFlow            = cartFlow;
        this.authFlow            = authFlow;
        this.checkoutFlow        = checkoutFlow;
        this.adminFlow           = adminFlow;
        this.salesHistoryService = salesHistoryService;
    }

    public void run() {
        while (true) {
            menu.printMain();
            int choice = input.readInt("Choose: ");
            if (choice == 0) break;

            switch (choice) {
                case 1 -> shopService.viewProducts();
                case 2 -> cartFlow.addToCart(cart);
                case 3 -> shopService.viewCart(cart);
                case 4 -> cartFlow.removeFromCart(cart);
                case 5 -> authFlow.register();
                case 6 -> {
                    Customer c = authFlow.login();
                    if (c != null) customer = c;
                }
                case 7 -> checkoutFlow.checkout(customer, cart);
                case 8 -> adminFlow.run();
                case 9 -> salesHistoryService.show();
                default -> System.out.println("Invalid option.");
            }
        }
        System.out.println("Goodbye!");
    }
}
