package core.validators;

import core.exceptions.ServerErrorException;
import vezh_bank.enums.Role;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static vezh_bank.constants.ExceptionMessages.CAN_NOT_READ_PROPERTIES;

public abstract class Validator { //TODO: get logging better
    protected Map<String, String> requestParams;
    protected DataBaseService dataBaseService;
    protected Properties properties;

    protected final String LETTERS_ONLY = "[a-z, A-Z, -]";
    protected final String LETTERS_AND_NUMBERS = "[a-z, A-Z, 0-9, -]";

    protected void loadProperties() throws ServerErrorException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("vezh_bank.properties");
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ServerErrorException(CAN_NOT_READ_PROPERTIES);
        }
    }

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

    protected boolean valueLengthValid(int minLength, int maxLength, String value) {
        if (value.length() < minLength || value.length() > maxLength) {
            return false;
        }
        return true;
    }
}
