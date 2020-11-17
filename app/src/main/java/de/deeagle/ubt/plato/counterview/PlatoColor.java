package de.deeagle.ubt.plato.counterview;

public enum PlatoColor {
    LOCATION_STATE_OPEN("#28a745");

    public final String strCode;

    private PlatoColor(String strCode) {
        this.strCode = strCode;
    }

    public String getStrCode() {
        return strCode;
    }
}
