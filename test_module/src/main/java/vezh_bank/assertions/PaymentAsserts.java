package vezh_bank.assertions;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import vezh_bank.persistence.entity.Currency;
import vezh_bank.persistence.entity.Payment;

import java.math.BigDecimal;
import java.util.List;

public class PaymentAsserts extends Asserts {

    @Step("Check payment")
    public void checkPayment(String expectedName, String expectedDescription, BigDecimal expectedMinAmount,
                             BigDecimal expectedMaxAmount, BigDecimal expectedCommission, Currency expectedCurrency,
                             Payment actualPayment) {
        checkObject(expectedName, actualPayment.getName(), "Payment name");
        checkObject(expectedDescription, actualPayment.getDescription(), "Payment description");
        checkObject(expectedMinAmount, actualPayment.getMinAmount(), "Payment min amount");
        checkObject(expectedMaxAmount, actualPayment.getMaxAmount(), "Payment max amount");
        checkObject(expectedCommission, actualPayment.getCommission(), "Payment commission");
        CurrencyAsserts currencyAsserts = new CurrencyAsserts();
        currencyAsserts.checkCurrency(expectedCurrency.getCode(), expectedCurrency.getValue(), actualPayment.getCurrency());
    }

    public void checkPaymentCount(int expectedPaymentCount, List<Payment> payments) {
        checkItemsCount(expectedPaymentCount, payments, "Number of payments");
    }
}
