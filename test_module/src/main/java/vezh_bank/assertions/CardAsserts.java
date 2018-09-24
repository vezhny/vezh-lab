package vezh_bank.assertions;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import vezh_bank.enums.CardStatus;
import vezh_bank.persistence.entity.Card;
import vezh_bank.persistence.entity.Currency;
import vezh_bank.persistence.entity.User;

import java.math.BigDecimal;
import java.util.List;

public class CardAsserts extends Asserts {

    @Step("Check card")
    public void checkCard(String expectedPan, User expectedHolder, int expectedCvc,
                          String expectedExpiry, Currency expectedCurrency, CardStatus expectedStatus,
                          BigDecimal expectedAmount, Card actualCard) {
        checkObject(expectedPan, actualCard.getPan(), "PAN");
        checkObject(expectedHolder.toString(), actualCard.getHolder().toString(), "Holder");
        checkObject(expectedCvc, actualCard.getCvc(), "CVC");
        checkObject(expectedExpiry, actualCard.getExpiry(), "Expiry");
        checkObject(expectedCurrency.toString(), actualCard.getCurrency().toString(), "Currency");
        checkObject(expectedStatus.toString(), actualCard.getStatus(), "Status");
        checkObject(expectedAmount, actualCard.getAmount(), "Amount");
    }

    public void checkCardsCount(int expectedCount, List<Card> cards) {
        checkItemsCount(expectedCount, cards, "Number of cards");
    }
}
