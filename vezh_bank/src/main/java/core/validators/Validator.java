package core.validators;

import vezh_bank.enums.Role;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.User;

import java.util.Map;

public abstract class Validator {
    protected Map<String, String> requestParams;
    protected DataBaseService dataBaseService;

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

    protected boolean isClient(User user) {
        if (user.getRole().equals(Role.CLIENT.toString())) {
            return true;
        }
        return false;
    }
}
