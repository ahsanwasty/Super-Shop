/**
 * Handles customer registration and authentication.
 *
 * Fixes SRP: auth logic is no longer a static method on the Customer data class.
 * Fixes DIP: depends on ICustomerRepository abstraction, not on file details.
 */
public class AuthService {
    private final ICustomerRepository repository;

    public AuthService(ICustomerRepository repository) {
        this.repository = repository;
    }

    public boolean register(String name, String phone, String password) {
        if (name == null || name.isBlank()) return false;
        if (phone == null || phone.isBlank()) return false;
        if (password == null || password.isBlank()) return false;

        String n  = sanitize(name.trim());
        String ph = sanitize(phone.trim());
        String pw = sanitize(password);

        if (n.isEmpty() || ph.isEmpty() || pw.isEmpty()) return false;
        if (repository.exists(ph)) return false;

        return repository.save(n, ph, pw);
    }

    public Customer login(String phone, String password) {
        if (phone == null || phone.isBlank()) return null;
        if (password == null || password.isBlank()) return null;

        String ph = sanitize(phone.trim());
        String pw = sanitize(password);

        return repository.findByCredentials(ph, pw);
    }

    private static String sanitize(String s) {
        if (s == null) return "";
        return s.replace("|", "").replace("\n", "").replace("\r", "");
    }
}
