import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class IbanGeneratorTest {

    @Test
    public void calculateMod(){
        int mod =IbanUtils.calculateMod("3214282912345698765432161182");
        assertEquals(mod, 1);
    }
    @Test
    public void replaceCharsWithNumbers(){
        String numericValue = IbanUtils.replaceCharsWithNumbers("WEST12345698765432GB82");
        assertEquals(numericValue, "3214282912345698765432161182");
    }

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
        List<Thread> threadList = new ArrayList<Thread>();
        for (int i = 0; i < 5; i++) {
            IbanGeneratorTest.IbanGeneratorTester tester = new IbanGeneratorTest.IbanGeneratorTester(ibanList);
            Thread thread = new Thread(tester);
            thread.start();
            threadList.add(thread);
        }
        for (Thread thread : threadList) {
            thread.join();
        }

        assertFalse(doesListContainDuplicates(ibanList));
        assertEquals(ibanList.size(), 5000);
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