package vezh_bank.persistence;

import core.config.VezhBankConfiguration;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
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
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.UserRole;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = VezhBankConfiguration.class)
@WebAppConfiguration
@ActiveProfiles(MavenProfiles.TEST)
public class RoleTests {
    private static Logger logger = Logger.getLogger(RoleTests.class);

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
    }

    @Test
    public void selectAllRoles() {
        testUtils.logTestStart("Select all roles");
        List<UserRole> roles = dataBaseService.getRoleDao().selectAll();
        Assertions.assertEquals(3, roles.size(), "Roles count");
    }
}
