package vezh_bank.persistence;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import vezh_bank.extended_tests.PersistenceTest;
import vezh_bank.persistence.entity.Currency;
import vezh_bank.persistence.providers.currency.CurrencySearchArgumentsProvider;

import java.util.List;

@Epic("Persistence")
@Story("Currency persistence")
@Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/5", url = "https://github.com/vezhny/vezh-lab/issues/5")
@Severity(SeverityLevel.BLOCKER)
public class CurrencyTests extends PersistenceTest {

    @Feature("Insert entity")
    @Description("Insert currency test")
    @Test
    public void insertCurrencyTest() {
        testUtils.logTestStart("Insert currency test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        Currency currencyFromDb = dataBaseService.getCurrencyDao().getById(currencyCode);
        currencyAsserts.checkCurrency(currencyCode, currencyValue, currencyFromDb);
    }

    @Feature("Update entity")
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
        currencyAsserts.checkCurrency(currencyCode, newCurrencyValue, currency);
    }

    @Feature("Delete entity")
    @Description("Delete currency test")
    @Test
    public void deleteCurrencyTest() {
        testUtils.logTestStart("Delete currency test");
        Currency currency = new Currency(643, "RUB");
        dataBaseService.getCurrencyDao().insert(currency);

        dataBaseService.getCurrencyDao().delete(currency);
        currencyAsserts.checkCurrenciesCount(0, dataBaseService.getCurrencyDao().selectAll());
    }

    @Feature("Select entity")
    @Description("Select currency by value test")
    @Test
    public void getCurrencyByValueTest() {
        testUtils.logTestStart("SelectCurrency by value test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        Currency currency = dataBaseService.getCurrencyDao().getByValue(currencyValue).get(0);
        currencyAsserts.checkCurrency(currencyCode, currencyValue, currency);
    }

    @Feature("Delete entity")
    @Description("Delete currency by code test")
    @Test
    public void deleteCurrencyByCodeTest() {
        testUtils.logTestStart("Delete currency by code test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        dataBaseService.getCurrencyDao().delete(currencyCode);
        currencyAsserts.checkCurrenciesCount(0, dataBaseService.getCurrencyDao().selectAll());
    }

    @Feature("Delete entity")
    @Description("Delete currency by value test")
    @Test
    public void deleteCurrencyByValueTest() {
        testUtils.logTestStart("Delete currency by value test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        dataBaseService.getCurrencyDao().delete(currencyValue);
        currencyAsserts.checkCurrenciesCount(0, dataBaseService.getCurrencyDao().selectAll());
    }

    @Severity(SeverityLevel.NORMAL)
    @Feature("Sort entity")
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
        currencyAsserts.checkCurrenciesCount(2, currencies);
        currencyAsserts.checkCurrency(currencyCode2, currencyValue2, currencies.get(0));
        currencyAsserts.checkCurrency(currencyCode1, currencyValue1, currencies.get(1));
    }

    @Feature("Select entity")
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
        currencyAsserts.checkCurrenciesCount(2, currencies);
    }

    @Feature("Select entity")
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
        currencyAsserts.checkCurrenciesCount(expectedCurrenciesCount, currencies);
    }

    @Feature("Select entity")
    @Description("Currency count test")
    @Test
    public void currencyCountTest() {
        testUtils.logTestStart("Currency count test");
        createCurrency(643, "RUB");

        int numberOfCurrencies = dataBaseService.getCurrencyDao().selectCount();
        asserts.checkObject(1, numberOfCurrencies, "Number of currencies");
    }

    @Feature("Select entity")
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
        currencyAsserts.checkCurrenciesCount(expectedCurrenciesCount, currencies);
    }

    @Feature("Select entity")
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
        asserts.checkObject(expectedCurrenciesCount, currencies, "Number of currencies");
    }
}
