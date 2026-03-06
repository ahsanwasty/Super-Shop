public enum Category {
    GROCERY("Grocery"),
    HOUSEHOLD("Household"),
    SNACKS("Snacks");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}