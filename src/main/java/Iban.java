public class Iban {

    private CountryIbanFormat countryIbanFormat;
    private String checkDigits;
    private String bankCode;
    private String accountNumber;

    private static final String DEFAULT_CHECK_DIGITS = "00";

    public Iban() {
    }

    public Iban(CountryIbanFormat countryIbanFormat, String bankCode, String accountNumber) {
        this.countryIbanFormat = countryIbanFormat;
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.checkDigits = DEFAULT_CHECK_DIGITS;
        this.checkDigits = calculateCheckDigits(getRawValue());
    }

    private String calculateCheckDigits(String ibanRawValue) {
        String numericIbanRawValue = IbanUtils.replaceCharsWithNumbers(ibanRawValue);
        int mod = IbanUtils.calculateMod(numericIbanRawValue);
        int checkDigit = 98 - mod;
        String checkDigitString = Integer.valueOf(checkDigit).toString();
        return checkDigit > 9 ? checkDigitString : "0" + checkDigitString;

    }

    public String getRawValue() {
        StringBuilder sb = new StringBuilder();
        sb.append(bankCode);
        sb.append(accountNumber);
        sb.append(countryIbanFormat);
        sb.append(checkDigits);
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(countryIbanFormat);
        sb.append(checkDigits);
        sb.append(bankCode);
        sb.append(accountNumber);
        return sb.toString();
    }

    public CountryIbanFormat getCountryIbanFormat() {
        return countryIbanFormat;
    }

    public void setCountryIbanFormat(CountryIbanFormat countryIbanFormat) {
        this.countryIbanFormat = countryIbanFormat;
    }

    public String getCheckDigits() {
        return checkDigits;
    }

    public void setCheckDigits(String checkDigits) {
        this.checkDigits = checkDigits;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Iban iban = (Iban) o;

        if (countryIbanFormat != iban.countryIbanFormat) return false;
        if (checkDigits != null ? !checkDigits.equals(iban.checkDigits) : iban.checkDigits != null) return false;
        if (bankCode != null ? !bankCode.equals(iban.bankCode) : iban.bankCode != null) return false;
        return accountNumber != null ? accountNumber.equals(iban.accountNumber) : iban.accountNumber == null;

    }

    @Override
    public int hashCode() {
        int result = countryIbanFormat != null ? countryIbanFormat.hashCode() : 0;
        result = 31 * result + (checkDigits != null ? checkDigits.hashCode() : 0);
        result = 31 * result + (bankCode != null ? bankCode.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        return result;
    }
}
