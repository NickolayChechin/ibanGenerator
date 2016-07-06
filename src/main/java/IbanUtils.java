/**
 * Utility class
 */
public class IbanUtils {

    private static final int DIV = 97;

    /**
     * Replaces characters with numbers where A is 10, B is 11, etc
     * @param ibanRawValue
     * @return numeric string
     */
    public static String replaceCharsWithNumbers(String ibanRawValue) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ibanRawValue.length(); i++) {
            int numericValue = Character.getNumericValue(ibanRawValue.charAt(i));
            sb.append(numericValue);
        }
        return sb.toString();
    }

    /**
     * Validates Iban
     * @param iban
     * @return true if Iban is valid
     */
    public static boolean isValid(Iban iban) {
        boolean lengthIsValid = iban.toString().length() == iban.getCountryIbanFormat().getFullLenght();
        boolean checkNumbersIsValid = calculateMod(replaceCharsWithNumbers(iban.getRawValue())) == 1;
        return lengthIsValid && checkNumbersIsValid;
    }

    /**
     * Calculates Iban's modulo
     * @param ibanRawValue
     * @return modulo
     */
    public static int calculateMod(String ibanRawValue) {

        if(ibanRawValue.length() < 9){
            return Integer.parseInt(ibanRawValue) % DIV;
        }

        int N = Integer.parseInt(ibanRawValue.substring(0, 9));
        int mod = N % DIV;

        String restIbanRawValue = ibanRawValue.substring(9);
        while (restIbanRawValue.length() >= 7) {
            N = Integer.parseInt(mod + restIbanRawValue.substring(0, 7));
            mod = N % DIV;
            restIbanRawValue = restIbanRawValue.substring(7);
        }

        if (restIbanRawValue.length() > 0) {
            N = Integer.parseInt(mod + restIbanRawValue);
            mod = N % DIV;
        }

        return mod;
    }

}
