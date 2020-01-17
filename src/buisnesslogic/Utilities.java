package buisnesslogic;

public class Utilities {

    public String isStringValid(String inPut) {
        boolean nameIsValid = true;
        String stringIsValid = "";
        while (nameIsValid) {
            char first;
            stringIsValid = inPut;
            if (stringIsValid.length() > 0) {
                first = stringIsValid.charAt(0);
                nameIsValid = false;
            } else {

            }
        }
        return stringIsValid;
    }

    public Long isLongValid(String pn) {
        Long pnIsValid = 0L;
        boolean isValid = true;
        while (isValid) {
            try {
                pnIsValid = Long.parseLong(pn);
                isValid = true;
            } catch (Exception e) {
                //
            }
        }
        return pnIsValid;
    }
}
