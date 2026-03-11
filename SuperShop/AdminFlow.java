/**
 * Handles user interactions for the admin panel.
 *
 * Fixes SRP: admin flow was previously embedded in App.
 * Fixes ISP: depends on IAdminShopService only — no customer methods visible.
 */
public class AdminFlow {
    private static final String ADMIN_PASSWORD = "admin123";

    private final IAdminShopService adminService;
    private final Input input;
    private final Menu menu;

    public AdminFlow(IAdminShopService adminService, Input input, Menu menu) {
        this.adminService = adminService;
        this.input        = input;
        this.menu         = menu;
    }

    public void run() {
        String pass = input.readLine("Admin password: ");
        if (!pass.equals(ADMIN_PASSWORD)) {
            System.out.println("Access denied.");
            return;
        }

        while (true) {
            menu.printAdmin();
            int c = input.readInt("Choose: ");
            if (c == 0) break;

            else if (c == 1) addProduct();
            else if (c == 2) updateStock();
            else if (c == 3) updatePrice();
            else if (c == 4) adminService.viewProducts();
            else             System.out.println("Invalid option.");
        }
    }

    private void addProduct() {
        String name = input.readLine("Product name: ");
        String cat  = input.readLine("Category (GROCERY/HOUSEHOLD/SNACKS): ").trim().toUpperCase();
        Category category;
        try {
            category = Category.valueOf(cat);
        } catch (Exception e) {
            System.out.println("Invalid category.");
            return;
        }
        double price = input.readDouble("Price: ");
        int stock    = input.readInt("Stock: ");

        Product p = adminService.addProduct(name, category, price, stock);
        if (p == null) { System.out.println("Failed to add product."); return; }
        System.out.println("Added: ID " + p.getId() + " - " + p.getName());
    }

    private void updateStock() {
        adminService.viewProducts();
        int id  = input.readInt("Product ID: ");
        int add = input.readInt("Add stock amount: ");
        boolean ok = adminService.updateStock(id, add);
        System.out.println(ok ? "Stock updated." : "Failed to update stock.");
    }

    private void updatePrice() {
        adminService.viewProducts();
        int id       = input.readInt("Product ID: ");
        double price = input.readDouble("New price: ");
        boolean ok   = adminService.updatePrice(id, price);
        System.out.println(ok ? "Price updated." : "Failed to update price.");
    }
}
