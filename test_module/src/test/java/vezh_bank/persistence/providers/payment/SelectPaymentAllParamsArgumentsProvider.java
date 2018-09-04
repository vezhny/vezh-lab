package vezh_bank.persistence.providers.payment;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class SelectPaymentAllParamsArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of(1, 4, "", "", 4),
            Arguments.of(2, 4, "", "", 4),
            Arguments.of(1, 4, "Payment", "", 4),
            Arguments.of(2, 4, "Payment", "", 4),
            Arguments.of(1, 4, "Payment1", "", 1),
            Arguments.of(1, 4, "", "643", 4),
            Arguments.of(2, 3, "", "643", 1),
            Arguments.of(1, 3, "Payment", "643", 3),
            Arguments.of(2, 3, "Payment", "643", 1),
            Arguments.of(1, 4, "Payment1", "643", 1),
            Arguments.of(1, 4, "Payment1", "891", 0)
        );
    }
}
