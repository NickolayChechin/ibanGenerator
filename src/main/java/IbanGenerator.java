public class IbanGenerator {

    private static final long MAX_ACCOUNT_NUMBER = 9999999999L;
    private static final long MAX_BANK_CODE = 99999999;
    private static final String DEFAULT_CHECK_DIGITS = "00";
    private static final int MOD = 97;

    private long accountNumbersCounter = 1L;
    private long bankCodesCounter = 1;

    private static final IbanGenerator INSTANCE = new IbanGenerator();

    private IbanGenerator() {
    }

    public static IbanGenerator getInstance(){
        return INSTANCE;
    }

    public Iban getIban(CountryIbanFormat countryIbanFormat) {
        Iban iban = new Iban();
        iban.setCountryIbanFormat(countryIbanFormat);
        BankCodeAccountNumber bankCodeAccountNumber = generateBankCodeAccountNumber(countryIbanFormat.getBankCodeLength(), countryIbanFormat.getAccountNumberLength());
        iban.setBankCode(bankCodeAccountNumber.getBankCode());
        iban.setAccountNumber(bankCodeAccountNumber.getAccountNumber());
        iban.setCheckDigits(DEFAULT_CHECK_DIGITS);
        String checkDigits = calculateCheckDigits(iban.getRawValue());
        iban.setCheckDigits(checkDigits);
        return iban;
    }

    private String calculateCheckDigits(String ibanRawValue) {
        int mod = calculateMod(ibanRawValue);
        int checkDigit = 98 - mod;
        String checkDigitString = Integer.valueOf(checkDigit).toString();
        return checkDigit > 9 ? checkDigitString : "0" + checkDigitString;

    }

    public boolean isValid(Iban iban) {
        boolean lengthIsValid = iban.toString().length() == iban.getCountryIbanFormat().getFullLenght();
        boolean checkNumbersIsValid = calculateMod(iban.getRawValue()) == 1;
        return lengthIsValid && checkNumbersIsValid;
    }

    private int calculateMod(String ibanRawValue) {

        int N = Integer.parseInt(ibanRawValue.substring(0, 9));
        int mod = N % MOD;

        String restIbanRawValue = ibanRawValue.substring(9);
        while (restIbanRawValue.length() >= 7) {
            N = Integer.parseInt(mod + restIbanRawValue.substring(0, 7));
            mod = N % MOD;
            restIbanRawValue = restIbanRawValue.substring(7);
        }

        if (restIbanRawValue.length() > 0) {
            N = Integer.parseInt(mod + restIbanRawValue);
            mod = N % MOD;
        }

        return mod;
    }

    private BankCodeAccountNumber generateBankCodeAccountNumber(int bankCodeLength, int accountNumberLength) {

        long accountNumber;
        if (accountNumbersCounter < MAX_ACCOUNT_NUMBER) {
            accountNumber = accountNumbersCounter;
            accountNumbersCounter++;
        } else if (bankCodesCounter < MAX_BANK_CODE) {
            accountNumbersCounter = 1L;
            accountNumber = accountNumbersCounter;
            bankCodesCounter++;
        } else {
            throw new RuntimeException("All possible Ibans have been generated");
        }
        long bankCode = bankCodesCounter;

        String stringAccountNumber = fillWithZeros(accountNumberLength, accountNumber);
        String stringBankCode = fillWithZeros(bankCodeLength, bankCode);

        return new BankCodeAccountNumber(stringBankCode, stringAccountNumber);
    }

    private String fillWithZeros(int numberLength, long number) {
        StringBuilder sb = new StringBuilder("");
        sb.append(number);
        for (int i = 0; i < numberLength - String.valueOf(number).length(); i++) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    private static class BankCodeAccountNumber {

        private String bankCode;
        private String accountNumber;

        public BankCodeAccountNumber(String bankCode, String accountNumber) {
            this.bankCode = bankCode;
            this.accountNumber = accountNumber;
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

}
