package core.validators;

public abstract class Validator {

    protected boolean isStringCanBeNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean isNull(Object value) {
        if (value == null) {
            return true;
        }
        return false;
    }
}
