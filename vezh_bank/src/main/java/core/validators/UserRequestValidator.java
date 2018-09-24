package core.validators;

import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import vezh_bank.constants.DatePatterns;
import vezh_bank.constants.RequestParams;
import vezh_bank.enums.Role;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.User;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static java.lang.String.format;
import static vezh_bank.constants.ExceptionMessages.*;
import static vezh_bank.constants.Properties.*;
import static vezh_bank.util.TypeConverter.stringToInt;

public class UserRequestValidator extends Validator {

    public UserRequestValidator(DataBaseService dataBaseService, Map<String, String> requestParams) throws ServerErrorException {
        this.dataBaseService = dataBaseService;
        this.requestParams = requestParams;
        loadProperties();
    }

    public void checkRequestTypePresents() throws BadRequestException {
        if (isNull(requestParams.get(RequestParams.REQUEST_TYPE))) {
            throw new BadRequestException(MISSING_REQUEST_TYPE_PARAMETER);
        }
    }

    public void checkUserRegistrationParams() throws BadRequestException {
        checkLogin(requestParams.get(RequestParams.LOGIN));
        checkPassword(requestParams.get(RequestParams.PASSWORD));
        checkRole(requestParams.get(RequestParams.ROLE));
        checkUserData();
    }

    private void checkUserAccess() throws BadRequestException { //TODO: user access tests
        if (!isNull(RequestParams.USER_ID)) {
            checkUserId(requestParams.get(RequestParams.USER_ID));
        }
    }

    @Override
    protected void checkUserId(String userId) throws BadRequestException {
        if (isNull(userId)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.USER_ID));
        }
        if (!isStringCanBeNumber(userId)) {
            throw new BadRequestException(String.format(VALUE_CAN_NOT_BE_A_NUMBER, userId));
        }
        User user = dataBaseService.getUserDao().getById(stringToInt(userId));
        if (isNull(user)) {
            throw new BadRequestException(String.format(USER_DOES_NOT_EXIST, userId));
        }
        if (!user.getRole().equals(requestParams.get(RequestParams.ROLE))) {
            throw new BadRequestException(THIS_OPERATION_IS_NOT_AVAILABLE_FOR_CLIENTS);
        }
    }

    private void checkRole(String role) throws BadRequestException {
        if (isNull(role)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.ROLE));
        }

        if (!role.equals(Role.CLIENT.toString()) && !role.equals(Role.EMPLOYEE.toString())
                && !role.equals(Role.ADMIN.toString())) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.ROLE), format(ROLE_IS_UNKNOWN, role));
        }
    }

    private void checkLogin(String login) throws BadRequestException {
        if (isNull(login)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.LOGIN));
        }

        int minLength = stringToInt(properties.getProperty(USER_LOGIN_MIN_LENGTH));
        int maxLength = stringToInt(properties.getProperty(USER_LOGIN_MAX_LENGTH));
        if (!valueLengthValid(minLength, maxLength, login)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.LOGIN),
                    format(VALUE_SHOULD_HAVE_LENGTH, RequestParams.LOGIN, minLength, maxLength, login.length()));
        }

        if (!login.matches("\\w+")) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.LOGIN),
                    format(VALUE_DOES_NOT_MATCH_TO_REGEX, login, "\\w+"));
        }

        if (!dataBaseService.getUserDao().isLoginUnique(login)) {
            throw new BadRequestException(format(USER_WITH_LOGIN_IS_ALREADY_REGISTERED, login));
        }
    }

    private void checkPassword(String password) throws BadRequestException {
        if (isNull(password)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.PASSWORD));
        }

        int minLength = stringToInt(properties.getProperty(USER_PASSWORD_MIN_LENGTH));
        int maxLength = stringToInt(properties.getProperty(USER_PASSWORD_MAX_LENGTH));
        if (!valueLengthValid(minLength, maxLength, password)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.PASSWORD),
                    format(VALUE_SHOULD_HAVE_LENGTH, RequestParams.PASSWORD, minLength, maxLength, password.length()));
        }
    }

    private void checkUserData() throws BadRequestException {
        checkName(requestParams.get(RequestParams.FIRST_NAME), RequestParams.FIRST_NAME);
        checkName(requestParams.get(RequestParams.MIDDLE_NAME), RequestParams.MIDDLE_NAME);
        checkName(requestParams.get(RequestParams.PATRONYMIC), RequestParams.PATRONYMIC);
        checkBirthDate(requestParams.get(RequestParams.BIRTH_DATE));
        checkAddress();
        checkContactNumber(requestParams.get(RequestParams.CONTACT_NUMBER));
        checkEmail(requestParams.get(RequestParams.EMAIL));
    }

    private void checkName(String name, String paramName) throws BadRequestException {
        if (isNull(name)) {
            throw new BadRequestException(format(MISSING_PARAMETER,
                    paramName));
        }

        int minLength = stringToInt(properties.getProperty(USER_NAME_MIN_LENGTH)); // TODO: make it in one place
        int maxLength = stringToInt(properties.getProperty(USER_NAME_MAX_LENGTH));
        if (!valueLengthValid(minLength, maxLength, name)) {
            throw new BadRequestException(format(INVALID_PARAMETER, paramName),
                    format(VALUE_SHOULD_HAVE_LENGTH, paramName, minLength, maxLength, name.length()));
        }

        if (!hasLettersOnly(name)) {
            throw new BadRequestException(format(INVALID_PARAMETER, paramName));
        }
    }

    private void checkBirthDate(String date) throws BadRequestException {
        if (isNull(date)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.BIRTH_DATE));
        }
        try {
            LocalDate birthDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DatePatterns.DEFAULT_DATE_PATTERN));

            if (Period.between(birthDate, LocalDate.now()).getYears() < 18) {
                throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.BIRTH_DATE));
            }
        } catch (DateTimeParseException e) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.BIRTH_DATE),
                    format(DATE_HAS_INVALID_PATTERN, date));
        }
    }

    private void checkAddress() throws BadRequestException {
        checkCountry();
        checkRegion();
        checkCity();
        checkStreet();
        checkAddressNumberValue(requestParams.get(RequestParams.HOUSE), RequestParams.HOUSE);
        checkHousePart();
        checkAddressNumberValue(requestParams.get(RequestParams.ROOM), RequestParams.ROOM);
    }

    private void checkCountry() throws BadRequestException {
        String country = requestParams.get(RequestParams.COUNTRY);

        if (isNull(country)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.COUNTRY));
        }

        int minLength = stringToInt(properties.getProperty(USER_ADDRESS_COUNTRY_MIN_LENGTH));
        int maxLength = stringToInt(properties.getProperty(USER_ADDRESS_COUNTRY_MAX_LENGTH));
        if (!valueLengthValid(minLength, maxLength, country)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.COUNTRY),
                    format(VALUE_SHOULD_HAVE_LENGTH, RequestParams.COUNTRY, minLength, maxLength, country.length()));
        }

        if (!hasLettersOnly(country)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.COUNTRY));
        }
    }

    private void checkRegion() throws BadRequestException {
        String region = requestParams.get(RequestParams.REGION);

        if (isNull(region)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.REGION));
        }

        int minLength = stringToInt(properties.getProperty(USER_ADDRESS_REGION_MIN_LENGTH));
        int maxLength = stringToInt(properties.getProperty(USER_ADDRESS_REGION_MAX_LENGTH));
        if (!valueLengthValid(minLength, maxLength, region)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.REGION),
                    format(VALUE_SHOULD_HAVE_LENGTH, RequestParams.REGION, minLength, maxLength, region.length()));
        }

        if (!hasOnlyLettersAndNumbers(region)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.REGION));
        }
    }

    private void checkCity() throws BadRequestException {
        String city = requestParams.get(RequestParams.CITY);

        if (isNull(city)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.CITY));
        }

        int minLength = stringToInt(properties.getProperty(USER_ADDRESS_CITY_MIN_LENGTH));
        int maxLength = stringToInt(properties.getProperty(USER_ADDRESS_CITY_MAX_LENGTH));
        if (!valueLengthValid(minLength, maxLength, city)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.CITY),
                    format(VALUE_SHOULD_HAVE_LENGTH, RequestParams.CITY, minLength, maxLength, city.length()));
        }

        if (!hasOnlyLettersAndNumbers(city)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.CITY));
        }
    }

    private void checkStreet() throws BadRequestException {
        String street = requestParams.get(RequestParams.STREET);

        if (isNull(street)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.STREET));
        }

        int minLength = stringToInt(properties.getProperty(USER_ADDRESS_STREET_MIN_LENGTH));
        int maxLength = stringToInt(properties.getProperty(USER_ADDRESS_STREET_MAX_LENGTH));
        if (!valueLengthValid(minLength, maxLength, street)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.STREET),
                    format(VALUE_SHOULD_HAVE_LENGTH, RequestParams.STREET, minLength, maxLength, street.length()));
        }
    }

    private void checkAddressNumberValue(String value, String paramName) throws BadRequestException {
        if (isNull(value)) {
            throw new BadRequestException(format(MISSING_PARAMETER, paramName));
        }

        if (!isStringCanBeNumber(value)) {
            throw new BadRequestException(format(INVALID_PARAMETER, paramName), format(VALUE_CAN_NOT_BE_A_NUMBER, value));
        }

        if (stringToInt(value) <= 0) {
            throw new BadRequestException(format(INVALID_PARAMETER, paramName));
        }
    }

    private void checkHousePart() throws BadRequestException {
        String housePart = requestParams.get(RequestParams.HOUSE_PART);
        if (!isNull(housePart)) {
            checkAddressNumberValue(housePart, RequestParams.HOUSE_PART);
        }
    }

    private void checkContactNumber(String number) throws BadRequestException {
        if (isNull(number)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.CONTACT_NUMBER));
        }

        if (!number.matches("\\+\\d+")) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.CONTACT_NUMBER));
        }

        int minLength = stringToInt(properties.getProperty(USER_CONTACT_NUMBER_MIN_LENGTH));
        int maxLength = stringToInt(properties.getProperty(USER_CONTACT_NUMBER_MAX_LENGTH));
        if (!valueLengthValid(minLength, maxLength, number.replaceAll("\\+", ""))) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.CONTACT_NUMBER),
                    format(VALUE_SHOULD_HAVE_LENGTH, RequestParams.CONTACT_NUMBER, minLength, maxLength, number.length()));
        }
    }

    private void checkEmail(String email) throws BadRequestException {
        if (!isNull(email)) {
            if (!email.matches("\\w+@\\w+\\.\\w+")) {
                throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.EMAIL));
            }
        }
    }
}
