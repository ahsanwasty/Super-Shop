import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final ArrayList<CartItem> items = new ArrayList<>();

    public List<CartItem> items() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public CartItem find(int productId) {
        for (CartItem ci : items) {
            if (ci.getProduct().getId() == productId) return ci;
        }
        return null;
    }

    public void add(Product p, int qty) {
        CartItem existing = find(p.getId());
        if (existing == null) items.add(new CartItem(p, qty));
        else existing.add(qty);
    }

    public boolean remove(int productId, int qty) {
        CartItem existing = find(productId);
        if (existing == null) return false;
        existing.sub(qty);
        if (existing.getQty() == 0) items.remove(existing);
        return true;
    }

    public double subtotal() {
        double s = 0;
        for (CartItem ci : items) s += ci.lineTotal();
        return s;
    }

    public void clear() {
        items.clear();
    }
}