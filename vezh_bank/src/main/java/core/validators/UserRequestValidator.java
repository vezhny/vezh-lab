package core.validators;

import core.exceptions.BadRequestException;
import vezh_bank.constants.ExceptionMessages;
import vezh_bank.constants.RequestParams;
import vezh_bank.persistence.DataBaseService;

import java.util.Map;

import static vezh_bank.constants.ExceptionMessages.MISSING_REQUEST_TYPE_PARAMETER;

public class UserRequestValidator extends Validator {

    public UserRequestValidator(DataBaseService dataBaseService, Map<String, String> requestParams) {
        this.dataBaseService = dataBaseService;
        this.requestParams = requestParams;
    }

    public void checkRequestTypePresents() throws BadRequestException {
        if (isNull(requestParams.get(RequestParams.REQUEST_TYPE))) {
            throw new BadRequestException(MISSING_REQUEST_TYPE_PARAMETER);
        }
    }

    public void checkUserRegistrationParams() {

    }

    private void checkName(String name, String paramName) throws BadRequestException {
        if (isNull(name)) {
            throw new BadRequestException(String.format(ExceptionMessages.MISSING_PARAMETER,
                    paramName));
        }
    }
}
