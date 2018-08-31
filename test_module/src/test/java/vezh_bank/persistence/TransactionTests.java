package vezh_bank.persistence;

import org.junit.jupiter.api.Assertions;
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

public class TransactionTests extends PersistenceTest {

    @Test
    public void insertTrxTest() {
        testUtils.logTestStart("Insert transaction test");
        TransactionType transactionType = TransactionType.PAYMENT;
        String trxData = "Test data";
        TransactionStatus transactionStatus = TransactionStatus.SUCCESS;
        createTransaction(new Transaction(transactionType, trxData, transactionStatus));

        List<Transaction> transactions = dataBaseService.getTransactionDao().selectAll();
        checkTransactionCount(1, transactions);
        checkTransaction(transactionType, trxData, transactionStatus, transactions.get(0));
    }

    @Test
    public void selectByIdTest() {
        testUtils.logTestStart("Select by ID test");
        TransactionType transactionType = TransactionType.PAYMENT;
        String trxData = "Test data";
        TransactionStatus transactionStatus = TransactionStatus.SUCCESS;
        createTransaction(new Transaction(transactionType, trxData, transactionStatus));

        Transaction transaction = dataBaseService.getTransactionDao().selectAll().get(0);
        transaction = dataBaseService.getTransactionDao().getById(transaction.getId());
        checkTransaction(transactionType, trxData, transactionStatus, transaction);
    }

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
        checkTransactionCount(expectedTransactionsCount, transactions);
    }

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
        checkTransactionCount(expectedTransactionsCount, transactions);
    }

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

        Assertions.assertEquals(5, dataBaseService.getTransactionDao().selectCount(),
                "Number of transactions");
    }
}
