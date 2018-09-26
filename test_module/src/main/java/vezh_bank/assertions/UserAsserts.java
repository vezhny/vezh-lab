package vezh_bank.assertions;

import core.dto.UserDTO;
import core.dto.UserRoleDTO;
import core.json.UserConfig;
import core.json.UserData;
import io.qameta.allure.Step;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.util.Encryptor;

import java.util.List;

public class UserAsserts extends Asserts {

    @Step("Check user")
    public void checkUser(String expectedLogin, String expectedPassword, UserRoleDTO expectedRole,
                          UserConfig expectedUserConfig, int expectedAttemptsToSignIn,
                          boolean expectedBlocked, String expectedLastSignIn,
                          UserData expectedUserData, User actualUser) {

        checkUser(expectedLogin, expectedPassword, expectedRole.getEntity(),
                expectedUserConfig.toString(), expectedAttemptsToSignIn, expectedBlocked,
                expectedLastSignIn, expectedUserData.toString(), actualUser);
    }

    @Step("Check user")
    public void checkUser(String expectedLogin, String expectedPassword, UserRole expectedRole,
                          String expectedConfig, int expectedAttemptsToSignInLeft, boolean expectedBlocked,
                          String expectedLastSignInDate, String expectedData, User actualUser) {

        Encryptor encryptor = new Encryptor();
        checkObject(expectedLogin, actualUser.getLogin(), "Login");
        checkObject(expectedPassword, encryptor.decrypt(actualUser.getPassword()), "Password");
        checkObject(expectedRole.getName(), actualUser.getRole(), "Role");
        checkObject(expectedConfig, actualUser.getConfig(), "Config");
        checkObject(expectedAttemptsToSignInLeft, actualUser.getAttemptsToSignIn(), "Attempts to sign in");
        checkObject(expectedBlocked, actualUser.isBlocked(), "Blocked");
        checkObject(expectedLastSignInDate, actualUser.getLastSignIn(), "Last sign in");
        checkObject(expectedData, actualUser.getData(), "Data");
    }

    @Step("Check user")
    public void checkUser(User expectedUser, User actualUser) {
        UserDTO userDTO = new UserDTO(expectedUser);
        checkUser(userDTO.getLogin(), userDTO.getPassword(), userDTO.getRole(),
                userDTO.getConfig(), userDTO.getAttemptsToSignIn(), userDTO.isBlocked(),
                userDTO.getLastSignIn(), userDTO.getData(), actualUser);
    }

    public void checkUsersCount(int expectedCount, List<User> users) {
        checkItemsCount(expectedCount, users, "Number of users");
    }

    public void checkNumberOfUsers(int expected, int actual) {
        checkNumber(expected, actual, "Number of users");
    }


}
