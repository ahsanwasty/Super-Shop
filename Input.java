import java.util.Scanner;

public class Input {
    private final Scanner sc;

    public Input(Scanner sc) {
        this.sc = sc;
    }

    public int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter a valid number: ");
        }
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }

    public double readDouble(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            sc.next();
            System.out.print("Enter a valid amount: ");
        }
        double v = sc.nextDouble();
        sc.nextLine();
        return v;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}