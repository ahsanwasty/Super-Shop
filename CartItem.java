public class CartItem {
    private final Product product;
    private int qty;

    public CartItem(Product product, int qty) {
        this.product = product;
        this.qty = Math.max(0, qty);
    }

    public Product getProduct() { return product; }
    public int getQty() { return qty; }

    public void add(int q) {
        if (q > 0) qty += q;
    }

    public void sub(int q) {
        if (q > 0) qty -= q;
        if (qty < 0) qty = 0;
    }

    public double lineTotal() {
        return product.getPrice() * qty;
    }
}