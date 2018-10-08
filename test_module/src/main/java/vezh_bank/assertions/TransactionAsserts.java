package vezh_bank.assertions;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import vezh_bank.enums.TransactionStatus;
import vezh_bank.enums.TransactionType;
import vezh_bank.persistence.entity.Transaction;

import java.util.List;

public class TransactionAsserts extends Asserts {

    @Step("Check transaction")
    public void checkTransaction(TransactionType expectedTrxType, String expectedTrxData,
                                 TransactionStatus expectedTrxStatus, Transaction actualTrx) {
        checkObject(expectedTrxType.toString(), actualTrx.getType(), "Transaction type");
        checkObject(expectedTrxData, actualTrx.getData(), "Transaction data");
        checkObject(expectedTrxStatus.toString(), actualTrx.getStatus(), "Transaction status");
    }

    public void checkTransactionCount(int expectedTransactionsCount, List<Transaction> transactions) {
        checkItemsCount(expectedTransactionsCount, transactions, "Number of transactions");
    }
}
