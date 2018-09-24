package vezh_bank.service;

import core.dto.UserDTO;
import core.dto.UserRoleDTO;
import core.json.UserAddress;
import core.json.UserConfig;
import core.json.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import org.junit.jupiter.api.Test;
import vezh_bank.constants.UserDefault;
import vezh_bank.extended_tests.ServiceTest;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;

import java.util.List;

@Feature("User service") //TODO: try to user story instead on feature
@Link("https://github.com/vezhny/vezh-lab/issues/15")
public class UserServiceTests extends ServiceTest {

    @Description("Add user test")
    @Test
    public void addUser() {
        testUtils.logTestStart("Add user test");

        List<UserRole> userRoles = serviceProvider.getDataBaseService().getRoleDao().selectAll();
        String login = "Login";
        String password = "password";
        UserRoleDTO roleDTO = new UserRoleDTO(userRoles.get(0));
        UserAddress address = new UserAddress("Belarus", "Homiel", "Svetlogorsk",
                "Lunacharskogo", "30", "213");
        UserData userData = new UserData("Test", "Test", "Test",
                "10.11.1992", address, "+375293956223", "vezhny@gmail.com");
        serviceProvider.getUserService().addUser(new UserDTO(login, password,
                userRoles.get(0), userData));

        List<User> users = serviceProvider.getDataBaseService().getUserDao().selectAll();
        checkNumberOfUsers(1, users.size());

        checkUser(login, password, roleDTO, new UserConfig(), UserDefault.ATTEMPTS_TO_SIGN_IN,
                false, null, userData, users.get(0));
    }
}
