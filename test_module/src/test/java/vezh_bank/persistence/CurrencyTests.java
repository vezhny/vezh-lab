package vezh_bank.persistence;

import core.config.VezhBankConfiguration;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import vezh_bank.TestUtils;
import vezh_bank.constants.MavenProfiles;
import vezh_bank.persistence.entity.Currency;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = VezhBankConfiguration.class)
@WebAppConfiguration
@ActiveProfiles(MavenProfiles.TEST)
public class CurrencyTests {
    private Logger logger = Logger.getLogger(this.getClass());

    private TestUtils testUtils;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataBaseService dataBaseService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        testUtils = new TestUtils(logger);
    }

    @AfterEach
    public void tearDown() {
        testUtils.logTestEnd();
        dataBaseService.getCurrencyDao().deleteAll();
    }

    @Test
    public void insertCurrencyTest() {
        testUtils.logTestStart("Insert currency test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        Currency currencyFromDb = dataBaseService.getCurrencyDao().getById(currencyCode);
        checkCurrency(currencyCode, currencyValue, currencyFromDb);
    }

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

    @Test
    public void deleteCurrencyTest() {
        testUtils.logTestStart("Delete currency test");
        Currency currency = new Currency(643, "RUB");
        dataBaseService.getCurrencyDao().insert(currency);

        dataBaseService.getCurrencyDao().delete(currency);
        checkCurrenciesCount(0, dataBaseService.getCurrencyDao().selectAll());
    }

    @Test
    public void getCurrencyByValueTest() {
        testUtils.logTestStart("SelectCurrency by value test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        Currency currency = dataBaseService.getCurrencyDao().getByValue(currencyValue);
        checkCurrency(currencyCode, currencyValue, currency);
    }

    @Test
    public void deleteCurrencyByCodeTest() {
        testUtils.logTestStart("Delete currency by code test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        dataBaseService.getCurrencyDao().delete(currencyCode);
        checkCurrenciesCount(0, dataBaseService.getCurrencyDao().selectAll());
    }

    @Test
    public void deleteCurrencyByValueTest() {
        testUtils.logTestStart("Delete currency by value test");
        int currencyCode = 643;
        String currencyValue = "RUB";
        createCurrency(currencyCode, currencyValue);

        dataBaseService.getCurrencyDao().delete(currencyValue);
        checkCurrenciesCount(0, dataBaseService.getCurrencyDao().selectAll());
    }

    private void createCurrency(int code, String value) {
        dataBaseService.getCurrencyDao().insert(new Currency(code, value));
    }

    private void checkCurrency(int expectedCode, String expectedValue,
                               Currency actualCurrency) {
        Assertions.assertEquals(expectedCode, actualCurrency.getCode(),
                "Currency code");
        Assertions.assertEquals(expectedValue, actualCurrency.getValue(),
                "Currency value");
    }

    private void checkCurrenciesCount(int expectedCount, List<Currency> actualCurrencyList) {
        Assertions.assertEquals(expectedCount, actualCurrencyList.size(),
                "Currencies count");
    }
}
