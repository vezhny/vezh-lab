package vezh_bank.persistence;

import core.config.VezhBankConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import vezh_bank.enums.EventType;
import vezh_bank.persistence.entity.Currency;
import vezh_bank.persistence.entity.Event;
import vezh_bank.util.Logger;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = VezhBankConfiguration.class)
@WebAppConfiguration
@ActiveProfiles(MavenProfiles.TEST)
public class PersistenceTest {
    protected static Logger logger = Logger.getLogger(RoleTests.class);

    protected TestUtils testUtils;
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected DataBaseService dataBaseService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        testUtils = new TestUtils(logger);
    }

    @AfterEach
    public void tearDown() {
        testUtils.logTestEnd();
        dataBaseService.getCurrencyDao().deleteAll();
        dataBaseService.getEventDao().deleteAll();
    }

    protected void createCurrency(int code, String value) {
        dataBaseService.getCurrencyDao().insert(new Currency(code, value));
    }

    protected void checkCurrency(int expectedCode, String expectedValue,
                               Currency actualCurrency) {
        Assertions.assertEquals(expectedCode, actualCurrency.getCode(),
                "Currency code");
        Assertions.assertEquals(expectedValue, actualCurrency.getValue(),
                "Currency value");
    }

    protected void checkCurrenciesCount(int expectedCount, List<Currency> actualCurrencyList) {
        Assertions.assertEquals(expectedCount, actualCurrencyList.size(),
                "Currencies count");
    }

    protected void createEvent(Event event) {
        dataBaseService.getEventDao().insert(event);
    }

    protected void checkEvent(EventType expectedEventType, String containingData, Event actualEvent) {
        Assertions.assertEquals(expectedEventType.toString(), actualEvent.getType(), "Event type");
        Assertions.assertTrue(actualEvent.getData().contains(containingData), "Event data");
        Assertions.assertNotNull(actualEvent.getDate(), "Event date");
    }

    protected void checkEventsCount(int expectedEventCount, List<Event> events) {
        Assertions.assertEquals(expectedEventCount, events.size(), "Number of events");
    }
}
