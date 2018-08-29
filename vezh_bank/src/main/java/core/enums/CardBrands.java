package core.enums;

public enum CardBrands {
    // TODO: pan ranges and image urls
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
