package vezh_bank.persistence;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import vezh_bank.extended_tests.PersistenceTest;
import vezh_bank.persistence.entity.Currency;
import vezh_bank.persistence.providers.currency.CurrencySearchArgumentsProvider;

import java.util.List;

@Feature("Currency persistence")
@Link("https://github.com/vezhny/vezh-lab/issues/5")
public class CurrencyTests extends PersistenceTest {

    @Description("Insert currency test")
    @Test
    public void insertCurrencyTest() {
        testUtils.logTestStart("Insert currency test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        Currency currencyFromDb = dataBaseService.getCurrencyDao().getById(currencyCode);
        checkCurrency(currencyCode, currencyValue, currencyFromDb);
    }

    @Description("Update currency test")
    @Test
    public void updateCurrencyTest() {
        testUtils.logTestStart("Update currency test");
        int currencyCode = 643;
        String oldCurrencyValue = "RUR";
        String newCurrencyValue = "RUB";
        createCurrency(currencyCode, oldCurrencyValue);

        Currency currency = new Currency(currencyCode, newCurrencyValue);
        dataBaseService.getCurrencyDao().update(currency);

        currency = dataBaseService.getCurrencyDao().getById(currencyCode);
        checkCurrency(currencyCode, newCurrencyValue, currency);
    }

    @Description("Delete currency test")
    @Test
    public void deleteCurrencyTest() {
        testUtils.logTestStart("Delete currency test");
        Currency currency = new Currency(643, "RUB");
        dataBaseService.getCurrencyDao().insert(currency);

        dataBaseService.getCurrencyDao().delete(currency);
        checkCurrenciesCount(0, dataBaseService.getCurrencyDao().selectAll());
    }

    @Description("SelectCurrency by value test")
    @Test
    public void getCurrencyByValueTest() {
        testUtils.logTestStart("SelectCurrency by value test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        Currency currency = dataBaseService.getCurrencyDao().getByValue(currencyValue).get(0);
        checkCurrency(currencyCode, currencyValue, currency);
    }

    @Description("Delete currency by code test")
    @Test
    public void deleteCurrencyByCodeTest() {
        testUtils.logTestStart("Delete currency by code test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        dataBaseService.getCurrencyDao().delete(currencyCode);
        checkCurrenciesCount(0, dataBaseService.getCurrencyDao().selectAll());
    }

    @Description("Delete currency by value test")
    @Test
    public void deleteCurrencyByValueTest() {
        testUtils.logTestStart("Delete currency by value test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        dataBaseService.getCurrencyDao().delete(currencyValue);
        checkCurrenciesCount(0, dataBaseService.getCurrencyDao().selectAll());
    }

    @Description("Currency sorting test")
    @Test
    public void currencySortingTest() {
        testUtils.logTestStart("Currency sorting test");
        int currencyCode1 = 643;
        int currencyCode2 = 666;
        String currencyValue1 = "RUB";
        String currencyValue2 = "HEL";
        createCurrency(currencyCode1, currencyValue1);
        createCurrency(currencyCode2, currencyValue2);

        List<Currency> currencies = dataBaseService.getCurrencyDao().selectAll();
        checkCurrenciesCount(2, currencies);
        checkCurrency(currencyCode2, currencyValue2, currencies.get(0));
        checkCurrency(currencyCode1, currencyValue1, currencies.get(1));
    }

    @Description("Search currency by value test")
    @Test
    public void searchCurrencyByValueTest() {
        testUtils.logTestStart("Search currency by value test");
        int currencyCode1 = 643;
        int currencyCode2 = 642;
        String currencyValue1 = "RUB";
        String currencyValue2 = "RUR";
        createCurrency(currencyCode1, currencyValue1);
        createCurrency(currencyCode2, currencyValue2);

        List<Currency> currencies = dataBaseService.getCurrencyDao().getByValue("RU");
        checkCurrenciesCount(2, currencies);
    }

    @Description("Search currency where code: {0}, value: {1}")
    @ParameterizedTest
    @ArgumentsSource(CurrencySearchArgumentsProvider.class)
    public void searchCurrencyTest(String code, String value, int expectedCurrenciesCount) {
        testUtils.logTestStart("Search currency test");
        int currencyCode1 = 643;
        int currencyCode2 = 578;
        String currencyValue1 = "RUB";
        String currencyValue2 = "RUR";
        createCurrency(currencyCode1, currencyValue1);
        createCurrency(currencyCode2, currencyValue2);

        List<Currency> currencies = dataBaseService.getCurrencyDao().get(code, value);
        checkCurrenciesCount(expectedCurrenciesCount, currencies);
    }

    @Description("Currency count test")
    @Test
    public void currencyCountTest() {
        testUtils.logTestStart("Currency count test");
        createCurrency(643, "RUB");

        int numberOfCurrencies = dataBaseService.getCurrencyDao().selectCount();
        Assertions.assertEquals(1, numberOfCurrencies, "Number of currencies");
    }

    @Description("Currency page test. Required page: {0}, rows on page: {1}, code: {2}, value: {3}")
    @ParameterizedTest
    @ArgumentsSource(CurrencySearchArgumentsProvider.CurrencyPageArgumentsProvider.class)
    public void currencyPageTest(int requiredPage, int rowsOfPage, String code, String value, int expectedCurrenciesCount) {
        testUtils.logTestStart("Currency page test");
        createCurrency(643, "RUB");
        createCurrency(981, "USD");
        createCurrency(435, "EUR");
        createCurrency(193, "UAN");
        createCurrency(457, "FUN");
        createCurrency(853, "BYN");

        List<Currency> currencies = dataBaseService.getCurrencyDao().select(requiredPage, rowsOfPage, code, value);
        checkCurrenciesCount(expectedCurrenciesCount, currencies);
    }

    @Description("Search currencies count test. Code: {0}, value: {1}")
    @ParameterizedTest
    @ArgumentsSource(CurrencySearchArgumentsProvider.class)
    public void selectCount(String code, String value, int expectedCurrenciesCount) {
        testUtils.logTestStart("Search currencies count test");
        int currencyCode1 = 643;
        int currencyCode2 = 578;
        String currencyValue1 = "RUB";
        String currencyValue2 = "RUR";
        createCurrency(currencyCode1, currencyValue1);
        createCurrency(currencyCode2, currencyValue2);

        int currencies = dataBaseService.getCurrencyDao().selectCount(code, value);
        Assertions.assertEquals(expectedCurrenciesCount, currencies, "Number of currencies");
    }
}
