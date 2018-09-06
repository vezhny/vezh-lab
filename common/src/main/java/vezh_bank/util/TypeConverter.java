package vezh_bank.util;

public class TypeConverter {
    public static int booleanToInt(boolean is) {
        if (is) {
            return 1;
        }
        return 0;
    }

    public static int stringToInt(String value, int exceptionValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return exceptionValue;
        }
    }

    public static int stringToInt(String value) {
        return stringToInt(value, 0);
    }
}
