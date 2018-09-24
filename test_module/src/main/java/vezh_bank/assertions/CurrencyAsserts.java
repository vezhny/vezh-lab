package vezh_bank.assertions;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import vezh_bank.persistence.entity.Currency;

import java.util.List;

public class CurrencyAsserts extends Asserts {

    @Step("Check currency")
    public void checkCurrency(int expectedCode, String expectedValue,
                              Currency actualCurrency) {
        checkObject(expectedCode, actualCurrency.getCode(),
                "Currency code");
        checkObject(expectedValue, actualCurrency.getValue(),
                "Currency value");
    }

    public void checkCurrenciesCount(int expectedCount, List<Currency> actualCurrencyList) {
        checkItemsCount(expectedCount, actualCurrencyList, "Currencies count");
    }
}
