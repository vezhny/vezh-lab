package vezh_bank;


import core.json.UserAddress;
import core.json.UserConfig;
import core.json.UserData;
import io.qameta.allure.Step;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.util.Logger;

public class TestUtils {
    private Logger logger;

    public TestUtils(Logger logger) {
        this.logger = logger;
    }

    @Step("Start test: {0}")
    public void logTestStart(String log) {
        logger.info("------ " + log + " ------");
    }

    @Step("End test")
    public void logTestEnd() {
        logger.info("-------------------------\n");
    }

    public int createNotAClient(DataBaseService dataBaseService) {
        UserRole role = dataBaseService.getRoleDao().getById(2);
        return createUser(dataBaseService, role);
    }

    public int createClient(DataBaseService dataBaseService) {
        UserRole role = dataBaseService.getRoleDao().getById(1);
        return createUser(dataBaseService, role);
    }

    @Step("Create user. Role: {1}")
    public int createUser(DataBaseService dataBaseService, UserRole role) {
        UserAddress address = new UserAddress("", "", "", "", "", "", "");
        UserData userData = new UserData("Test", "Test", "Test",
                "10.10.1000", address, "Test", "");
        User user = new User("Test generated", "password", role,
                userData.toString(), new UserConfig().toString(), 3);
        dataBaseService.getUserDao().insert(user);
        return dataBaseService.getUserDao().selectAll().get(0).getId();
    }
}
