public enum CountryIbanFormat {
    DE("Germany", "1314", 8, 10),
    AT("Austria", "1029", 5, 11);

    private String description;
    private String numberRepresentation;
    private int bankCodeLength;
    private int accountNumberLength;
    private int fullLenght;

    CountryIbanFormat(String description, String numberRepresentation, int bankCodeLength, int accountNumberLength) {
        this.description = description;
        this.numberRepresentation = numberRepresentation;
        this.bankCodeLength = bankCodeLength;
        this.accountNumberLength = accountNumberLength;
        /**4 is for 2 country letters an 2 check digits*/
        this.fullLenght = 4 + bankCodeLength + accountNumberLength;
    }

    public String getDescription() {
        return description;
    }

    public String getNumberRepresentation() {
        return numberRepresentation;
    }

    public int getBankCodeLength() {
        return bankCodeLength;
    }

    public int getAccountNumberLength() {
        return accountNumberLength;
    }

    public int getFullLenght() {
        return fullLenght;
    }
}
