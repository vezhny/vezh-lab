package vezh_bank.persistence.providers.transaction;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.TransactionStatus;
import vezh_bank.enums.TransactionType;

import java.util.stream.Stream;

public class SelectTransactionArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of("", "", "", "", 8),
            Arguments.of(TransactionType.PAYMENT.toString(), "", "", "", 3),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "", "", 5),
            Arguments.of("", "", "Test data", "", 8),
            Arguments.of("", "", "Test data 1", "", 1),
            Arguments.of(TransactionType.PAYMENT.toString(), "", "Test data", "", 3),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "Test data", "", 5),
            Arguments.of(TransactionType.PAYMENT.toString(), "", "Test data 1", "", 1),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "Test data 1", "", 0),
            Arguments.of("", "", "", TransactionStatus.SUCCESS.toString(), 5),
            Arguments.of("", "", "", TransactionStatus.FAIL.toString(), 3),
            Arguments.of(TransactionType.PAYMENT.toString(), "", "", TransactionStatus.SUCCESS.toString(), 2),
            Arguments.of(TransactionType.PAYMENT.toString(), "", "", TransactionStatus.FAIL.toString(), 1),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "", TransactionStatus.SUCCESS.toString(), 3),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "", TransactionStatus.FAIL.toString(), 2),
            Arguments.of("", "", "Test data", TransactionStatus.SUCCESS.toString(), 5),
            Arguments.of("", "", "Test data", TransactionStatus.FAIL.toString(), 3),
            Arguments.of("", "", "Test data 1", TransactionStatus.SUCCESS.toString(), 1),
            Arguments.of("", "", "Test data 1", TransactionStatus.FAIL.toString(), 0),
            Arguments.of(TransactionType.PAYMENT.toString(), "", "Test data", TransactionStatus.SUCCESS.toString(), 2),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "Test data", TransactionStatus.SUCCESS.toString(), 3),
            Arguments.of(TransactionType.PAYMENT.toString(), "", "Test data", TransactionStatus.FAIL.toString(), 1),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "Test data", TransactionStatus.FAIL.toString(), 2),
            Arguments.of(TransactionType.PAYMENT.toString(), "", "Test data 1", TransactionStatus.SUCCESS.toString(), 1),
            Arguments.of(TransactionType.PAYMENT.toString(), "", "Test data 1", TransactionStatus.FAIL.toString(), 0),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "Test data 1", TransactionStatus.SUCCESS.toString(), 0),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "Test data 1", TransactionStatus.FAIL.toString(), 0),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "Test data 2", TransactionStatus.SUCCESS.toString(), 1),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "Test data 2", TransactionStatus.FAIL.toString(), 0),
            Arguments.of(TransactionType.PAYMENT.toString(), "", "Test data 3", TransactionStatus.FAIL.toString(), 1),
            Arguments.of(TransactionType.TRANSFER.toString(), "", "Test data 4", TransactionStatus.FAIL.toString(), 1)
        );
    }
}
