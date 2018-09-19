package vezh_bank.extended_tests;

import core.config.VezhBankConfiguration;
import core.services.ServiceProvider;
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
import vezh_bank.util.Logger;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = VezhBankConfiguration.class)
@WebAppConfiguration
@ActiveProfiles(MavenProfiles.TEST)
public class RootTest {
    protected static Logger logger = Logger.getLogger(PersistenceTest.class);

    protected TestUtils testUtils;
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected ServiceProvider serviceProvider;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        testUtils = new TestUtils(logger);
    }

    @AfterEach
    public void tearDown() {
        testUtils.logTestEnd();
        serviceProvider.getDataBaseService().getCurrencyDao().deleteAll();
        serviceProvider.getDataBaseService().getEventDao().deleteAll();
        serviceProvider.getDataBaseService().getPaymentDao().deleteAll();
        serviceProvider.getDataBaseService().getTransactionDao().deleteAll();
        serviceProvider.getDataBaseService().getUserDao().deleteAll();
        serviceProvider.getDataBaseService().getUserRequestDao().deleteAll();
        serviceProvider.getDataBaseService().getCardDao().deleteAll();
    }

    protected void checkNumberOfEvents(int expected, int actual) {
        checkNumber(expected, actual, "Number of events");
    }

    private void checkNumber(int expected, int actual, String message) {
        Assertions.assertEquals(expected, actual, message);
    }
}
