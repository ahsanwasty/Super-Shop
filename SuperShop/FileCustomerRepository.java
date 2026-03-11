import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * File-based implementation of ICustomerRepository.
 *
 * Fixes SRP: Customer data class is now a pure value object; all file I/O lives here.
 * Fixes DIP: AuthService depends on ICustomerRepository, not this class.
 */
public class FileCustomerRepository implements ICustomerRepository {
    private final String filename;

    public FileCustomerRepository(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean exists(String phone) {
        try {
            Path p = Path.of(filename);
            if (!Files.exists(p)) return false;
            for (String line : Files.readAllLines(p)) {
                String[] parts = split3(line);
                if (parts != null && parts[1].equals(phone)) return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean save(String name, String phone, String password) {
        String record = name + "|" + phone + "|" + password + System.lineSeparator();
        try {
            Files.writeString(
                Path.of(filename),
                record,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Customer findByCredentials(String phone, String password) {
        try {
            Path p = Path.of(filename);
            if (!Files.exists(p)) return null;
            List<String> lines = Files.readAllLines(p);
            for (String line : lines) {
                String[] parts = split3(line);
                if (parts != null && parts[1].equals(phone) && parts[2].equals(password)) {
                    return new Customer(parts[0], parts[1]);
                }
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private static String[] split3(String line) {
        if (line == null) return null;
        String t = line.trim();
        if (t.isEmpty()) return null;
        String[] parts = t.split("\\|", -1);
        if (parts.length != 3) return null;
        for (int i = 0; i < 3; i++) {
            parts[i] = parts[i] == null ? "" : parts[i].trim();
        }
        if (parts[0].isEmpty() || parts[1].isEmpty() || parts[2].isEmpty()) return null;
        return parts;
    }
}
