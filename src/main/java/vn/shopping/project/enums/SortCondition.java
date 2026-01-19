package vn.shopping.project.enums;

public enum SortCondition {
    NAME_FROM_Z_TO_A("za"),
    PRICE_FROM_LOW_TO_HIGH("lohi"),
    PRICE_FROM_HIGH_TO_LOW("hilo");

    private final String dropdownText;

    SortCondition(String dropdownText) {
        this.dropdownText = dropdownText;
    }

    public String getDropdownText() {
        return dropdownText;
    }
}
