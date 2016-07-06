import java.util.concurrent.atomic.AtomicLong;

public class IbanGenerator {

    private AtomicLong accountNumbersCounter = new AtomicLong(1L);
    private AtomicLong bankCodesCounter = new AtomicLong(1L);

    private static final IbanGenerator INSTANCE = new IbanGenerator();

    private IbanGenerator() {
    }

    public static IbanGenerator getInstance() {
        return INSTANCE;
    }

    public Iban getIban(CountryIbanFormat countryIbanFormat) {
        BankCodeAccountNumber bankCodeAccountNumber = generateBankCodeAccountNumber(countryIbanFormat.getBankCodeLength(), countryIbanFormat.getBankCodeFormat(), countryIbanFormat.getAccountNumberLength());
        Iban iban = new Iban(countryIbanFormat, bankCodeAccountNumber.getBankCode(), bankCodeAccountNumber.getAccountNumber());
        return iban;
    }

    private BankCodeAccountNumber generateBankCodeAccountNumber(int bankCodeLength, CountryIbanFormat.BankCodeFormat bankCodeFormat, int accountNumberLength) {

        long accountNumber;
        long maxAccountNumber = getMaxValue(accountNumberLength);
        long maxBankNumber;
        if (bankCodeFormat == CountryIbanFormat.BankCodeFormat.a) {
            String getMaxUpperCaseCharValue = getMaxUpperCaseCharValue(bankCodeLength);
            maxBankNumber = fromBase26(getMaxUpperCaseCharValue);
        } else {
            maxBankNumber = getMaxValue(bankCodeLength);
        }

        if (accountNumbersCounter.get() <= maxAccountNumber) {
            accountNumber = accountNumbersCounter.getAndIncrement();
        } else if (bankCodesCounter.get() <= maxBankNumber) {
            accountNumbersCounter = new AtomicLong(0L);
            accountNumber = accountNumbersCounter.getAndIncrement();
            bankCodesCounter.getAndIncrement();
        } else {
            throw new RuntimeException("All possible Ibans have been generated");
        }

        long bankCode = bankCodesCounter.get();
        String stringBankCode = "";
        if (bankCodeFormat == CountryIbanFormat.BankCodeFormat.a) {
            stringBankCode = toBase26(bankCode);
            stringBankCode = fillWithA(bankCodeLength, stringBankCode);
        } else if (bankCodeFormat == CountryIbanFormat.BankCodeFormat.n) {
            stringBankCode = fillWithZeros(bankCodeLength, bankCode);
        }

        String stringAccountNumber = fillWithZeros(accountNumberLength, accountNumber);


        return new BankCodeAccountNumber(stringBankCode, stringAccountNumber);
    }

    private long getMaxValue(int length) {
        if (length > 18) {
            throw new RuntimeException("bank code or account number length must be less or equal 18 digits");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append('9');
        }
        return Long.parseLong(sb.toString());
    }

    private String getMaxUpperCaseCharValue(int length) {
        if (length > 13) {
            throw new RuntimeException("bank code or account number length must be less or equal 18 digits");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append('Z');
        }
        return sb.toString();
    }

    private String fillWithA(int stringLength, String string) {
        StringBuilder sb = new StringBuilder("");
        sb.append(string);
        for (int i = 0; i < stringLength - String.valueOf(string).length(); i++) {
            sb.insert(0, 'A');
        }
        return sb.toString();
    }

    private String fillWithZeros(int numberLength, long number) {
        StringBuilder sb = new StringBuilder("");
        sb.append(number);
        for (int i = 0; i < numberLength - String.valueOf(number).length(); i++) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }


    /**
     * Converts a number to base 26
     * <a href="https://en.wikipedia.org/w/index.php?title=Hexavigesimal&oldid=578218059#Bijective_base-26">Bijective base-26</a>.
     */
    public String toBase26(long n) {
        StringBuilder ret = new StringBuilder();
        while (n > 0) {
            --n;
            ret.append((char) ('A' + n % 26));
            n /= 26;
        }
        // reverse the result, since its
        // digits are in the wrong order
        return ret.reverse().toString();
    }

    public long fromBase26(String number) {
        long n = 0;
        if (number != null && number.length() > 0) {
            n = (number.charAt(0) - 'A' + 1);
            for (int i = 1; i < number.length(); i++) {
                n *= 26;
                n += (number.charAt(i) - 'A' + 1);
            }
        }
        return n;
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
