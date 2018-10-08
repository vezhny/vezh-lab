package core.dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import vezh_bank.persistence.entity.Currency;

public class CurrencyDTO implements BaseDTO<Currency> {
    @Expose
    private int code;

    @Expose
    private String value;

    public CurrencyDTO(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public CurrencyDTO(Currency currency) {
        this.code = currency.getCode();
        this.value = currency.getValue();
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Currency getEntity() {
        Currency currency = new Currency(code, value);
        return currency;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
