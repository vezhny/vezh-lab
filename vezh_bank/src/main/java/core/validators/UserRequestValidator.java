package core.validators;

import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import vezh_bank.constants.DatePatterns;
import vezh_bank.constants.RequestParams;
import vezh_bank.enums.Role;
import vezh_bank.persistence.DataBaseService;

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
        checkUserAccess();
    }

    private void checkUserAccess() throws BadRequestException {
        if (!isNull(RequestParams.USER_ID)) {
            checkUserId(requestParams.get(RequestParams.USER_ID));
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

        checkValueLength(USER_LOGIN_MIN_LENGTH, USER_NAME_MAX_LENGTH, login, RequestParams.LOGIN);

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

        checkValueLength(USER_PASSWORD_MIN_LENGTH, USER_PASSWORD_MAX_LENGTH, password, RequestParams.PASSWORD);
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

        checkValueLength(USER_NAME_MIN_LENGTH, USER_NAME_MAX_LENGTH, name, paramName);

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

        checkValueLength(USER_ADDRESS_COUNTRY_MIN_LENGTH, USER_ADDRESS_COUNTRY_MAX_LENGTH, country, RequestParams.COUNTRY);

        if (!hasLettersOnly(country)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.COUNTRY));
        }
    }

    private void checkRegion() throws BadRequestException {
        String region = requestParams.get(RequestParams.REGION);

        if (isNull(region)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.REGION));
        }

        checkValueLength(USER_ADDRESS_REGION_MIN_LENGTH, USER_ADDRESS_REGION_MAX_LENGTH, region, RequestParams.REGION);

        if (!hasOnlyLettersAndNumbers(region)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.REGION));
        }
    }

    private void checkCity() throws BadRequestException {
        String city = requestParams.get(RequestParams.CITY);

        if (isNull(city)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.CITY));
        }

        checkValueLength(USER_ADDRESS_CITY_MIN_LENGTH, USER_ADDRESS_CITY_MAX_LENGTH, city, RequestParams.CITY);

        if (!hasOnlyLettersAndNumbers(city)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.CITY));
        }
    }

    private void checkStreet() throws BadRequestException {
        String street = requestParams.get(RequestParams.STREET);

        if (isNull(street)) {
            throw new BadRequestException(format(MISSING_PARAMETER, RequestParams.STREET));
        }

        checkValueLength(USER_ADDRESS_STREET_MIN_LENGTH, USER_ADDRESS_STREET_MAX_LENGTH, street, RequestParams.STREET);
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

        checkValueLength(USER_CONTACT_NUMBER_MIN_LENGTH, USER_CONTACT_NUMBER_MAX_LENGTH, number, RequestParams.CONTACT_NUMBER);
    }

    private void checkEmail(String email) throws BadRequestException {
        if (!isNull(email)) {
            if (!email.matches("\\w+@\\w+\\.\\w+")) {
                throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.EMAIL));
            }
        }
    }
}
