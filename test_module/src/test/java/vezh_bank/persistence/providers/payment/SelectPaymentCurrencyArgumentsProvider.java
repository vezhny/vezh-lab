package vezh_bank.persistence.providers.payment;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class SelectPaymentCurrencyArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of("", 4),
            Arguments.of("643", 2),
            Arguments.of("891", 2),
            Arguments.of("8", 2)
        );
    }
}
