package vezh_bank.constants;

import vezh_bank.enums.UserAccess;

import static java.lang.String.format;

public class ExceptionMessages {
    public static final String VALUE_CAN_NOT_BE_A_NUMBER = "Value \"%s\" can't be a number";
    public static final String USER_DOES_NOT_EXIST = "User with ID \"%s\" doesn't exist";
    public static final String MISSING_PARAMETER = "Missing parameter \"%s\"";
    public static final String CAN_NOT_READ_PROPERTIES = "Can't read property file";
    public static final String INVALID_PARAMETER = "Invalid parameter: \"%s\"";
    public static final String USER_WITH_LOGIN_IS_ALREADY_REGISTERED = "User with login \"%s\" already registered";
    public static final String PARAMETER_IS_NULL = "Parameter \"%s\" is null";
    public static final String ROLE_IS_UNKNOWN = "Role \"%s\" is unknown";
    public static final String VALUE_SHOULD_HAVE_LENGTH = "Value \"%s\" should have length [%s, %s], " +
            "but actual length is \"%s\"";
    public static final String VALUE_SHOULD_HAVE_LENGTH_NO_RANGE = "Value \"%s\" should have length \"s\", " +
            "but actual length is \"%s\"";
    public static final String VALUE_DOES_NOT_MATCH_TO_REGEX = "Value \"%s\" doesn't match regex \"%s\"";
    public static final String DATE_HAS_INVALID_PATTERN = "Date \"%s\" has invalid pattern";
    public static final String UNABLE_TO_DECRYPT_VALUE = "Unable to decrypt value \"%s\"";
    public static final String USER_HAS_GOT_CARDS = "User \"%s\" has got %s of cards";
    public static final String YOU_CAN_NOT_DELETE_YOURSELF = "You can't delete yourself";
    public static final String ACCESS_DENIED = "Access denied";
    public static final String USER_TRIED_TO_DO_OPERATON_WITH_ACCESS = "\"%s\" (%s) tried to do " +
            "operation with access \"%s\"";
    public static final String VALUE_SHOULD_BE_IN_RANGE = "Value \"%s\" should be in range [%s, %s]";
    public static final String INVALID_LOGIN_OR_PASSWORD = "Invalid login or password";
    public static final String USER_IS_BLOCKED = "User is blocked";
    public static final String USER_WITH_LOGIN_IS_BLOCKED = "User \"%s\" is blocked";
    public static final String CURRENCY_WITH_ALREADY_EXISTS = "Currency with %s \"%s\" is already exists";

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

    public static String valueShouldHaveLength(String paramName, int expectedLength, int actualLength) {
        return format(VALUE_SHOULD_HAVE_LENGTH_NO_RANGE, paramName, expectedLength, actualLength);
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

    public static String userHasGotCards(String user, int numberOfCards) {
        return format(USER_HAS_GOT_CARDS, user, numberOfCards);
    }

    public static String userTriedToDoOperationWithAccess(String login, String role, UserAccess access) {
        return format(USER_TRIED_TO_DO_OPERATON_WITH_ACCESS, login, role, access.toString());
    }

    public static String valueShouldBeInRange(String paramName, int minValue, int maxValue) {
        return format(VALUE_SHOULD_BE_IN_RANGE, paramName, minValue, maxValue);
    }

    public static String userIsBlocked(String login) {
        return format(USER_WITH_LOGIN_IS_BLOCKED, login);
    }

    public static String currencyWithCodeIsAlreadyExists(String code) {
        return format(CURRENCY_WITH_ALREADY_EXISTS, "code", code);
    }
}
