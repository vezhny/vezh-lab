package vezh_bank.constants;

import static java.lang.String.format;

public class EventDescriptions {
    public static final String REGISTERED_USER_WITH_LOGIN = "Registered user with login: %s";
    public static final String USER_DELETED_USER_WITH_LOGIN = "User \"%s\" (%s) deleted another user \"%s\" (%s)";

    public static String registeredUserWithLogin(String login) {
        return format(REGISTERED_USER_WITH_LOGIN, login);
    }

    public static String userDeletedUser(String deleterLogin, String deleterRole, String victimLogin, String victimRole) {
        return format(USER_DELETED_USER_WITH_LOGIN, deleterLogin, deleterRole, victimLogin, victimRole);
    }
}
