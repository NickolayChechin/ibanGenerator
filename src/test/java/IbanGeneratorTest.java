import org.junit.Test;

import static org.junit.Assert.*;

public class IbanGeneratorTest {

    @Test
    public void ibanValidationFalse(){
        IbanGenerator ibanGenerator = IbanGenerator.getInstance();
        Iban iban = new Iban();
        iban.setCountryIbanFormat(CountryIbanFormat.DE);
        iban.setBankCode("50010518");
        iban.setAccountNumber("0123456787");
        iban.setCheckDigits("42");
        assertFalse(ibanGenerator.isValid(iban));
    }

    @Test
    public void ibanValidationTrue(){
        IbanGenerator ibanGenerator = IbanGenerator.getInstance();
        Iban iban = new Iban();
        iban.setCountryIbanFormat(CountryIbanFormat.DE);
        iban.setBankCode("50010517");
        iban.setAccountNumber("0123456789");
        iban.setCheckDigits("41");
        assertTrue(ibanGenerator.isValid(iban));
    }

    @Test
    public void getATIban(){
        IbanGenerator ibanGenerator = IbanGenerator.getInstance();
        Iban atIban = ibanGenerator.getIban(CountryIbanFormat.AT);
        assertTrue(ibanGenerator.isValid(atIban));
    }

    @Test
    public void getDEIban(){
        IbanGenerator ibanGenerator = IbanGenerator.getInstance();
        Iban deIban = ibanGenerator.getIban(CountryIbanFormat.DE);
        assertTrue(ibanGenerator.isValid(deIban));
    }

    @Test
    public void getNLIban(){
        IbanGenerator ibanGenerator = IbanGenerator.getInstance();
        Iban deIban = ibanGenerator.getIban(CountryIbanFormat.NL);
        assertTrue(ibanGenerator.isValid(deIban));
    }
}