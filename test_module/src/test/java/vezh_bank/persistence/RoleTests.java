package vezh_bank.persistence;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import vezh_bank.enums.Role;
import vezh_bank.extended_tests.PersistenceTest;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.persistence.providers.role.RoleArgumentsProvider;
import vezh_bank.persistence.providers.role.RoleNameArgumentsProvider;

import java.util.List;

@Epic("Persistence")
@Story("Role persistence")
@Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/5", url = "https://github.com/vezhny/vezh-lab/issues/5")
@Severity(SeverityLevel.BLOCKER)
public class RoleTests extends PersistenceTest {

    @Feature("Select entity")
    @Description("Select all roles")
    @Test
    public void selectAllRoles() {
        testUtils.logTestStart("Select all roles");
        List<UserRole> roles = dataBaseService.getRoleDao().selectAll();
        asserts.checkObject(3, roles.size(), "Roles count");
    }

    @Feature("Select entity")
    @Description("Select role by id test. ID: {0}")
    @ParameterizedTest
    @ArgumentsSource(RoleArgumentsProvider.class)
    public void selectRoleById(int id, Role role) {
        testUtils.logTestStart("Select " + role.toString() + " role by id");
        int clientRoleId = id;
        UserRole userRole = dataBaseService.getRoleDao().getById(clientRoleId);
        asserts.checkObject(clientRoleId, userRole.getId(), "Role ID");
        asserts.checkObject(role.toString(), userRole.getName(), "Role name");
    }

    @Feature("Select entity")
    @Description("Select role by name test. Name: {0}")
    @ParameterizedTest
    @ArgumentsSource(RoleNameArgumentsProvider.class)
    public void selectRoleByName(String name) {
        testUtils.logTestStart("Select role with name \"" + name + "\"");
        UserRole userRole = serviceProvider.getDataBaseService().getRoleDao().get(name);
        asserts.checkNotNull(userRole, "user role");
    }
}
