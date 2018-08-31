package vezh_bank.persistence;

import org.junit.jupiter.api.Test;
import vezh_bank.extended_tests.PersistenceTest;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;

import java.util.List;

import static vezh_bank.constants.UserDefault.ATTEMPTS_TO_SIGN_IN;

public class UserTests extends PersistenceTest {

    @Test
    public void insertUserTest() {
        testUtils.logTestStart("Insert user test");

        String login = "Test";
        String password = "Test";
        UserRole role = dataBaseService.getRoleDao().getById(1);
        String config = "Test config";
        String data = "Test data";
        User user = new User(login, password, role, data, config, ATTEMPTS_TO_SIGN_IN);
        createUser(user);

        List<User> users = dataBaseService.getUserDao().selectAll();
        checkUsersCount(1, users);
        checkUser(login, password, role, config, ATTEMPTS_TO_SIGN_IN, false,
                null, data, users.get(0));
    }

    @Test
    public void selectByIdUserTest() {
        testUtils.logTestStart("Select user by ID test");

        String login = "Test";
        String password = "Test";
        UserRole role = dataBaseService.getRoleDao().getById(1);
        String config = "Test config";
        String data = "Test data";
        User user = new User(login, password, role, data, config, ATTEMPTS_TO_SIGN_IN);
        createUser(user);

        List<User> users = dataBaseService.getUserDao().selectAll();
        user = dataBaseService.getUserDao().getById(users.get(0).getId());
        checkUser(login, password, role, config, ATTEMPTS_TO_SIGN_IN, false,
                null, data, user);
    }
}
