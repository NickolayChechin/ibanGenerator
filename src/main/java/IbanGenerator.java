import java.util.concurrent.atomic.AtomicLong;

public class IbanGenerator {

    private static final long MAX_ACCOUNT_NUMBER = 9999999999L;
    private static final long MAX_BANK_CODE = 99999999;

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
        if (accountNumbersCounter.get() < MAX_ACCOUNT_NUMBER) {
            accountNumber = accountNumbersCounter.getAndIncrement();
        } else if (bankCodesCounter.get() < MAX_BANK_CODE) {
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
    private String toBase26(long n) {
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
