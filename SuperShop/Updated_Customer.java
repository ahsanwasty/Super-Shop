/**
 * Pure value object holding customer data.
 *
 * Fixes SRP: all file I/O (register/login) has moved to FileCustomerRepository
 * and AuthService. This class has exactly one reason to change: the customer
 * data model itself.
 */
public class Customer {
    private final String name;
    private final String phone;

    public Customer(String name, String phone) {
        this.name  = name  == null ? "" : name;
        this.phone = phone == null ? "" : phone;
    }

    public String getName()  { return name; }
    public String getPhone() { return phone; }
}
