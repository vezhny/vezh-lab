package core.validators;

import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import vezh_bank.constants.DatePatterns;
import vezh_bank.constants.RequestParams;
import vezh_bank.enums.Role;
import vezh_bank.enums.UserAccess;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.User;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static vezh_bank.constants.ExceptionMessages.*;
import static vezh_bank.constants.Properties.*;
import static vezh_bank.util.TypeConverter.stringToInt;

public class UserRequestValidator extends Validator {

    public UserRequestValidator(DataBaseService dataBaseService, Map<String, String> requestParams) throws ServerErrorException {
        this.dataBaseService = dataBaseService;
        this.requestParams = requestParams;
        loadProperties();
    }

    public UserRequestValidator(DataBaseService dataBaseService) throws ServerErrorException {
        this.dataBaseService = dataBaseService;
        loadProperties();
    }

    public void checkUpdateUserParams() throws BadRequestException {
        checkUserId(requestParams.get(RequestParams.USER_ID), UserAccess.ANY);
        try {
            checkUserId(requestParams.get(RequestParams.UPDATING_USER_ID), UserAccess.ANY);
        } catch (BadRequestException e) {
            if (e.getMessage().contains(RequestParams.USER_ID)){
                throw new BadRequestException(e.getMessage().replaceAll(RequestParams.USER_ID,
                        RequestParams.UPDATING_USER_ID), e.getDetail().replaceAll(RequestParams.USER_ID,
                        RequestParams.UPDATING_USER_ID));
            }
            throw new BadRequestException(e.getMessage(), e.getDetail());
        }
        checkPassword(requestParams.get(RequestParams.PASSWORD));
        checkUserData();
        checkUserConfig();
        checkAdminAndOwnerAccess(requestParams.get(RequestParams.USER_ID), requestParams.get(RequestParams.UPDATING_USER_ID));
    }

    public void checkGetUserParams() throws BadRequestException {
        checkUserId(requestParams.get(RequestParams.USER_ID), UserAccess.ANY);
        checkUserId(requestParams.get(RequestParams.TARGET_ID), UserAccess.ANY, RequestParams.TARGET_ID);
        checkAdminAndOwnerAccess(requestParams.get(RequestParams.USER_ID), requestParams.get(RequestParams.TARGET_ID));
    }

    private void checkAdminAndOwnerAccess(String userId, String targetId) throws BadRequestException {
        User operator = dataBaseService.getUserDao().getById(stringToInt(userId));
        User victim = dataBaseService.getUserDao().getById(stringToInt(targetId));
        if (!operator.getRole().equals(Role.ADMIN.toString()) && operator.getId() != victim.getId()) {
            throw new BadRequestException(ACCESS_DENIED, userTriedToDoOperationWithAccess(operator.getLogin(),
                    operator.getRole(), UserAccess.ADMIN_ONLY));
        }
    }

    private void checkUserConfig() throws BadRequestException {
        checkConfigParam(requestParams.get(RequestParams.CARDS_ON_PAGE), RequestParams.CARDS_ON_PAGE);
        checkConfigParam(requestParams.get(RequestParams.CURRENCIES_ON_PAGE), RequestParams.CURRENCIES_ON_PAGE);
        checkConfigParam(requestParams.get(RequestParams.EVENTS_ON_PAGE), RequestParams.EVENTS_ON_PAGE);
        checkConfigParam(requestParams.get(RequestParams.PAYMENTS_ON_PAGE), RequestParams.PAYMENTS_ON_PAGE);
        checkConfigParam(requestParams.get(RequestParams.TRANSACTIONS_ON_PAGE), RequestParams.TRANSACTIONS_ON_PAGE);
        checkConfigParam(requestParams.get(RequestParams.USERS_ON_PAGE), RequestParams.USERS_ON_PAGE);
        checkConfigParam(requestParams.get(RequestParams.USER_REQUESTS_ON_PAGE), RequestParams.USER_REQUESTS_ON_PAGE);
    }

    private void checkConfigParam(String param, String paramName) throws BadRequestException {
        if (isNull(param)) {
            throw new BadRequestException(missingParameter(paramName));
        }
        if (!isStringCanBeNumber(param)) {
            throw new BadRequestException(invalidParameter(paramName), valueCanNotBeANumber(param));
        }
        int number = stringToInt(param);
        int minRowsCount = stringToInt(properties.getProperty(USER_ROWS_MIN_COUNT));
        int maxRowsCount = stringToInt(properties.getProperty(USER_ROWS_MAX_COUNT));
        if (number < minRowsCount || number > maxRowsCount) {
            throw new BadRequestException(invalidParameter(paramName),
                    valueShouldBeInRange(paramName, minRowsCount, maxRowsCount));
        }
    }

    public void checkDeletingUser() throws BadRequestException {
        String userId = requestParams.get(RequestParams.DELETING_USER_ID);
        checkUserId(userId, UserAccess.ANY, RequestParams.DELETING_USER_ID);

        User user = dataBaseService.getUserDao().getById(stringToInt(userId));
        if (userId.equals(requestParams.get(RequestParams.USER_ID))) {
            throw new BadRequestException(YOU_CAN_NOT_DELETE_YOURSELF);
        }
        if (user.getCards().size() > 0) {
            throw new BadRequestException(userHasGotCards(user.getLogin(), user.getCards().size()));
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
        if (!isNull(requestParams.get(RequestParams.USER_ID))) {
            checkUserId(requestParams.get(RequestParams.USER_ID), UserAccess.ADMIN_ONLY);
        }
    }

    private void checkRole(String role) throws BadRequestException {
        if (isNull(role)) {
            throw new BadRequestException(missingParameter(RequestParams.ROLE));
        }

        if (!role.equals(Role.CLIENT.toString()) && !role.equals(Role.EMPLOYEE.toString())
                && !role.equals(Role.ADMIN.toString())) {
            throw new BadRequestException(invalidParameter(RequestParams.ROLE), roleIsUnknown(role));
        }
    }

    public void checkLogin(String login) throws BadRequestException {
        checkLogin(login, true);
    }

    private void checkLogin(String login, boolean shouldBeUnique) throws BadRequestException {
        if (isNull(login)) {
            throw new BadRequestException(missingParameter(RequestParams.LOGIN));
        }

        checkValueLength(USER_LOGIN_MIN_LENGTH, USER_NAME_MAX_LENGTH, login, RequestParams.LOGIN);

        if (!login.matches("\\w+")) {
            throw new BadRequestException(invalidParameter(RequestParams.LOGIN),
                    valueDoesNotMatchToRegex(login, "\\w+"));
        }

        if (shouldBeUnique && !dataBaseService.getUserDao().isLoginUnique(login)) {
            throw new BadRequestException(userWithLoginAlreadyRegistered(login));
        }
    }

    private void checkPassword(String password) throws BadRequestException {
        if (isNull(password)) {
            throw new BadRequestException(missingParameter(RequestParams.PASSWORD));
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
            throw new BadRequestException(missingParameter(paramName));
        }

        checkValueLength(USER_NAME_MIN_LENGTH, USER_NAME_MAX_LENGTH, name, paramName);

        if (!hasLettersOnly(name)) {
            throw new BadRequestException(invalidParameter(paramName));
        }
    }

    private void checkBirthDate(String date) throws BadRequestException {
        if (isNull(date)) {
            throw new BadRequestException(missingParameter(RequestParams.BIRTH_DATE));
        }
        try {
            LocalDate birthDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DatePatterns.DEFAULT_DATE_PATTERN));

            if (Period.between(birthDate, LocalDate.now()).getYears() < 18) {
                throw new BadRequestException(invalidParameter(RequestParams.BIRTH_DATE));
            }
        } catch (DateTimeParseException e) {
            throw new BadRequestException(invalidParameter(RequestParams.BIRTH_DATE),
                    dateHasInvalidPattern(date));
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
            throw new BadRequestException(missingParameter(RequestParams.COUNTRY));
        }

        checkValueLength(USER_ADDRESS_COUNTRY_MIN_LENGTH, USER_ADDRESS_COUNTRY_MAX_LENGTH, country, RequestParams.COUNTRY);

        if (!hasLettersOnly(country)) {
            throw new BadRequestException(invalidParameter(RequestParams.COUNTRY));
        }
    }

    private void checkRegion() throws BadRequestException {
        String region = requestParams.get(RequestParams.REGION);

        if (isNull(region)) {
            throw new BadRequestException(missingParameter(RequestParams.REGION));
        }

        checkValueLength(USER_ADDRESS_REGION_MIN_LENGTH, USER_ADDRESS_REGION_MAX_LENGTH, region, RequestParams.REGION);

        if (!hasOnlyLettersAndNumbers(region)) {
            throw new BadRequestException(invalidParameter(RequestParams.REGION));
        }
    }

    private void checkCity() throws BadRequestException {
        String city = requestParams.get(RequestParams.CITY);

        if (isNull(city)) {
            throw new BadRequestException(missingParameter(RequestParams.CITY));
        }

        checkValueLength(USER_ADDRESS_CITY_MIN_LENGTH, USER_ADDRESS_CITY_MAX_LENGTH, city, RequestParams.CITY);

        if (!hasOnlyLettersAndNumbers(city)) {
            throw new BadRequestException(invalidParameter(RequestParams.CITY));
        }
    }

    private void checkStreet() throws BadRequestException {
        String street = requestParams.get(RequestParams.STREET);

        if (isNull(street)) {
            throw new BadRequestException(missingParameter(RequestParams.STREET));
        }

        checkValueLength(USER_ADDRESS_STREET_MIN_LENGTH, USER_ADDRESS_STREET_MAX_LENGTH, street, RequestParams.STREET);
    }

    private void checkAddressNumberValue(String value, String paramName) throws BadRequestException {
        if (isNull(value)) {
            throw new BadRequestException(missingParameter(paramName));
        }

        if (!isStringCanBeNumber(value)) {
            throw new BadRequestException(invalidParameter(paramName), valueCanNotBeANumber(value));
        }

        if (stringToInt(value) <= 0) {
            throw new BadRequestException(invalidParameter(paramName));
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
            throw new BadRequestException(missingParameter(RequestParams.CONTACT_NUMBER));
        }

        if (!number.matches("\\+\\d+")) {
            throw new BadRequestException(invalidParameter(RequestParams.CONTACT_NUMBER));
        }

        checkValueLength(USER_CONTACT_NUMBER_MIN_LENGTH, USER_CONTACT_NUMBER_MAX_LENGTH,
                number.replaceAll("\\+", ""), RequestParams.CONTACT_NUMBER);
    }

    private void checkEmail(String email) throws BadRequestException {
        if (!isNull(email)) {
            if (!email.matches("\\w+@\\w+\\.\\w+")) {
                throw new BadRequestException(invalidParameter(RequestParams.EMAIL));
            }
        }
    }
}
