package core.validators;

import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import vezh_bank.constants.RequestParams;
import vezh_bank.enums.Role;
import vezh_bank.enums.UserAccess;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

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
            Long.parseLong(value);
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

    public void checkUserId(String userId, UserAccess access) throws BadRequestException {
        checkUserId(userId, access, RequestParams.USER_ID);
    }

    public void checkUserId(String userId, UserAccess access, String userIdFieldName) throws BadRequestException {
        if (isNull(userId)) {
            throw new BadRequestException(missingParameter(userIdFieldName));
        }
        if (!isStringCanBeNumber(userId)) {
            throw new BadRequestException(invalidParameter(userIdFieldName), valueCanNotBeANumber(userId));
        }
        User user = dataBaseService.getUserDao().getById(stringToInt(userId));
        if (isNull(user)) {
            throw new BadRequestException(userDoesNotExist(userId));
        }
        if (!access.equals(UserAccess.ANY)) {
            checkUserAccess(user, access);
        }
    }

    private void checkUserAccess(User user, UserAccess access) throws BadRequestException {
        switch (access) {
            case EMPLOYEE_ONLY: {
                if (user.getRole().equals(Role.CLIENT.toString())) {
                    throw new BadRequestException(ACCESS_DENIED,
                            userTriedToDoOperationWithAccess(user.getLogin(), user.getRole(), access));
                }
                break;
            }
            case ADMIN_ONLY: {
                if (!user.getRole().equals(Role.ADMIN.toString())) {
                    throw new BadRequestException(ACCESS_DENIED,
                            userTriedToDoOperationWithAccess(user.getLogin(), user.getRole(), access));
                }
            }
        }
    }

    protected void checkValueLength(String propertyMinLengthName, String propertyMaxLengthName,
                                    String value, String paramName) throws BadRequestException {
        int minLength = stringToInt(properties.getProperty(propertyMinLengthName));
        int maxLength = stringToInt(properties.getProperty(propertyMaxLengthName));
        if (!valueLengthValid(minLength, maxLength, value)) {
            throw new BadRequestException(invalidParameter(paramName),
                    valueShouldHaveLength(paramName, minLength, maxLength, value.length()));
        }
    }
}
