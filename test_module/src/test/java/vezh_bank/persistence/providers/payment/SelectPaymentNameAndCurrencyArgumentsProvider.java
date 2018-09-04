package vezh_bank.persistence.providers.payment;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class SelectPaymentNameAndCurrencyArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of("", "", 4),
            Arguments.of("Payment", "", 4),
            Arguments.of("Payment1", "", 1),
            Arguments.of("", "643", 2),
            Arguments.of("", "891", 2),
            Arguments.of("Payment2", "643", 0),
            Arguments.of("Payment", "643", 2),
            Arguments.of("Payment1", "643", 1)
        );
    }
}
