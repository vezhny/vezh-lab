package core.enums;

public enum CardBrands {
    MAESTRO("MAESTRO"),
    MASTERCARD("MASTERCARD"),
    AMERICAN_EXPRESS("AMERICAN EXPRESS"),
    VISA("VISA");

    private String name;

    CardBrands(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
