package vezh_bank.constants;

public class ExceptionMessages {
    public static final String USER_ID_MUST_PRESENT = "User ID must present";
    public static final String VALUE_CAN_NOT_BE_A_NUMBER = "Value \"%s\" can't be a number";
    public static final String USER_DOES_NOT_EXIST = "User with ID \"%s\" doesn't exist";
    public static final String THIS_OPERATION_IS_NOT_AVAILABLE_FOR_CLIENTS = "This operation is not allowed for clients";
    public static final String MISSING_REQUEST_TYPE_PARAMETER = "Missing request type parameter";
    public static final String MISSING_PARAMETER = "Missing parameter \"%s\"";
    public static final String CAN_NOT_READ_PROPERTIES = "Can't read property file";
    //TODO: add static methods for format
    public static final String INVALID_PARAMETER = "Invalid parameter: \"%s\"";
    public static final String USER_WITH_LOGIN_IS_ALREADY_REGISTERED = "User with login \"%s\" already registered";
    public static final String PARAMETER_IS_NULL = "Parameter \"%s\" is null";
    public static final String ROLE_IS_UNKNOWN = "Role \"%s\" is unknown";
    public static final String VALUE_SHOULD_HAVE_LENGTH = "Value \"%s\" should have length [%s, %s], " +
            "but actual length is \"%s\"";
    public static final String VALUE_DOES_NOT_MATCH_TO_REGEX = "Value \"%s\" doesn't match regex \"%s\"";
    public static final String DATE_HAS_INVALID_PATTERN = "Date \"%s\" has invalid pattern";
}
