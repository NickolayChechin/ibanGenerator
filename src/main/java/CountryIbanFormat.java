public enum CountryIbanFormat {
    DE("Germany", 8, BankCodeFormat.n, 10),
    AT("Austria", 5, BankCodeFormat.n, 11),
    NL("Netherlands", 4, BankCodeFormat.a, 10);

    static enum BankCodeFormat {
        /**digits*/
        n,
        /**upper case characters*/
        a;
    }

    private String description;
    private int bankCodeLength;
    private BankCodeFormat bankCodeFormat;
    private int accountNumberLength;
    private int fullLenght;

    CountryIbanFormat(String description, int bankCodeLength, BankCodeFormat bankCodeFormat, int accountNumberLength) {
        this.description = description;
        this.bankCodeLength = bankCodeLength;
        this.bankCodeFormat = bankCodeFormat;
        this.accountNumberLength = accountNumberLength;
        /**4 is for 2 country letters an 2 check digits*/
        this.fullLenght = 4 + bankCodeLength + accountNumberLength;
    }

    public String getDescription() {
        return description;
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

    public BankCodeFormat getBankCodeFormat() {
        return bankCodeFormat;
    }
}
