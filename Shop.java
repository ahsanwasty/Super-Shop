public class Shop {
    private final Inventory inventory = new Inventory();

    public void viewProducts() {
        System.out.println();
        System.out.println("ID  Name                 Category        Price    Stock");
        for (Product p : inventory.getAll()) {
            String line =
                TextFormatter.padRight(String.valueOf(p.getId()), 4) +
                TextFormatter.padRight(p.getName(), 21) +
                TextFormatter.padRight(p.getCategory().getLabel(), 16) +
                TextFormatter.padRight(TextFormatter.money(p.getPrice()), 9) +
                p.getStock();
            System.out.println(line);
        }
    }

    public boolean addToCart(Cart cart, int productId, int qty) {
        if (qty <= 0) return false;
        Product p = inventory.findById(productId);
        if (p == null) return false;
        if (!p.reserve(qty)) return false;
        cart.add(p, qty);
        return true;
    }

    public void viewCart(Cart cart) {
        System.out.println();
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("ID  Name                 Price    Qty   Total");
        for (CartItem ci : cart.items()) {
            Product p = ci.getProduct();
            String line =
                TextFormatter.padRight(String.valueOf(p.getId()), 4) +
                TextFormatter.padRight(p.getName(), 21) +
                TextFormatter.padRight(TextFormatter.money(p.getPrice()), 9) +
                TextFormatter.padRight(String.valueOf(ci.getQty()), 6) +
                TextFormatter.money(ci.lineTotal());
            System.out.println(line);
        }
        System.out.println("Subtotal: " + TextFormatter.money(cart.subtotal()));
    }

    public boolean removeFromCart(Cart cart, int productId, int qty) {
        if (qty <= 0) return false;
        CartItem ci = cart.find(productId);
        if (ci == null) return false;
        int actual = Math.min(qty, ci.getQty());
        cart.remove(productId, actual);
        Product p = inventory.findById(productId);
        if (p != null) p.release(actual);
        return true;
    }

    public void commitSale(Cart cart) {
        for (CartItem ci : cart.items()) {
            Product p = inventory.findById(ci.getProduct().getId());
            if (p != null) p.commit(ci.getQty());
        }
    }

    public Product adminAddProduct(String name, Category category, double price, int stock) {
        return inventory.addProduct(name, category, price, stock);
    }

    public boolean adminUpdateStock(int productId, int addQty) {
        if (addQty <= 0) return false;
        Product p = inventory.findById(productId);
        if (p == null) return false;
        p.addStock(addQty);
        return true;
    }

    public boolean adminUpdatePrice(int productId, double newPrice) {
        if (newPrice < 0) return false;
        Product p = inventory.findById(productId);
        if (p == null) return false;
        p.setPrice(newPrice);
        return true;
    }

    public Product findProduct(int id) {
        return inventory.findById(id);
    }
}