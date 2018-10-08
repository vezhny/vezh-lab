package vezh_bank.constants;

import static java.lang.String.format;

public class EventDescriptions {
    public static final String REGISTERED_USER_WITH_LOGIN = "Registered user with login: %s";
    public static final String USER_DELETED_USER_WITH_LOGIN = "User \"%s\" (%s) deleted another user \"%s\" (%s)";
    public static final String USER_HAS_BEEN_UPDATED_BY = "User \"%s\" (%s) has been updated by \"%s\" (%s)";
    public static final String USER_SIGNED_IN = "User \"%s\" signed in";

    public static String registeredUserWithLogin(String login) {
        return format(REGISTERED_USER_WITH_LOGIN, login);
    }

    public static String userDeletedUser(String deleterLogin, String deleterRole, String victimLogin, String victimRole) {
        return format(USER_DELETED_USER_WITH_LOGIN, deleterLogin, deleterRole, victimLogin, victimRole);
    }

    public static String userHasBeenUpdatedBy(String updaterLogin, String updaterRole,
                                              String victimLogin, String victimRole) {
        return format(USER_HAS_BEEN_UPDATED_BY, updaterLogin, updaterRole, victimLogin, victimRole);
    }

    public static String userSignedIn(String login) {
        return format(USER_SIGNED_IN, login);
    }
}
