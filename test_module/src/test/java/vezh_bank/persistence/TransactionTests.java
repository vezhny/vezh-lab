package vezh_bank.persistence;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import vezh_bank.enums.TransactionStatus;
import vezh_bank.enums.TransactionType;
import vezh_bank.extended_tests.PersistenceTest;
import vezh_bank.persistence.entity.Transaction;
import vezh_bank.persistence.providers.payment.SelectTransactionPagesArgumentsProvider;
import vezh_bank.persistence.providers.transaction.SelectTransactionArgumentsProvider;

import java.util.List;

@Feature("Transaction persistence")
@Link("https://github.com/vezhny/vezh-lab/issues/5")
public class TransactionTests extends PersistenceTest {

    @Description("Insert transaction test")
    @Test
    public void insertTrxTest() {
        testUtils.logTestStart("Insert transaction test");
        TransactionType transactionType = TransactionType.PAYMENT;
        String trxData = "Test data";
        TransactionStatus transactionStatus = TransactionStatus.SUCCESS;
        createTransaction(new Transaction(transactionType, trxData, transactionStatus));

        List<Transaction> transactions = dataBaseService.getTransactionDao().selectAll();
        transactionAsserts.checkTransactionCount(1, transactions);
        transactionAsserts.checkTransaction(transactionType, trxData, transactionStatus, transactions.get(0));
    }

    @Description("Select by ID test")
    @Test
    public void selectByIdTest() {
        testUtils.logTestStart("Select by ID test");
        TransactionType transactionType = TransactionType.PAYMENT;
        String trxData = "Test data";
        TransactionStatus transactionStatus = TransactionStatus.SUCCESS;
        createTransaction(new Transaction(transactionType, trxData, transactionStatus));

        Transaction transaction = dataBaseService.getTransactionDao().selectAll().get(0);
        transaction = dataBaseService.getTransactionDao().getById(transaction.getId());
        transactionAsserts.checkTransaction(transactionType, trxData, transactionStatus, transaction);
    }

    @Description("Select with params test. Type: {0}, data: {2}, status: {3}")
    @ParameterizedTest
    @ArgumentsSource(SelectTransactionArgumentsProvider.class)
    public void selectTest(String type, String dateTime, String data, String status, int expectedTransactionsCount) {
        testUtils.logTestStart("Select with params test");

        Transaction transaction1 = new Transaction(TransactionType.PAYMENT, "Test data 1", TransactionStatus.SUCCESS);
        Transaction transaction2 = new Transaction(TransactionType.TRANSFER, "Test data 2", TransactionStatus.SUCCESS);
        Transaction transaction3 = new Transaction(TransactionType.PAYMENT, "Test data 3", TransactionStatus.FAIL);
        Transaction transaction4 = new Transaction(TransactionType.TRANSFER, "Test data 4", TransactionStatus.FAIL);
        Transaction transaction5 = new Transaction(TransactionType.PAYMENT, "Test data 5", TransactionStatus.SUCCESS);
        Transaction transaction6 = new Transaction(TransactionType.TRANSFER, "Test data 6", TransactionStatus.FAIL);
        Transaction transaction7 = new Transaction(TransactionType.TRANSFER, "Test data 7", TransactionStatus.SUCCESS);
        Transaction transaction8 = new Transaction(TransactionType.TRANSFER, "Test data 8", TransactionStatus.SUCCESS);

        createTransaction(transaction1);
        createTransaction(transaction2);
        createTransaction(transaction3);
        createTransaction(transaction4);
        createTransaction(transaction5);
        createTransaction(transaction6);
        createTransaction(transaction7);
        createTransaction(transaction8);

        List<Transaction> transactions = dataBaseService.getTransactionDao().select(type, dateTime, data, status);
        transactionAsserts.checkTransactionCount(expectedTransactionsCount, transactions);
    }

    @Description("Select with params test. Required page: {0}, rows on page: {1}, type: {2}, data: {4}, status: {5}")
    @ParameterizedTest
    @ArgumentsSource(SelectTransactionPagesArgumentsProvider.class)
    public void selectTest(int requiredPage, int rowsOnPage, String type, String dateTime,
                           String data, String status, int expectedTransactionsCount) {
        testUtils.logTestStart("Select with params and pages test");

        Transaction transaction1 = new Transaction(TransactionType.PAYMENT, "Test data 1", TransactionStatus.SUCCESS);
        Transaction transaction2 = new Transaction(TransactionType.TRANSFER, "Test data 2", TransactionStatus.SUCCESS);
        Transaction transaction3 = new Transaction(TransactionType.PAYMENT, "Test data 3", TransactionStatus.FAIL);
        Transaction transaction4 = new Transaction(TransactionType.TRANSFER, "Test data 4", TransactionStatus.FAIL);
        Transaction transaction5 = new Transaction(TransactionType.PAYMENT, "Test data 5", TransactionStatus.SUCCESS);
        Transaction transaction6 = new Transaction(TransactionType.TRANSFER, "Test data 6", TransactionStatus.FAIL);
        Transaction transaction7 = new Transaction(TransactionType.TRANSFER, "Test data 7", TransactionStatus.SUCCESS);
        Transaction transaction8 = new Transaction(TransactionType.TRANSFER, "Test data 8", TransactionStatus.SUCCESS);

        createTransaction(transaction1);
        createTransaction(transaction2);
        createTransaction(transaction3);
        createTransaction(transaction4);
        createTransaction(transaction5);
        createTransaction(transaction6);
        createTransaction(transaction7);
        createTransaction(transaction8);

        List<Transaction> transactions = dataBaseService.getTransactionDao().select(requiredPage, rowsOnPage,
                type, dateTime, data, status);
        transactionAsserts.checkTransactionCount(expectedTransactionsCount, transactions);
    }

    @Description("Select count test")
    @Test
    public void selectCountTest() {
        testUtils.logTestStart("Select count test");

        Transaction transaction1 = new Transaction(TransactionType.PAYMENT, "Test data 1", TransactionStatus.SUCCESS);
        Transaction transaction2 = new Transaction(TransactionType.TRANSFER, "Test data 2", TransactionStatus.SUCCESS);
        Transaction transaction3 = new Transaction(TransactionType.PAYMENT, "Test data 3", TransactionStatus.FAIL);
        Transaction transaction4 = new Transaction(TransactionType.TRANSFER, "Test data 4", TransactionStatus.FAIL);
        Transaction transaction5 = new Transaction(TransactionType.PAYMENT, "Test data 5", TransactionStatus.SUCCESS);

        createTransaction(transaction1);
        createTransaction(transaction2);
        createTransaction(transaction3);
        createTransaction(transaction4);
        createTransaction(transaction5);

        asserts.checkObject(5, dataBaseService.getTransactionDao().selectCount(),
                "Number of transactions");
    }

    @Description("Select count with params test. Type: {0}, data: {2}, status: {3}")
    @ParameterizedTest
    @ArgumentsSource(SelectTransactionArgumentsProvider.class)
    public void selectCountTest(String type, String dateTime, String data, String status, int expectedTransactionsCount) {
        testUtils.logTestStart("Select count with params test");

        Transaction transaction1 = new Transaction(TransactionType.PAYMENT, "Test data 1", TransactionStatus.SUCCESS);
        Transaction transaction2 = new Transaction(TransactionType.TRANSFER, "Test data 2", TransactionStatus.SUCCESS);
        Transaction transaction3 = new Transaction(TransactionType.PAYMENT, "Test data 3", TransactionStatus.FAIL);
        Transaction transaction4 = new Transaction(TransactionType.TRANSFER, "Test data 4", TransactionStatus.FAIL);
        Transaction transaction5 = new Transaction(TransactionType.PAYMENT, "Test data 5", TransactionStatus.SUCCESS);
        Transaction transaction6 = new Transaction(TransactionType.TRANSFER, "Test data 6", TransactionStatus.FAIL);
        Transaction transaction7 = new Transaction(TransactionType.TRANSFER, "Test data 7", TransactionStatus.SUCCESS);
        Transaction transaction8 = new Transaction(TransactionType.TRANSFER, "Test data 8", TransactionStatus.SUCCESS);

        createTransaction(transaction1);
        createTransaction(transaction2);
        createTransaction(transaction3);
        createTransaction(transaction4);
        createTransaction(transaction5);
        createTransaction(transaction6);
        createTransaction(transaction7);
        createTransaction(transaction8);

        int transactions = dataBaseService.getTransactionDao().selectCount(type, dateTime, data, status);
        asserts.checkObject(expectedTransactionsCount, transactions, "number of transactions");
    }
}
