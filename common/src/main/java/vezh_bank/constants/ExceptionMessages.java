package vezh_bank.constants;

public class ExceptionMessages {
    public static final String USER_ID_MUST_PRESENT = "User ID must present";
    public static final String VALUE_CAN_NOT_BE_A_NUMBER = "Value \"%s\" can't be a number";
    public static final String USER_DOES_NOT_EXIST = "User with ID \"%s\" doesn't exist";
    public static final String THIS_OPERATION_IS_NOT_AVAILABLE_FOR_CLIENTS = "This operation is not allowed for clients";
    public static final String MISSING_REQUEST_TYPE_PARAMETER = "Missing request type parameter";
    public static final String MISSING_PARAMETER = "Missing %s parameter";
    public static final String CAN_NOT_READ_PROPERTIES = "Can't read property file";
    public static final String PARAMETER_SHOULD_HAVE_LENGTH = "Parameter \"%s\" should have length [%s, %s]"; //TODO: add static methods for format
    public static final String INVALID_PARAMETER = "Invalid parameter: \"%s\"";
}
