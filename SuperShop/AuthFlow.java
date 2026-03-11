/**
 * Handles user interactions for registration and login.
 *
 * Fixes SRP: auth flow was previously embedded in App.
 */
public class AuthFlow {
    private final AuthService authService;
    private final Input input;

    public AuthFlow(AuthService authService, Input input) {
        this.authService = authService;
        this.input       = input;
    }

    public void register() {
        String name     = input.readLine("Name: ");
        String phone    = input.readLine("Phone: ");
        String password = input.readLine("Password: ");
        boolean ok = authService.register(name, phone, password);
        if (!ok) {
            System.out.println("Registration failed. Phone may already exist or invalid input.");
            return;
        }
        System.out.println("Registration successful. Now login.");
    }

    /** @return the logged-in Customer, or null on failure. */
    public Customer login() {
        String phone    = input.readLine("Phone: ");
        String password = input.readLine("Password: ");
        Customer c = authService.login(phone, password);
        if (c == null) {
            System.out.println("Login failed.");
            return null;
        }
        System.out.println("Logged in as: " + c.getName() + " (" + c.getPhone() + ")");
        return c;
    }
}
