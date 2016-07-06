import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class IbanGeneratorTest {

    @Test
    public void ibanValidationFalse() {
        Iban iban = new Iban();
        iban.setCountryIbanFormat(CountryIbanFormat.DE);
        iban.setBankCode("50010518");
        iban.setAccountNumber("0123456787");
        iban.setCheckDigits("42");
        assertFalse(IbanUtils.isValid(iban));
    }

    @Test
    public void ibanValidationTrue() {
        Iban iban = new Iban();
        iban.setCountryIbanFormat(CountryIbanFormat.DE);
        iban.setBankCode("50010517");
        iban.setAccountNumber("0123456789");
        iban.setCheckDigits("41");
        assertTrue(IbanUtils.isValid(iban));
    }

    @Test
    public void getATIban() {
        IbanGenerator ibanGenerator = IbanGenerator.getInstance();
        Iban atIban = ibanGenerator.getIban(CountryIbanFormat.AT);
        assertTrue(IbanUtils.isValid(atIban));
    }

    @Test
    public void getDEIban() {
        IbanGenerator ibanGenerator = IbanGenerator.getInstance();
        Iban deIban = ibanGenerator.getIban(CountryIbanFormat.DE);
        assertTrue(IbanUtils.isValid(deIban));
    }

    @Test
    public void getNLIban() {
        IbanGenerator ibanGenerator = IbanGenerator.getInstance();
        Iban nlIban = ibanGenerator.getIban(CountryIbanFormat.NL);
        assertTrue(IbanUtils.isValid(nlIban));
    }

    @Test
    public void multithreadingTest() throws InterruptedException {
        List<String> ibanList = Collections.synchronizedList(new ArrayList<String>());
        IbanGeneratorTest.IbanGeneratorTester tester1 = new IbanGeneratorTest.IbanGeneratorTester(ibanList);
        Thread thread1 = new Thread(tester1);
        IbanGeneratorTest.IbanGeneratorTester tester2 = new IbanGeneratorTest.IbanGeneratorTester(ibanList);
        Thread thread2 = new Thread(tester2);
        IbanGeneratorTest.IbanGeneratorTester tester3 = new IbanGeneratorTest.IbanGeneratorTester(ibanList);
        Thread thread3 = new Thread(tester3);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        assertFalse(doesListContainDuplicates(ibanList));
    }

    private static boolean doesListContainDuplicates(List<String> list) {
        Set<String> set = new HashSet<String>();
        for (String value : list) {
            if (!set.add(value)) {
                return true;
            }
        }
        return false;
    }

    private static class IbanGeneratorTester implements Runnable {

        private IbanGenerator ibanGenerator = IbanGenerator.getInstance();
        private List<String> ibanList;

        public IbanGeneratorTester(List<String> ibanList) {
            this.ibanList = ibanList;
        }

        public void run() {
            for (int i = 0; i < 1000; i++) {
                Iban iban = ibanGenerator.getIban(CountryIbanFormat.DE);
                ibanList.add(iban.toString());
            }
        }
    }
}