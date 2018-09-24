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
import vezh_bank.assertions.*;
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

    protected Asserts asserts;
    protected CardAsserts cardAsserts;
    protected CurrencyAsserts currencyAsserts;
    protected EventAsserts eventAsserts;
    protected HttpAsserts httpAsserts;
    protected PaymentAsserts paymentAsserts;
    protected RoleAsserts roleAsserts;
    protected TransactionAsserts transactionAsserts;
    protected UserAsserts userAsserts;
    protected UserRequestAsserts userRequestAsserts;

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

        asserts = new Asserts();
        cardAsserts = new CardAsserts();
        currencyAsserts = new CurrencyAsserts();
        eventAsserts = new EventAsserts();
        httpAsserts = new HttpAsserts();
        paymentAsserts = new PaymentAsserts();
        roleAsserts = new RoleAsserts();
        transactionAsserts = new TransactionAsserts();
        userAsserts = new UserAsserts();
        userRequestAsserts = new UserRequestAsserts();
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
