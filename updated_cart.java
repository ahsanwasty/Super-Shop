import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private final ArrayList<CartItem> items = new ArrayList<>();

    public List<CartItem> items() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public CartItem find(int productId) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                return item;
            }
        }
        return null;
    }

    public void add(Product product, int qty) {
        CartItem existing = find(product.getId());
        if (existing == null) {
            items.add(new CartItem(product, qty));
        } else {
            existing.add(qty);
        }
    }

    public boolean remove(int productId, int qty) {
        CartItem existing = find(productId);
        if (existing == null) {
            return false;
        }
        existing.sub(qty);
        if (existing.getQty() == 0) {
            items.remove(existing);
        }
        return true;
    }

    public double subtotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.lineTotal();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }
}
