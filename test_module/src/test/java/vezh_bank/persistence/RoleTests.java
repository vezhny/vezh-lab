package vezh_bank.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import vezh_bank.enums.Role;
import vezh_bank.extended_tests.PersistenceTest;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.persistence.providers.role.RoleArgumentsProvider;

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
