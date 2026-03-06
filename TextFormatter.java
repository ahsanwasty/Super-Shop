import java.text.DecimalFormat;

public class TextFormatter {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static String money(double v) {
        return df.format(v);
    }

    public static String padRight(String s, int w) {
        if (s == null) s = "";
        if (s.length() >= w) return s;
        return s + " ".repeat(w - s.length());
    }
}