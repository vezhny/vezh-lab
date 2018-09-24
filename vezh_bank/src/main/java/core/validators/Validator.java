package core.validators;

import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import vezh_bank.constants.RequestParams;
import vezh_bank.enums.Role;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static java.lang.String.format;
import static vezh_bank.constants.ExceptionMessages.*;
import static vezh_bank.util.TypeConverter.stringToInt;

public abstract class Validator {
    protected Map<String, String> requestParams;
    protected DataBaseService dataBaseService;
    protected Properties properties;

    private final String LETTERS_ONLY = "1234567890!@#$%^&*()+=`~[]{};:'\"\\|/?.,<>_";
    private final String LETTERS_AND_NUMBERS = "!@#$%^&*()+=`~[]{};:'\"\\|/?.,<>_";

    protected void loadProperties() throws ServerErrorException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("vezh_bank.properties");
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ServerErrorException(CAN_NOT_READ_PROPERTIES);
        }
    }

    protected boolean hasLettersOnly(String value) {
        return checkStringContains(value, LETTERS_ONLY);
    }

    protected boolean hasOnlyLettersAndNumbers(String value) {
        return checkStringContains(value, LETTERS_AND_NUMBERS);
    }

    private boolean checkStringContains(String value, String containingChars) {
        for (Character character : containingChars.toCharArray()) {
            if (value.contains(character.toString())) {
                return false;
            }
        }
        return true;
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

    protected void checkUserId(String userId) throws BadRequestException {
        if (isNull(userId)) {
            throw new BadRequestException(USER_ID_MUST_PRESENT, format(PARAMETER_IS_NULL, RequestParams.USER_ID));
        }
        if (!isStringCanBeNumber(userId)) {
            throw new BadRequestException(format(VALUE_CAN_NOT_BE_A_NUMBER, userId));
        }
        User user = dataBaseService.getUserDao().getById(stringToInt(userId));
        if (isNull(user)) {
            throw new BadRequestException(format(USER_DOES_NOT_EXIST, userId));
        }
        if (isClient(user)) {
            throw new BadRequestException(THIS_OPERATION_IS_NOT_AVAILABLE_FOR_CLIENTS);
        }
    }
}
