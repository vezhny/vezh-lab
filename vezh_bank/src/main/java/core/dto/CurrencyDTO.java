package core.dto;

import vezh_bank.persistence.entity.Currency;

public class CurrencyDTO implements BaseDTO<Currency> {
    private int code;
    private String value;

    @Override
    public Currency getEntity() {
        return null;
    }
}
