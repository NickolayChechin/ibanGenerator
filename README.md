ibanGenerator is a utility for generating test IBANs. It supports IBAN generation for Germany, Austria and Netherlands. The list of countries can be easily extended by adding new CountryIbanFormat instances.

####Usage example:
```java
        //get generator instance
        IbanGenerator ibanGenerator = IbanGenerator.getInstance();
        //get Germany IBAN using CountryIbanFormat.DE
        //to get Austria and Netherlands IBANs it's need to use CountryIbanFormat.AT and CountryIbanFormat.NL respectively
        Iban iban = ibanGenerator.getIban(CountryIbanFormat.DE);
        //String representatoin
        iban.toString(); //DE44000000010000000001
```
