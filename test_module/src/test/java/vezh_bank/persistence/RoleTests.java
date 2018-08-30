package vezh_bank.persistence;

import core.config.VezhBankConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
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
import vezh_bank.enums.Role;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.persistence.providers.role.RoleArgumentsProvider;
import vezh_bank.util.Logger;

import java.util.List;

public class RoleTests extends PersistenceTest {

    @Test
    public void selectAllRoles() {
        testUtils.logTestStart("Select all roles");
        List<UserRole> roles = dataBaseService.getRoleDao().selectAll();
        Assertions.assertEquals(3, roles.size(), "Roles count");
    }

    @ParameterizedTest
    @ArgumentsSource(RoleArgumentsProvider.class)
    public void selectRoleById(int id, Role role) {
        testUtils.logTestStart("Select " + role.toString() + " role by id");
        int clientRoleId = id;
        UserRole userRole = dataBaseService.getRoleDao().getById(clientRoleId);
        Assertions.assertEquals(clientRoleId, userRole.getId(), "Role ID");
        Assertions.assertEquals(role.toString(), userRole.getName(), "Role name");
    }
}
