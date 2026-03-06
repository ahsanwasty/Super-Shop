public class Product {
    private final int id;
    private String name;
    private Category category;
    private double price;
    private int stock;
    private int reserved;

    public Product(int id, String name, Category category, double price, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.reserved = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public boolean reserve(int qty) {
        if (qty <= 0) {
            return false;
        }
        if (stock - reserved < qty) {
            return false;
        }
        reserved += qty;
        return true;
    }

    public void release(int qty) {
        if (qty <= 0) {
            return;
        }
        reserved -= qty;
        if (reserved < 0) {
            reserved = 0;
        }
    }

    public void commit(int qty) {
        if (qty <= 0) {
            return;
        }
        if (reserved >= qty) {
            reserved -= qty;
        }
        stock -= qty;
        if (stock < 0) {
            stock = 0;
        }
    }

    public void addStock(int qty) {
        if (qty > 0) {
            stock += qty;
        }
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        }
    }
}
