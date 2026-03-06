import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Customer {
    private final String name;
    private final String phone;

    public Customer(String name, String phone) {
        this.name = name == null ? "" : name;
        this.phone = phone == null ? "" : phone;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }

    public static boolean register(String filename, String name, String phone, String password) {
        if (name == null || name.isBlank()) return false;
        if (phone == null || phone.isBlank()) return false;
        if (password == null || password.isBlank()) return false;

        String n = sanitize(name.trim());
        String ph = sanitize(phone.trim());
        String pw = sanitize(password);

        if (ph.isEmpty() || pw.isEmpty() || n.isEmpty()) return false;

        if (existsPhone(filename, ph)) return false;

        String record = n + "|" + ph + "|" + pw + System.lineSeparator();

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

    public static Customer login(String filename, String phone, String password) {
        if (phone == null || phone.isBlank()) return null;
        if (password == null || password.isBlank()) return null;

        String ph = sanitize(phone.trim());
        String pw = sanitize(password);

        try {
            Path p = Path.of(filename);
            if (!Files.exists(p)) return null;
            List<String> lines = Files.readAllLines(p);
            for (String line : lines) {
                String[] parts = split3(line);
                if (parts == null) continue;
                if (parts[1].equals(ph) && parts[2].equals(pw)) {
                    return new Customer(parts[0], parts[1]);
                }
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private static boolean existsPhone(String filename, String phone) {
        try {
            Path p = Path.of(filename);
            if (!Files.exists(p)) return false;
            List<String> lines = Files.readAllLines(p);
            for (String line : lines) {
                String[] parts = split3(line);
                if (parts == null) continue;
                if (parts[1].equals(phone)) return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private static String sanitize(String s) {
        if (s == null) return "";
        return s.replace("|", "").replace("\n", "").replace("\r", "");
    }

    private static String[] split3(String line) {
        if (line == null) return null;
        String t = line.trim();
        if (t.isEmpty()) return null;
        String[] parts = t.split("\\|", -1);
        if (parts.length != 3) return null;
        for (int i = 0; i < 3; i++) {
            if (parts[i] == null) parts[i] = "";
            parts[i] = parts[i].trim();
        }
        if (parts[0].isEmpty() || parts[1].isEmpty() || parts[2].isEmpty()) return null;
        return parts;
    }
}