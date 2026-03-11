import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * File-based implementation of ISalesRepository.
 *
 * Fixes SRP: Receipt no longer owns file I/O — this class does.
 * Fixes DIP: CheckoutService depends on ISalesRepository, not this class.
 */
public class FileSalesRepository implements ISalesRepository {
    private final String filename;

    public FileSalesRepository(String filename) {
        this.filename = filename;
    }

    @Override
    public void save(String record) {
        try {
            Files.writeString(
                Path.of(filename),
                record,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.out.println("Could not save sales history.");
        }
    }

    @Override
    public String loadAll() {
        try {
            Path p = Path.of(filename);
            if (!Files.exists(p)) return null;
            String content = Files.readString(p);
            return content.isBlank() ? null : content;
        } catch (IOException e) {
            return null;
        }
    }
}
