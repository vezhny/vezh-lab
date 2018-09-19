package vezh_bank;


import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.util.Logger;

public class TestUtils {
    private Logger logger;

    public TestUtils(Logger logger) {
        this.logger = logger;
    }

    public void logTestStart(String log) {
        logger.info("------ " + log + " ------");
    }

    public void logTestEnd() {
        logger.info("-------------------------\n");
    }

    public int createNotAClient(DataBaseService dataBaseService) {
        UserRole role = dataBaseService.getRoleDao().getById(2);
        return createUser(dataBaseService, role);
    }

    public int createClient(DataBaseService dataBaseService) {
        UserRole role = dataBaseService.getRoleDao().getById(1);
        return createUser(dataBaseService, role);    }

    private int createUser(DataBaseService dataBaseService, UserRole role) {
        User user = new User("Test generated", "password", role,
                "Test generated", "No config yet", 3);
        dataBaseService.getUserDao().insert(user);
        return dataBaseService.getUserDao().selectAll().get(0).getId();
    }
}
