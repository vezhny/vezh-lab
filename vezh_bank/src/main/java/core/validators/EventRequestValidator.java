package core.validators;

import core.exceptions.BadRequestException;
import vezh_bank.constants.RequestParams;
import vezh_bank.persistence.DataBaseService;

import java.util.Map;

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
}