package vezh_bank.controller.event.providers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.EventType;

import java.util.stream.Stream;

public class EventControllerArgumentsProvider implements ArgumentsProvider {
    /**
     * requiredPage, EventType, EventData, expectedEventsCount, expectedCurrentPage, expectedPagesCount
     * @param extensionContext
     * @return
     * @throws Exception
     */
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of("", "", "", 3, 1, 1),
            Arguments.of(null, "", "", 3, 1, 1),
            Arguments.of(null, null, null, 3, 1, 1),
            Arguments.of(String.valueOf(1), "", "", 3, 1, 1),
            Arguments.of(String.valueOf(2), "", "", 3, 1, 1),
            Arguments.of(String.valueOf(0), "", "", 3, 1, 1),
            Arguments.of(String.valueOf(-1), "", "", 3, 1, 1),
            Arguments.of("", EventType.ACTIVATING_CARD.toString(), "", 1, 1, 1),
            Arguments.of(String.valueOf(1), EventType.ACTIVATING_CARD.toString(), "", 1, 1, 1),
            Arguments.of(String.valueOf(2), EventType.ACTIVATING_CARD.toString(), "", 1, 1, 1),
            Arguments.of("", "", "some", 3, 1, 1),
            Arguments.of(String.valueOf(1), "", "some", 3, 1, 1),
            Arguments.of(String.valueOf(2), "", "some", 3, 1, 1),
            Arguments.of("", "", "some data", 3, 1, 1),
            Arguments.of(String.valueOf(1), "", "some data", 3, 1, 1),
            Arguments.of(String.valueOf(2), "", "some data", 3, 1, 1),
            Arguments.of("", EventType.ACTIVATING_CARD.toString(), "some data", 1, 1, 1),
            Arguments.of(String.valueOf(1), EventType.ACTIVATING_CARD.toString(), "some data", 1, 1, 1),
            Arguments.of(String.valueOf(2), EventType.ACTIVATING_CARD.toString(), "some data", 1, 1, 1),
            Arguments.of("", "", "some data 1", 1, 1, 1),
            Arguments.of(String.valueOf(1), "", "some data 1", 1, 1, 1),
            Arguments.of(String.valueOf(2), "", "some data 1", 1, 1, 1),
            Arguments.of("", EventType.ACTIVATING_CARD.toString(), "some data 1", 1, 1, 1),
            Arguments.of("", EventType.DELETE_CARD.toString(), "some data 1", 0, 1, 0),
            Arguments.of(String.valueOf(1), EventType.ACTIVATING_CARD.toString(), "some data 1", 1, 1, 1),
            Arguments.of(String.valueOf(2), EventType.ACTIVATING_CARD.toString(), "some data 1", 1, 1, 1),
            Arguments.of(String.valueOf(1), EventType.DELETE_CARD.toString(), "some data 1", 0, 1, 0),
            Arguments.of(String.valueOf(2), EventType.DELETE_CARD.toString(), "some data 1", 0, 1, 0)
        );
    }
}
