public class Iban {

    private CountryIbanFormat countryIbanFormat;
    private String checkDigits;
    private String bankCode;
    private String accountNumber;
    /**a value without check digits*/
    private String rawValue;

    public String getRawValue(){
        StringBuilder sb = new StringBuilder();
        sb.append(bankCode);
        sb.append(accountNumber);
        sb.append(countryIbanFormat);
        sb.append(checkDigits);
        return sb.toString();
    }

    @Override
    public String toString(){
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
}
