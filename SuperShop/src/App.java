import java.util.Scanner;

public class App {
    private final Scanner sc = new Scanner(System.in);
    private final Input input = new Input(sc);
    private final Shop shop = new Shop();
    private final Cart cart = new Cart();
    private final Menu menu = new Menu();
    private final Discount discount = new Discount();
    private Customer customer = new Customer("Walk-in", "0000");
    private final String salesFile = "sales.txt";
    private final String customersFile = "customers.txt";
    private final String adminPassword = "admin123";

    public void run() {
        while (true) {
            menu.printMain();
            int choice = input.readInt("Choose: ");
            if (choice == 0) break;

            if (choice == 1) {
                shop.viewProducts();
            } else if (choice == 2) {
                addToCartFlow();
            } else if (choice == 3) {
                shop.viewCart(cart);
            } else if (choice == 4) {
                removeFromCartFlow();
            } else if (choice == 5) {
                registerFlow();
            } else if (choice == 6) {
                loginFlow();
            } else if (choice == 7) {
                checkoutFlow();
            } else if (choice == 8) {
                adminFlow();
            } else if (choice == 9) {
                Receipt.showSalesHistory(salesFile);
            } else {
                System.out.println("Invalid option.");
            }
        }
        sc.close();
        System.out.println("Goodbye!");
    }

    private void addToCartFlow() {
        shop.viewProducts();
        int id = input.readInt("Enter product ID: ");
        int qty = input.readInt("Enter quantity: ");
        boolean ok = shop.addToCart(cart, id, qty);
        if (ok) System.out.println("Added to cart.");
        else System.out.println("Failed to add. Check ID/qty/stock.");
    }

    private void removeFromCartFlow() {
        shop.viewCart(cart);
        if (cart.isEmpty()) return;
        int id = input.readInt("Enter product ID to remove: ");
        int qty = input.readInt("Enter quantity to remove: ");
        boolean ok = shop.removeFromCart(cart, id, qty);
        if (ok) System.out.println("Removed from cart.");
        else System.out.println("Failed to remove. Check ID/qty.");
    }

    private void registerFlow() {
        String name = input.readLine("Name: ");
        String phone = input.readLine("Phone: ");
        String password = input.readLine("Password: ");

        boolean ok = Customer.register(customersFile, name, phone, password);
        if (!ok) {
            System.out.println("Registration failed. Phone may already exist or invalid input.");
            return;
        }
        System.out.println("Registration successful. Now login.");
    }

    private void loginFlow() {
        String phone = input.readLine("Phone: ");
        String password = input.readLine("Password: ");

        Customer c = Customer.login(customersFile, phone, password);
        if (c == null) {
            System.out.println("Login failed.");
            return;
        }
        customer = c;
        System.out.println("Logged in as: " + customer.getName() + " (" + customer.getPhone() + ")");
    }

    private void checkoutFlow() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        shop.viewCart(cart);

        double subtotal = cart.subtotal();
        double vat = subtotal * 0.05;
        String code = input.readLine("Coupon code (press Enter to skip): ");
        double disc = discount.calculateDiscount(subtotal, code);

        double total = subtotal + vat - disc;
        if (total < 0) total = 0;

        System.out.println("Subtotal: " + TextFormatter.money(subtotal));
        System.out.println("VAT (5%): " + TextFormatter.money(vat));
        System.out.println("Discount: " + TextFormatter.money(disc));
        System.out.println("Grand Total: " + TextFormatter.money(total));

        double paid = input.readDouble("Payment amount: ");
        Payment payment = new Payment(paid, total);

        if (!payment.isSufficient()) {
            System.out.println("Insufficient payment. Need: " + TextFormatter.money(payment.getDue()));
            return;
        }

        Receipt receipt = new Receipt(customer, cart, subtotal, vat, disc, total, payment.getPaid(), payment.getChange());
        receipt.print();
        receipt.saveToFile(salesFile);

        shop.commitSale(cart);
        cart.clear();
        System.out.println("Checkout complete.");
    }

    private void adminFlow() {
        String pass = input.readLine("Admin password: ");
        if (!pass.equals(adminPassword)) {
            System.out.println("Access denied.");
            return;
        }

        while (true) {
            menu.printAdmin();
            int c = input.readInt("Choose: ");
            if (c == 0) break;

            if (c == 1) {
                adminAddProduct();
            } else if (c == 2) {
                adminUpdateStock();
            } else if (c == 3) {
                adminUpdatePrice();
            } else if (c == 4) {
                shop.viewProducts();
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void adminAddProduct() {
        String name = input.readLine("Product name: ");
        String cat = input.readLine("Category (GROCERY/HOUSEHOLD/SNACKS): ").trim().toUpperCase();
        Category category;
        try {
            category = Category.valueOf(cat);
        } catch (Exception e) {
            System.out.println("Invalid category.");
            return;
        }
        double price = input.readDouble("Price: ");
        int stock = input.readInt("Stock: ");

        Product p = shop.adminAddProduct(name, category, price, stock);
        if (p == null) {
            System.out.println("Failed to add product.");
            return;
        }
        System.out.println("Added: ID " + p.getId() + " - " + p.getName());
    }

    private void adminUpdateStock() {
        shop.viewProducts();
        int id = input.readInt("Product ID: ");
        int add = input.readInt("Add stock amount: ");
        boolean ok = shop.adminUpdateStock(id, add);
        if (ok) System.out.println("Stock updated.");
        else System.out.println("Failed to update stock.");
    }

    private void adminUpdatePrice() {
        shop.viewProducts();
        int id = input.readInt("Product ID: ");
        double price = input.readDouble("New price: ");
        boolean ok = shop.adminUpdatePrice(id, price);
        if (ok) System.out.println("Price updated.");
        else System.out.println("Failed to update price.");
    }
}