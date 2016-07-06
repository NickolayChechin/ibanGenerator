ibanGenerator is a thread-safe utility for generating test IBANs. The IBANs uniqueness is guaranteed within a specific instance. The utility supports IBAN generation for Germany, Austria and Netherlands. The list of countries can be easily extended by adding new CountryIbanFormat instances.

####Usage example:

Add dependency:
```
    <dependency>
        <groupId>com.chechin</groupId>
        <artifactId>ibanGenerator</artifactId>
        <version>1.0</version>
    </dependency>
```
Code sample:
```java
    //get generator instance
    IbanGenerator ibanGenerator = IbanGenerator.getInstance();
    //get Germany IBAN using CountryIbanFormat.DE
    //it's need to use CountryIbanFormat.AT and CountryIbanFormat.NL to get Austria and Netherlands IBANs respectively
    Iban iban = ibanGenerator.getIban(CountryIbanFormat.DE);
    //String representatoin
    iban.toString(); //DE44000000010000000001
```
