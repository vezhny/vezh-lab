package vezh_bank.persistence.providers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CurrencySearchArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of("6", "RU", 1),
            Arguments.of("", "RU", 2),
            Arguments.of("5", "", 1),
            Arguments.of("", "", 2),
            Arguments.of("5", "RUB", 0)
        );
    }
}
