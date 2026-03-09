import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final ArrayList<Product> products = new ArrayList<>();
    private int nextId = 1;

    public Inventory() {
        seed(new Product(genId(), "Rice 1kg", Category.GROCERY, 85.0, 30));
        seed(new Product(genId(), "Lentil 1kg", Category.GROCERY, 140.0, 20));
        seed(new Product(genId(), "Sugar 1kg", Category.GROCERY, 120.0, 15));
        seed(new Product(genId(), "Oil 1L", Category.GROCERY, 185.0, 25));
        seed(new Product(genId(), "Soap", Category.HOUSEHOLD, 45.0, 50));
        seed(new Product(genId(), "Shampoo", Category.HOUSEHOLD, 220.0, 18));
        seed(new Product(genId(), "Biscuit", Category.SNACKS, 20.0, 60));
        seed(new Product(genId(), "Chocolate", Category.SNACKS, 55.0, 40));
    }

    private void seed(Product p) {
        products.add(p);
    }

    private int genId() {
        return nextId++;
    }

    public List<Product> getAll() {
        return products;
    }

    public Product findById(int id) {
        for (Product p : products) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public Product addProduct(String name, Category category, double price, int stock) {
        if (name == null || name.isBlank()) return null;
        if (category == null) return null;
        if (price < 0) return null;
        if (stock < 0) return null;
        Product p = new Product(genId(), name.trim(), category, price, stock);
        products.add(p);
        return p;
    }
}