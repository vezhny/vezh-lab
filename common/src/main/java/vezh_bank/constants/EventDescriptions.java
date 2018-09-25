package vezh_bank.constants;

public class EventDescriptions {
    public static final String REGISTERED_USER_WITH_LOGIN = "Registered user with login: %s";

    public static String registeredUserWithLogin(String login) {
        return String.format(REGISTERED_USER_WITH_LOGIN, login);
    }
}
