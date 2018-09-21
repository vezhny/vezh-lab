package core.validators;

import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import vezh_bank.constants.DatePatterns;
import vezh_bank.constants.RequestParams;
import vezh_bank.persistence.DataBaseService;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static java.lang.String.format;
import static vezh_bank.constants.ExceptionMessages.*;
import static vezh_bank.constants.Properties.USER_NAME_MAX_LENGTH;
import static vezh_bank.constants.Properties.USER_NAME_MIN_LENGTH;
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
        checkName(requestParams.get(RequestParams.FIRST_NAME), RequestParams.FIRST_NAME);
        checkName(requestParams.get(RequestParams.MIDDLE_NAME), RequestParams.MIDDLE_NAME);
        checkName(requestParams.get(RequestParams.PATRONYMIC), RequestParams.PATRONYMIC);
        checkBirthDate(requestParams.get(RequestParams.BIRTH_DATE));
    }

    private void checkName(String name, String paramName) throws BadRequestException {
        if (isNull(name)) {
            throw new BadRequestException(format(MISSING_PARAMETER,
                    paramName));
        }

        int minLength = stringToInt(properties.getProperty(USER_NAME_MIN_LENGTH));
        int maxLength = stringToInt(properties.getProperty(USER_NAME_MAX_LENGTH));
        if (name.length() < minLength || name.length() > maxLength) {
            throw new BadRequestException(format(PARAMETER_SHOULD_HAVE_LENGTH, paramName, minLength, maxLength));
        }

        if (!name.matches(LETTERS_ONLY)) {
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
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.BIRTH_DATE));
        }
    }

    private void checkAddress() {

    }

    private void checkCountry() throws BadRequestException {
        String country = requestParams.get(RequestParams.COUNTRY);

        if (isNull(country)) {
            throw new BadRequestException(format(INVALID_PARAMETER, RequestParams.COUNTRY));
        }
    }
}
