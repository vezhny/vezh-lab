package core.validators;

import core.exceptions.BadRequestException;
import vezh_bank.constants.RequestParams;
import vezh_bank.persistence.DataBaseService;

import java.util.Map;

import static vezh_bank.constants.ExceptionMessages.*;
import static vezh_bank.util.TypeConverter.stringToInt;

public class EventRequestValidator extends Validator {
    private Map<String, String> requestParams;
    private DataBaseService dataBaseService;

    public EventRequestValidator(Map<String, String> requestParams, DataBaseService dataBaseService) {
        this.requestParams = requestParams;
        this.dataBaseService = dataBaseService;
    }

    public void checkRequestParams() throws BadRequestException {
        checkUserId(requestParams.get(RequestParams.USER_ID));
    }

    private void checkUserId(String userId) throws BadRequestException {
        if (isNull(userId)) {
            throw new BadRequestException(USER_ID_MUST_PRESENT);
        }
        if (!isStringCanBeNumber(userId)) {
            throw new BadRequestException(String.format(VALUE_CAN_NOT_BE_A_NUMBER, userId));
        }
        if (isNull(dataBaseService.getUserDao().getById(stringToInt(userId)))) {
            throw new BadRequestException(String.format(USER_DOES_NOT_EXIST, userId));
        }
    }
}
