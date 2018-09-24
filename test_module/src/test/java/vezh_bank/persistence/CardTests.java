package vezh_bank.persistence;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import vezh_bank.enums.CardStatus;
import vezh_bank.extended_tests.PersistenceTest;
import vezh_bank.persistence.entity.Card;
import vezh_bank.persistence.entity.Currency;
import vezh_bank.persistence.entity.User;
import vezh_bank.persistence.entity.UserRole;
import vezh_bank.persistence.providers.card.SelectCardArgumentsProvider;
import vezh_bank.persistence.providers.card.SelectCardWithPageArgumentsProvider;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static vezh_bank.constants.UserDefault.ATTEMPTS_TO_SIGN_IN;

@Feature("Card persistence")
@Link("https://github.com/vezhny/vezh-lab/issues/5")
public class CardTests extends PersistenceTest {

    @Description("Insert card test")
    @Test
    public void insertTest() {
        testUtils.logTestStart("Insert card test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User holder = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        createUser(holder);
        holder = dataBaseService.getUserDao().selectAll().get(0);

        Currency currency = new Currency(643, "RUB");
        createCurrency(currency);

        String pan = "5454545454545454";
        int cvc = 111;
        String expiry = "1020";

        Card card = new Card(pan, holder, cvc, expiry, currency);
        createCard(card);

        List<Card> cards = dataBaseService.getCardDao().selectAll();
        anAssert.checkCardsCount(1, cards);
        anAssert.checkCard(pan, holder, cvc, expiry, currency, CardStatus.ACTIVE, new BigDecimal("0.00"), cards.get(0));
    }

    @Description("Select card with ID test")
    @Test
    public void selectTest() {
        testUtils.logTestStart("Select card with ID test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User holder = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        createUser(holder);
        holder = dataBaseService.getUserDao().selectAll().get(0);

        Currency currency = new Currency(643, "RUB");
        createCurrency(currency);

        String pan = "5454545454545454";
        int cvc = 111;
        String expiry = "1020";

        Card card = new Card(pan, holder, cvc, expiry, currency);
        createCard(card);

        List<Card> cards = dataBaseService.getCardDao().selectAll();
        card = dataBaseService.getCardDao().getById(cards.get(0).getId());

        anAssert.checkCard(pan, holder, cvc, expiry, currency, CardStatus.ACTIVE, new BigDecimal("0.00"), card);
    }

    @Description("Update card test")
    @Test
    public void updateTest() {
        testUtils.logTestStart("Update card test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User holder = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        createUser(holder);
        holder = dataBaseService.getUserDao().selectAll().get(0);

        Currency currency = new Currency(643, "RUB");
        createCurrency(currency);

        String pan = "5454545454545454";
        int cvc = 111;
        String expiry = "1020";

        Card card = new Card(pan, holder, cvc, expiry, currency);
        createCard(card);

        List<Card> cards = dataBaseService.getCardDao().selectAll();
        card = dataBaseService.getCardDao().getById(cards.get(0).getId());
        BigDecimal newAmount = new BigDecimal("5.00");
        card.setAmount(newAmount);
        dataBaseService.getCardDao().update(card);

        card = dataBaseService.getCardDao().getById(card.getId());
        anAssert.checkCard(pan, holder, cvc, expiry, currency, CardStatus.ACTIVE, newAmount, card);
    }

    @Description("Delete card test")
    @Test
    public void deleteTest() {
        testUtils.logTestStart("Delete card test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User holder = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        createUser(holder);
        holder = dataBaseService.getUserDao().selectAll().get(0);

        Currency currency = new Currency(643, "RUB");
        createCurrency(currency);

        String pan = "5454545454545454";
        int cvc = 111;
        String expiry = "1020";

        Card card = new Card(pan, holder, cvc, expiry, currency);
        createCard(card);

        List<Card> cards = dataBaseService.getCardDao().selectAll();
        card = dataBaseService.getCardDao().getById(cards.get(0).getId());
        dataBaseService.getCardDao().delete(card);

        anAssert.checkCardsCount(0, dataBaseService.getCardDao().selectAll());
    }

    @Description("Delete card by ID test")
    @Test
    public void deleteByIdTest() {
        testUtils.logTestStart("Delete card by ID test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User holder = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        createUser(holder);
        holder = dataBaseService.getUserDao().selectAll().get(0);

        Currency currency = new Currency(643, "RUB");
        createCurrency(currency);

        String pan = "5454545454545454";
        int cvc = 111;
        String expiry = "1020";

        Card card = new Card(pan, holder, cvc, expiry, currency);
        createCard(card);

        List<Card> cards = dataBaseService.getCardDao().selectAll();
        card = dataBaseService.getCardDao().getById(cards.get(0).getId());
        dataBaseService.getCardDao().delete(card.getId());

        anAssert.checkCardsCount(0, dataBaseService.getCardDao().selectAll());
    }

    @Description("Select cards with holder test")
    @Test
    public void selectByHolderTest() {
        testUtils.logTestStart("Select cards with holder test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();

        User holder1 = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        User holder2 = new User("Login2", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        User holder3 = new User("Login3", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(holder1);
        createUser(holder2);
        createUser(holder3);

        List<User> holders = dataBaseService.getUserDao().selectAll();
        holder1 = holders.get(0);
        holder2 = holders.get(1);
        holder3 = holders.get(2);

        Currency currency = new Currency(643, "RUB");
        createCurrency(currency);

        Card card1 = new Card("5454545454545454", holder1, 111, "1020", currency);
        Card card2 = new Card("5454545454545456", holder2, 111, "1020", currency);
        Card card3 = new Card("5454545454545457", holder3, 111, "1020", currency);
        Card card4 = new Card("5454545454545458", holder1, 111, "1020", currency);
        Card card5 = new Card("5454545454545459", holder2, 111, "1020", currency);
        Card card6 = new Card("5454545454545451", holder2, 111, "1020", currency);

        createCard(card1);
        createCard(card2);
        createCard(card3);
        createCard(card4);
        createCard(card5);
        createCard(card6);

        List<Card> cards = dataBaseService.getCardDao().select(holder2.getId());

        anAssert.checkCardsCount(3, cards);
    }

    @Description("Select cards count test")
    @Test
    public void selectCountTest() {
        testUtils.logTestStart("Select cards count test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();

        User holder1 = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        User holder2 = new User("Login2", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        User holder3 = new User("Login3", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(holder1);
        createUser(holder2);
        createUser(holder3);

        List<User> holders = dataBaseService.getUserDao().selectAll();
        holder1 = holders.get(0);
        holder2 = holders.get(1);
        holder3 = holders.get(2);

        Currency currency = new Currency(643, "RUB");
        createCurrency(currency);

        Card card1 = new Card("5454545454545454", holder1, 111, "1020", currency);
        Card card2 = new Card("5454545454545456", holder2, 111, "1020", currency);
        Card card3 = new Card("5454545454545457", holder3, 111, "1020", currency);
        Card card4 = new Card("5454545454545458", holder1, 111, "1020", currency);
        Card card5 = new Card("5454545454545459", holder2, 111, "1020", currency);
        Card card6 = new Card("5454545454545451", holder2, 111, "1020", currency);

        createCard(card1);
        createCard(card2);
        createCard(card3);
        createCard(card4);
        createCard(card5);
        createCard(card6);

        int cards = dataBaseService.getCardDao().selectCount();

        anAssert.check(6, cards, "Number of cards");
    }

    @Description("Select card where: pan: {0}, holder name: {1}, creation date: {2}, expiry: {3}, currency: {4}" +
            ", status: {5}")
    @ParameterizedTest
    @ArgumentsSource(SelectCardArgumentsProvider.class)
    public void selectTest(String pan, String holderName, String creationDate, String expiry, String currency,
                           String status, int expectedCardsCount) {
        testUtils.logTestStart("Select card with params test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();

        User holder1 = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        User holder2 = new User("Login2", "password", userRoles.get(0),
                "User data 2", "Config", ATTEMPTS_TO_SIGN_IN);
        User holder3 = new User("Login3", "password", userRoles.get(0),
                "User data 3", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(holder1);
        createUser(holder2);
        createUser(holder3);

        List<User> holders = dataBaseService.getUserDao().selectAll();
        holder1 = holders.get(0);
        holder2 = holders.get(1);
        holder3 = holders.get(2);

        Currency cardCurrency1 = new Currency(643, "RUB");
        Currency cardCurrency2 = new Currency(891, "BYN");
        Currency cardCurrency3 = new Currency(943, "USD");
        createCurrency(cardCurrency1);
        createCurrency(cardCurrency2);
        createCurrency(cardCurrency3);

        Card card1 = new Card("5454545454545454", holder1, 111, "1020", cardCurrency1);
        Card card2 = new Card("1234567890123456", holder2, 101, "0920", cardCurrency2);
        card2.setStatus(CardStatus.BLOCKED);
        Card card3 = new Card("5555555555555555", holder3, 120, "0820", cardCurrency1);
        Card card4 = new Card("6666666666666669", holder1, 210, "1021", cardCurrency2);
        Card card5 = new Card("1111111111111111", holder2, 150, "1120", cardCurrency3);
        card5.setStatus(CardStatus.BLOCKED);
        Card card6 = new Card("2222222222222222", holder2, 160, "1220", cardCurrency1);
        Card card7 = new Card("3333333333333333", holder3, 310, "0818", cardCurrency2);
        card7.setStatus(CardStatus.EXPIRED);
        Card card8 = new Card("4444444444444444", holder1, 790, "0519", cardCurrency3);

        createCard(card1);
        createCard(card2);
        createCard(card3);
        createCard(card4);
        createCard(card5);
        createCard(card6);
        createCard(card7);
        createCard(card8);

        List<Card> cards = dataBaseService.getCardDao().select(pan, holderName, creationDate, expiry, currency, status);

        anAssert.checkCardsCount(expectedCardsCount, cards);
    }

    @Description("Select cards count where pan: {0}, holder name: {1}, creation date: {2}, expiry: {3}, currency: {4} " +
            ", status: {5}")
    @ParameterizedTest
    @ArgumentsSource(SelectCardArgumentsProvider.class)
    public void selectCountTest(String pan, String holderName, String creationDate, String expiry, String currency,
                           String status, int expectedCardsCount) {
        testUtils.logTestStart("Select cards count with params test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();

        User holder1 = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        User holder2 = new User("Login2", "password", userRoles.get(0),
                "User data 2", "Config", ATTEMPTS_TO_SIGN_IN);
        User holder3 = new User("Login3", "password", userRoles.get(0),
                "User data 3", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(holder1);
        createUser(holder2);
        createUser(holder3);

        List<User> holders = dataBaseService.getUserDao().selectAll();
        holder1 = holders.get(0);
        holder2 = holders.get(1);
        holder3 = holders.get(2);

        Currency cardCurrency1 = new Currency(643, "RUB");
        Currency cardCurrency2 = new Currency(891, "BYN");
        Currency cardCurrency3 = new Currency(943, "USD");
        createCurrency(cardCurrency1);
        createCurrency(cardCurrency2);
        createCurrency(cardCurrency3);

        Card card1 = new Card("5454545454545454", holder1, 111, "1020", cardCurrency1);
        Card card2 = new Card("1234567890123456", holder2, 101, "0920", cardCurrency2);
        card2.setStatus(CardStatus.BLOCKED);
        Card card3 = new Card("5555555555555555", holder3, 120, "0820", cardCurrency1);
        Card card4 = new Card("6666666666666669", holder1, 210, "1021", cardCurrency2);
        Card card5 = new Card("1111111111111111", holder2, 150, "1120", cardCurrency3);
        card5.setStatus(CardStatus.BLOCKED);
        Card card6 = new Card("2222222222222222", holder2, 160, "1220", cardCurrency1);
        Card card7 = new Card("3333333333333333", holder3, 310, "0818", cardCurrency2);
        card7.setStatus(CardStatus.EXPIRED);
        Card card8 = new Card("4444444444444444", holder1, 790, "0519", cardCurrency3);

        createCard(card1);
        createCard(card2);
        createCard(card3);
        createCard(card4);
        createCard(card5);
        createCard(card6);
        createCard(card7);
        createCard(card8);

        int cards = dataBaseService.getCardDao().selectCount(pan, holderName, creationDate, expiry, currency, status);

        anAssert.check(expectedCardsCount, cards, "Number of cards");
    }

    @Description("Select cards where required page: {0}, rows on page: {1},  pan: {2}, holder name: {3}, " +
            "creation date: {4}, expiry: {5}, currency: {6}, status: {7}")
    @ParameterizedTest
    @ArgumentsSource(SelectCardWithPageArgumentsProvider.class)
    public void selectTest(int requiredPage, int rowsOnPage, String pan, String holderName, String creationDate,
                           String expiry, String currency, String status, int expectedCardsCount) {
        testUtils.logTestStart("Select cards with pages test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();

        User holder1 = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        User holder2 = new User("Login2", "password", userRoles.get(0),
                "User data 2", "Config", ATTEMPTS_TO_SIGN_IN);
        User holder3 = new User("Login3", "password", userRoles.get(0),
                "User data 3", "Config", ATTEMPTS_TO_SIGN_IN);

        createUser(holder1);
        createUser(holder2);
        createUser(holder3);

        List<User> holders = dataBaseService.getUserDao().selectAll();
        holder1 = holders.get(0);
        holder2 = holders.get(1);
        holder3 = holders.get(2);

        Currency cardCurrency1 = new Currency(643, "RUB");
        Currency cardCurrency2 = new Currency(891, "BYN");
        Currency cardCurrency3 = new Currency(943, "USD");
        createCurrency(cardCurrency1);
        createCurrency(cardCurrency2);
        createCurrency(cardCurrency3);

        Card card1 = new Card("5454545454545454", holder1, 111, "1020", cardCurrency1);
        Card card2 = new Card("1234567890123456", holder2, 101, "0920", cardCurrency2);
        card2.setStatus(CardStatus.BLOCKED);
        Card card3 = new Card("5555555555555555", holder3, 120, "0820", cardCurrency1);
        Card card4 = new Card("6666666666666669", holder1, 210, "1021", cardCurrency2);
        Card card5 = new Card("1111111111111111", holder2, 150, "1120", cardCurrency3);
        card5.setStatus(CardStatus.BLOCKED);
        Card card6 = new Card("2222222222222222", holder2, 160, "1220", cardCurrency1);
        Card card7 = new Card("3333333333333333", holder3, 310, "0818", cardCurrency2);
        card7.setStatus(CardStatus.EXPIRED);
        Card card8 = new Card("4444444444444444", holder1, 790, "0519", cardCurrency3);

        createCard(card1);
        createCard(card2);
        createCard(card3);
        createCard(card4);
        createCard(card5);
        createCard(card6);
        createCard(card7);
        createCard(card8);

        List<Card> cards = dataBaseService.getCardDao().select(requiredPage, rowsOnPage, pan, holderName, creationDate,
                expiry, currency, status);

        anAssert.checkCardsCount(expectedCardsCount, cards);
    }

    @Description("Delete card holder test")
    @Test
    public void deleteCardHolderTest() {
        testUtils.logTestStart("Delete card holder test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User holder = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        createUser(holder);
        holder = dataBaseService.getUserDao().selectAll().get(0);

        Currency currency = new Currency(643, "RUB");
        createCurrency(currency);

        String pan = "5454545454545454";
        int cvc = 111;
        String expiry = "1020";

        Card card = new Card(pan, holder, cvc, expiry, currency);
        createCard(card);

        dataBaseService.getUserDao().delete(holder);
        anAssert.checkException(EntityNotFoundException.class, () -> dataBaseService.getCardDao().selectAll().get(0));
    }

    @Description("Delete card currency test")
    @Test
    public void deleteCardCurrencyTest() {
        testUtils.logTestStart("Delete card currency test");

        List<UserRole> userRoles = dataBaseService.getRoleDao().selectAll();
        User holder = new User("Login1", "password", userRoles.get(0),
                "User data 1", "Config", ATTEMPTS_TO_SIGN_IN);
        createUser(holder);
        holder = dataBaseService.getUserDao().selectAll().get(0);

        Currency currency = new Currency(643, "RUB");
        createCurrency(currency);

        String pan = "5454545454545454";
        int cvc = 111;
        String expiry = "1020";

        Card card = new Card(pan, holder, cvc, expiry, currency);
        createCard(card);

        dataBaseService.getCurrencyDao().delete(currency);
        anAssert.checkException(EntityNotFoundException.class, () -> dataBaseService.getCardDao().selectAll().get(0));
    }
}
