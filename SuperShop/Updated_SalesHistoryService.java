/**
 * Handles displaying sales history to the user.
 *
 * Fixes SRP: this was previously a static method on Receipt, which mixed
 * data/print responsibility with storage reading.
 * Fixes DIP: depends on ISalesRepository, not on file details.
 */
public class SalesHistoryService {
    private final ISalesRepository salesRepository;

    public SalesHistoryService(ISalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public void show() {
        String content = salesRepository.loadAll();
        if (content == null) {
            System.out.println("No sales history found.");
            return;
        }
        System.out.println();
        System.out.println("========== SALES HISTORY ==========");
        System.out.println(content);
    }
}
