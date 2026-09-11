package com.globaldocs.factorymethod.model;

/**
 * Countries supported by GlobalDocs Solutions, each with its own
 * regulatory body and electronic document standards.
 */
public enum Country {
    COLOMBIA("Colombia", "CO", "🇨🇴", "DIAN · Resolution 000042 (Electronic Invoicing)"),
    MEXICO("Mexico", "MX", "🇲🇽", "SAT · CFDI 4.0"),
    ARGENTINA("Argentina", "AR", "🇦🇷", "AFIP · General Resolution 4291 (Electronic Receipts)"),
    CHILE("Chile", "CL", "🇨🇱", "SII · Electronic Tax Document (DTE)");

    private final String displayName;
    private final String isoCode;
    private final String flagEmoji;
    private final String regulation;

    Country(String displayName, String isoCode, String flagEmoji, String regulation) {
        this.displayName = displayName;
        this.isoCode = isoCode;
        this.flagEmoji = flagEmoji;
        this.regulation = regulation;
    }

    public String displayName() {
        return displayName;
    }

    public String isoCode() {
        return isoCode;
    }

    public String flagEmoji() {
        return flagEmoji;
    }

    public String regulation() {
        return regulation;
    }
}
