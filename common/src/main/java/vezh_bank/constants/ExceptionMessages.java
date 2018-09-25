package vezh_bank.constants;

import static java.lang.String.format;

public class ExceptionMessages {
    public static final String USER_ID_MUST_PRESENT = "User ID must present";
    public static final String VALUE_CAN_NOT_BE_A_NUMBER = "Value \"%s\" can't be a number";
    public static final String USER_DOES_NOT_EXIST = "User with ID \"%s\" doesn't exist";
    public static final String THIS_OPERATION_IS_NOT_AVAILABLE_FOR_CLIENTS = "This operation is not allowed for clients";
    public static final String MISSING_REQUEST_TYPE_PARAMETER = "Missing request type parameter";
    public static final String MISSING_PARAMETER = "Missing parameter \"%s\"";
    public static final String CAN_NOT_READ_PROPERTIES = "Can't read property file";
    public static final String INVALID_PARAMETER = "Invalid parameter: \"%s\"";
    public static final String USER_WITH_LOGIN_IS_ALREADY_REGISTERED = "User with login \"%s\" already registered";
    public static final String PARAMETER_IS_NULL = "Parameter \"%s\" is null";
    public static final String ROLE_IS_UNKNOWN = "Role \"%s\" is unknown";
    public static final String VALUE_SHOULD_HAVE_LENGTH = "Value \"%s\" should have length [%s, %s], " +
            "but actual length is \"%s\"";
    public static final String VALUE_DOES_NOT_MATCH_TO_REGEX = "Value \"%s\" doesn't match regex \"%s\"";
    public static final String DATE_HAS_INVALID_PATTERN = "Date \"%s\" has invalid pattern";
    public static final String UNABLE_TO_DECRYPT_VALUE = "Unable to decrypt value \"%s\"";

    public static String valueCanNotBeANumber(String value) {
        return format(VALUE_CAN_NOT_BE_A_NUMBER, value);
    }

    public static String userDoesNotExist(String user) {
        return format(USER_DOES_NOT_EXIST, user);
    }

    public static String missingParameter(String paramName) {
        return format(MISSING_PARAMETER, paramName);
    }

    public static String invalidParameter(String paramName) {
        return format(INVALID_PARAMETER, paramName);
    }

    public static String userWithLoginAlreadyRegistered(String login) {
        return format(USER_WITH_LOGIN_IS_ALREADY_REGISTERED, login);
    }

    public static String parameterIsNull(String paramName) {
        return format(PARAMETER_IS_NULL, paramName);
    }

    public static String roleIsUnknown(String role) {
        return format(ROLE_IS_UNKNOWN, role);
    }

    public static String valueShouldHaveLength(String paramName, int minLength, int maxLength, int actualLength) {
        return format(VALUE_SHOULD_HAVE_LENGTH, paramName, minLength, maxLength, actualLength);
    }

    public static String valueDoesNotMatchToRegex(String value, String regex) {
        return format(VALUE_DOES_NOT_MATCH_TO_REGEX, value, regex);
    }

    public static String dateHasInvalidPattern(String date) {
        return format(DATE_HAS_INVALID_PATTERN, date);
    }

    public static String unableToDecryptValue(String value) {
        return format(UNABLE_TO_DECRYPT_VALUE, value);
    }
}
