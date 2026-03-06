import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt {
    private final Customer customer;
    private final Cart cart;
    private final double subtotal;
    private final double vat;
    private final double discount;
    private final double total;
    private final double paid;
    private final double change;
    private final String time;

    public Receipt(Customer customer, Cart cart, double subtotal, double vat, double discount, double total, double paid, double change) {
        this.customer = customer;
        this.cart = cart;
        this.subtotal = subtotal;
        this.vat = vat;
        this.discount = discount;
        this.total = total;
        this.paid = paid;
        this.change = change;
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void print() {
        System.out.println();
        System.out.println("========== SUPER SHOP ==========");
        System.out.println("Time: " + time);
        System.out.println("Customer: " + customer.getName() + " (" + customer.getPhone() + ")");
        System.out.println("--------------------------------");
        System.out.println("Name                 Qty   Total");
        for (CartItem ci : cart.items()) {
            String line =
                TextFormatter.padRight(ci.getProduct().getName(), 21) +
                TextFormatter.padRight(String.valueOf(ci.getQty()), 6) +
                TextFormatter.money(ci.lineTotal());
            System.out.println(line);
        }
        System.out.println("--------------------------------");
        System.out.println("Subtotal : " + TextFormatter.money(subtotal));
        System.out.println("VAT 5%   : " + TextFormatter.money(vat));
        System.out.println("Discount : " + TextFormatter.money(discount));
        System.out.println("Total    : " + TextFormatter.money(total));
        System.out.println("Paid     : " + TextFormatter.money(paid));
        System.out.println("Change   : " + TextFormatter.money(change));
        System.out.println("================================");
    }

    public void saveToFile(String filename) {
        StringBuilder sb = new StringBuilder();
        sb.append("========== SUPER SHOP ==========").append(System.lineSeparator());
        sb.append("Time: ").append(time).append(System.lineSeparator());
        sb.append("Customer: ").append(customer.getName()).append(" (").append(customer.getPhone()).append(")").append(System.lineSeparator());
        sb.append("Items:").append(System.lineSeparator());
        for (CartItem ci : cart.items()) {
            sb.append(ci.getProduct().getName())
              .append(" x")
              .append(ci.getQty())
              .append(" = ")
              .append(TextFormatter.money(ci.lineTotal()))
              .append(System.lineSeparator());
        }
        sb.append("Subtotal: ").append(TextFormatter.money(subtotal)).append(System.lineSeparator());
        sb.append("VAT: ").append(TextFormatter.money(vat)).append(System.lineSeparator());
        sb.append("Discount: ").append(TextFormatter.money(discount)).append(System.lineSeparator());
        sb.append("Total: ").append(TextFormatter.money(total)).append(System.lineSeparator());
        sb.append("Paid: ").append(TextFormatter.money(paid)).append(System.lineSeparator());
        sb.append("Change: ").append(TextFormatter.money(change)).append(System.lineSeparator());
        sb.append("================================").append(System.lineSeparator()).append(System.lineSeparator());

        try {
            Files.writeString(
                Path.of(filename),
                sb.toString(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.out.println("Could not save sales history.");
        }
    }

    public static void showSalesHistory(String filename) {
        try {
            Path p = Path.of(filename);
            if (!Files.exists(p)) {
                System.out.println("No sales history found.");
                return;
            }
            String content = Files.readString(p);
            if (content.isBlank()) {
                System.out.println("No sales history found.");
                return;
            }
            System.out.println();
            System.out.println("========== SALES HISTORY ==========");
            System.out.println(content);
        } catch (IOException e) {
            System.out.println("Could not read sales history.");
        }
    }
}