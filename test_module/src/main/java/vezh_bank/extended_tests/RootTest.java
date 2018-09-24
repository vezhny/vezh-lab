package vezh_bank.extended_tests;

import com.google.gson.Gson;
import core.config.VezhBankConfiguration;
import core.services.ServiceProvider;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
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
import vezh_bank.assertions.Assert;
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
    protected Gson gson;
    protected Assert anAssert;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected ServiceProvider serviceProvider;

    @Step("Preparing environment")
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        testUtils = new TestUtils(logger);
        gson = new Gson();
        anAssert = new Assert();
    }

    @Step("Clear database")
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
}
