package vezh_bank.persistence.providers.event;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.EventType;

import java.util.stream.Stream;

public class EventPagesArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of(1, 4, "", "", "", 4),
            Arguments.of(1, 4, null, null, null, 4),
            Arguments.of(2, 4, "", "", "", 3),
            Arguments.of(1, 4, EventType.USER_SIGN_IN.toString(), "", "", 3),
            Arguments.of(1, 4, EventType.USER_SIGN_UP.toString(), "", "", 1),
            Arguments.of(1, 4, "", "", "User ID:", 4),
            Arguments.of(2, 4, "", "", "User ID:", 3)
        );
    }
}
