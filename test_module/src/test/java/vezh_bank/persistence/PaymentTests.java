package vezh_bank.persistence;

import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import vezh_bank.extended_tests.PersistenceTest;
import vezh_bank.persistence.entity.Currency;
import vezh_bank.persistence.entity.Payment;
import vezh_bank.persistence.providers.payment.SelectPaymentAllParamsArgumentsProvider;
import vezh_bank.persistence.providers.payment.SelectPaymentCurrencyArgumentsProvider;
import vezh_bank.persistence.providers.payment.SelectPaymentDeletedCurrencyArgumentsProvider;
import vezh_bank.persistence.providers.payment.SelectPaymentNameAndCurrencyArgumentsProvider;

import java.math.BigDecimal;
import java.util.List;

@Story("Payment persistence")
@Link(url = "https://github.com/vezhny/vezh-lab/issues/5")
public class PaymentTests extends PersistenceTest {

    @Description("Insert payment test")
    @Test
    public void createPaymentTest() {
        testUtils.logTestStart("Insert payment test");
        String paymentName = "Test payment";
        String paymentDescription = "Test description";
        BigDecimal minAmount = new BigDecimal("100.05");
        BigDecimal maxAmount = new BigDecimal("999.99");
        BigDecimal commission = new BigDecimal("1.5");
        Currency currency = new Currency(643, "RUB");
        createCurrency(currency.getCode(), currency.getValue());
        Payment payment = new Payment(paymentName, paymentDescription, minAmount.toString(),
                maxAmount.toString(), commission.toString(), currency);
        createPayment(payment);

        List<Payment> payments = dataBaseService.getPaymentDao().selectAll();
        paymentAsserts.checkPaymentCount(1, payments);
        paymentAsserts.checkPayment(paymentName, paymentDescription, minAmount, maxAmount, commission, currency, payments.get(0));
    }

    @Description("Select by ID test")
    @Test
    public void selectByIdTest() {
        testUtils.logTestStart("Select by ID test");
        String paymentName = "Test payment";
        String paymentDescription = "Test description";
        BigDecimal minAmount = new BigDecimal("100.05");
        BigDecimal maxAmount = new BigDecimal("999.99");
        BigDecimal commission = new BigDecimal("1.5");
        Currency currency = new Currency(643, "RUB");
        createCurrency(currency.getCode(), currency.getValue());
        Payment payment = new Payment(paymentName, paymentDescription, minAmount.toString(),
                maxAmount.toString(), commission.toString(), currency);
        createPayment(payment);

        payment = dataBaseService.getPaymentDao().getById(dataBaseService.getPaymentDao().selectAll().get(0).getId());
        paymentAsserts.checkPayment(paymentName, paymentDescription, minAmount, maxAmount, commission, currency, payment);
    }

    @Description("Update payment test")
    @Test
    public void updatePaymentTest() {
        testUtils.logTestStart("Update payment test");
        String paymentName = "Test payment";
        String paymentDescription = "Test description";
        BigDecimal minAmount = new BigDecimal("100.05");
        BigDecimal maxAmount = new BigDecimal("999.99");
        BigDecimal commission = new BigDecimal("1.5");
        Currency currency = new Currency(643, "RUB");
        createCurrency(currency.getCode(), currency.getValue());
        Payment payment = new Payment(paymentName, paymentDescription, minAmount.toString(),
                maxAmount.toString(), commission.toString(), currency);
        createPayment(payment);

        payment = dataBaseService.getPaymentDao().getById(dataBaseService.getPaymentDao().selectAll().get(0).getId());
        String updatedName = "Test update";
        payment.setName(updatedName);
        dataBaseService.getPaymentDao().update(payment);

        List<Payment> payments = dataBaseService.getPaymentDao().selectAll();
        paymentAsserts.checkPaymentCount(1, payments);
        paymentAsserts.checkPayment(updatedName, paymentDescription, minAmount, maxAmount, commission, currency, payments.get(0));
    }

    @Description("Delete payment test")
    @Test
    public void deletePaymentTest() {
        testUtils.logTestStart("Delete payment test");
        String paymentName = "Test payment";
        String paymentDescription = "Test description";
        BigDecimal minAmount = new BigDecimal("100.05");
        BigDecimal maxAmount = new BigDecimal("999.99");
        BigDecimal commission = new BigDecimal("1.5");
        Currency currency = new Currency(643, "RUB");
        createCurrency(currency.getCode(), currency.getValue());
        Payment payment = new Payment(paymentName, paymentDescription, minAmount.toString(),
                maxAmount.toString(), commission.toString(), currency);
        createPayment(payment);

        payment = dataBaseService.getPaymentDao().getById(dataBaseService.getPaymentDao().selectAll().get(0).getId());
        dataBaseService.getPaymentDao().delete(payment);
        paymentAsserts.checkPaymentCount(0, dataBaseService.getPaymentDao().selectAll());
    }

    @Description("Delete payment by ID test")
    @Test
    public void deletePaymentByIdTest() {
        testUtils.logTestStart("Delete payment by ID test");
        String paymentName = "Test payment";
        String paymentDescription = "Test description";
        BigDecimal minAmount = new BigDecimal("100.05");
        BigDecimal maxAmount = new BigDecimal("999.99");
        BigDecimal commission = new BigDecimal("1.5");
        Currency currency = new Currency(643, "RUB");
        createCurrency(currency.getCode(), currency.getValue());
        Payment payment = new Payment(paymentName, paymentDescription, minAmount.toString(),
                maxAmount.toString(), commission.toString(), currency);
        createPayment(payment);

        dataBaseService.getPaymentDao().delete(dataBaseService.getPaymentDao().selectAll().get(0).getId());
        paymentAsserts.checkPaymentCount(0, dataBaseService.getPaymentDao().selectAll());
    }

    @Description("Select count test")
    @Test
    public void selectCountTest() {
        testUtils.logTestStart("Select count test");
        Currency currency = new Currency(643, "RUB");
        createCurrency(currency.getCode(), currency.getValue());
        Payment payment1 = new Payment("Payment1", "Test description",
                "100.05", "999.99", "1.5", currency);
        Payment payment2 = new Payment("Payment2", "Test description",
                "100.05", "999.99", "1.5", currency);
        createPayment(payment1);
        createPayment(payment2);

        asserts.checkObject(2, dataBaseService.getPaymentDao().selectCount(), "Number of payments");
    }

    @Description("Select payment with currency \"{0}\" test")
    @ParameterizedTest
    @ArgumentsSource(SelectPaymentCurrencyArgumentsProvider.class)
    public void selectPaymentWithCurrency(String currency, int expectedPaymentCount) {
        testUtils.logTestStart("Select payment with currency test");
        Currency currency1 = new Currency(643, "RUB");
        Currency currency2 = new Currency(891, "BYN");
        createCurrency(currency1);
        createCurrency(currency2);

        Payment payment1 = new Payment("Payment1", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment2 = new Payment("Payment2", "Test description",
                "100.05", "999.99", "1.5", currency2);
        Payment payment3 = new Payment("Payment3", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment4 = new Payment("Payment4", "Test description",
                "100.05", "999.99", "1.5", currency2);

        createPayment(payment1);
        createPayment(payment2);
        createPayment(payment3);
        createPayment(payment4);

        List<Payment> payments = dataBaseService.getPaymentDao().select(currency);
        paymentAsserts.checkPaymentCount(expectedPaymentCount, payments);
    }

    @Description("Select payment with name \"{0}\" and currency \"{1}\" test")
    @ParameterizedTest
    @ArgumentsSource(SelectPaymentNameAndCurrencyArgumentsProvider.class)
    public void selectPaymentWithNameAndCurrencyTest(String name, String currency, int expectedPaymentCount) {
        testUtils.logTestStart("Select payment with name and currency test");
        Currency currency1 = new Currency(643, "RUB");
        Currency currency2 = new Currency(891, "BYN");
        createCurrency(currency1);
        createCurrency(currency2);

        Payment payment1 = new Payment("Payment1", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment2 = new Payment("Payment2", "Test description",
                "100.05", "999.99", "1.5", currency2);
        Payment payment3 = new Payment("Payment3", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment4 = new Payment("Payment4", "Test description",
                "100.05", "999.99", "1.5", currency2);

        createPayment(payment1);
        createPayment(payment2);
        createPayment(payment3);
        createPayment(payment4);

        List<Payment> payments = dataBaseService.getPaymentDao().select(name, currency);
        paymentAsserts.checkPaymentCount(expectedPaymentCount, payments);
    }

    @Description("Select payment with all params test. Required page: {0}, rows on page: {1}, name: {2}, currency: {3}")
    @ParameterizedTest
    @ArgumentsSource(SelectPaymentAllParamsArgumentsProvider.class)
    public void selectPaymentAllParamsTest(int requiredPage, int rowsOnPage,
                                           String name, String currency, int expectedPaymentCount) {
        testUtils.logTestStart("Select payment with all params test");
        Currency currency1 = new Currency(643, "RUB");
        Currency currency2 = new Currency(891, "BYN");
        createCurrency(currency1);
        createCurrency(currency2);

        Payment payment1 = new Payment("Payment1", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment2 = new Payment("Payment2", "Test description",
                "100.05", "999.99", "1.5", currency2);
        Payment payment3 = new Payment("Payment3", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment4 = new Payment("Payment4", "Test description",
                "100.05", "999.99", "1.5", currency2);
        Payment payment8 = new Payment("Payment5", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment5 = new Payment("Payment6", "Test description",
                "100.05", "999.99", "1.5", currency2);
        Payment payment6 = new Payment("Payment7", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment7 = new Payment("Payment8", "Test description",
                "100.05", "999.99", "1.5", currency2);

        createPayment(payment1);
        createPayment(payment2);
        createPayment(payment3);
        createPayment(payment4);
        createPayment(payment5);
        createPayment(payment6);
        createPayment(payment7);
        createPayment(payment8);

        List<Payment> payments = dataBaseService.getPaymentDao().select(requiredPage, rowsOnPage, name, currency);
        paymentAsserts.checkPaymentCount(expectedPaymentCount, payments);
    }

    @Description("Delete payment currency test")
    @Test
    public void deletePaymentCurrencyTest() {
        testUtils.logTestStart("Delete payment currency test");
        Currency currency = new Currency(643, "RUB");
        createCurrency(currency);
        String paymentName = "Test payment";
        String paymentDescription = "Test description";
        BigDecimal minAmount = new BigDecimal("100.05");
        BigDecimal maxAmount = new BigDecimal("999.99");
        BigDecimal commission = new BigDecimal("1.5");
        Payment payment = new Payment(paymentName, paymentDescription, minAmount.toString(),
                maxAmount.toString(), commission.toString(), currency);
        createPayment(payment);

        dataBaseService.getCurrencyDao().delete(currency);
        payment = dataBaseService.getPaymentDao().getById(dataBaseService.getPaymentDao().selectAll().get(0).getId());
        asserts.checkObject(paymentName, payment.getName(), "Payment name");
        asserts.checkObject(paymentDescription, payment.getDescription(), "Payment description");
        asserts.checkObject(minAmount, payment.getMinAmount(), "Payment min amount");
        asserts.checkObject(maxAmount, payment.getMaxAmount(), "Payment max amount");
        asserts.checkObject(commission, payment.getCommission(), "Payment commission");
        asserts.checkNull(payment.getCurrency(), "Payment currency");
    }

    @Description("Select payment with deleted currency test")
    @ParameterizedTest
    @ArgumentsSource(SelectPaymentDeletedCurrencyArgumentsProvider.class)
    public void selectPaymentDeletedCurrencyTest(String name, String currency, int expectedPaymentCount) {
        testUtils.logTestStart("Select payment with deleted currency test");
        Currency currency1 = new Currency(643, "RUB");
        Currency currency2 = new Currency(891, "BYN");
        createCurrency(currency1);
        createCurrency(currency2);

        Payment payment1 = new Payment("Payment1", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment2 = new Payment("Payment2", "Test description",
                "100.05", "999.99", "1.5", currency2);
        Payment payment3 = new Payment("Payment3", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment4 = new Payment("Payment4", "Test description",
                "100.05", "999.99", "1.5", currency2);

        createPayment(payment1);
        createPayment(payment2);
        createPayment(payment3);
        createPayment(payment4);

        dataBaseService.getCurrencyDao().delete(currency1.getCode());

        List<Payment> payments = dataBaseService.getPaymentDao().select(name, currency);
        paymentAsserts.checkPaymentCount(expectedPaymentCount, payments);
    }

    @Description("Select payments count with name \"{0}\" and currency \"{1}\" test")
    @ParameterizedTest
    @ArgumentsSource(SelectPaymentNameAndCurrencyArgumentsProvider.class)
    public void selectPaymentsCountWithNameAndCurrencyTest(String name, String currency, int expectedPaymentCount) {
        testUtils.logTestStart("Select payments count with name and currency test");
        Currency currency1 = new Currency(643, "RUB");
        Currency currency2 = new Currency(891, "BYN");
        createCurrency(currency1);
        createCurrency(currency2);

        Payment payment1 = new Payment("Payment1", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment2 = new Payment("Payment2", "Test description",
                "100.05", "999.99", "1.5", currency2);
        Payment payment3 = new Payment("Payment3", "Test description",
                "100.05", "999.99", "1.5", currency1);
        Payment payment4 = new Payment("Payment4", "Test description",
                "100.05", "999.99", "1.5", currency2);

        createPayment(payment1);
        createPayment(payment2);
        createPayment(payment3);
        createPayment(payment4);

        int payments = dataBaseService.getPaymentDao().selectCount(name, currency);
        asserts.checkObject(expectedPaymentCount, payments, "Number of payments");
    }

}
