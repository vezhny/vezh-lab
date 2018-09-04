package vezh_bank.persistence.providers.event;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.EventType;

import java.util.stream.Stream;

public class SelectEventArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of(EventType.USER_SIGN_IN.toString(), "", "User ID: 1", 1),
            Arguments.of(EventType.USER_SIGN_IN.toString(), "", "User ID:", 3),
            Arguments.of(EventType.USER_SIGN_IN.toString(), "", "", 3),
            Arguments.of("", "", "User ID: 1", 2),
            Arguments.of("", "", "User ID:", 7),
            Arguments.of("", "", "", 7),
            Arguments.of("", "", "User ID: 3", 3),
            Arguments.of(EventType.USER_SIGN_UP.toString(), "", "", 1)
        );
    }
}
