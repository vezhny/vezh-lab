package vezh_bank;


import core.json.UserAddress;
import core.json.UserConfig;
import core.json.UserData;
import io.qameta.allure.Step;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.Card;
import vezh_bank.persistence.entity.Currency;
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
        logger.info("Creating " + role.getName());
        UserAddress address = new UserAddress("", "", "", "", "", "", "");
        UserData userData = new UserData("Test", "Test", "Test",
                "10.10.1000", address, "Test", "");
        String login = String.valueOf(System.currentTimeMillis());
        User user = new User(login, "password", role,
                userData.toString(), new UserConfig().toString(), 3);
        dataBaseService.getUserDao().insert(user);
        return dataBaseService.getUserDao().select(login, role.getName(), null, null).get(0).getId();
    }

    @Step("Create card. Holder: {1}. Currency: {2}")
    public int createCard(DataBaseService dataBaseService, User user, Currency currency) {
        logger.info("Creating card with holder: " + user.toString());

        Card card = new Card(String.valueOf(System.currentTimeMillis()), user, 123, "1025", currency);
        dataBaseService.getCardDao().insert(card);
        return dataBaseService.getCardDao().select(user.getId()).get(0).getId();
    }

    @Step("Create currency. Code: {1}. Value: {2}")
    public int createCurrency(DataBaseService dataBaseService, int code, String value) {
        Currency currency = new Currency(code, value);
        dataBaseService.getCurrencyDao().insert(currency);
        return code;
    }
}
