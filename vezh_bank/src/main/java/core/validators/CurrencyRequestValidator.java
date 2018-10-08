package core.validators;

import core.exceptions.BadRequestException;
import core.exceptions.ServerErrorException;
import vezh_bank.constants.RequestParams;
import vezh_bank.persistence.DataBaseService;

import java.util.Map;

import static vezh_bank.constants.ExceptionMessages.*;
import static vezh_bank.util.TypeConverter.stringToInt;

public class CurrencyRequestValidator extends Validator {

    public CurrencyRequestValidator(DataBaseService dataBaseService, Map<String, String> requestParams) throws ServerErrorException {
        this.dataBaseService = dataBaseService;
        this.requestParams = requestParams;
        loadProperties();
    }

    public void checkAddCurrencyParams() {

    }

    private void checkCurrencyCode(String code) throws BadRequestException {
        if (isNull(code)) {
            throw new BadRequestException(missingParameter(RequestParams.CURRENCY_CODE));
        }
        if (!isStringCanBeNumber(code)) {
            throw new BadRequestException(invalidParameter(RequestParams.CURRENCY_CODE), valueCanNotBeANumber(code));
        }
        if (code.length() != 3) {
            throw new BadRequestException(invalidParameter(RequestParams.CURRENCY_CODE),
                    valueShouldHaveLength(RequestParams.CURRENCY_CODE, 3, code.length()));
        }
        if (dataBaseService.getCurrencyDao().getById(stringToInt(code)) != null) {
            throw new BadRequestException(currencyWithCodeIsAlreadyExists(code));
        }
    }
}
