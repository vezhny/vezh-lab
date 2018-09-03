package vezh_bank.persistence.providers.user_request;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.UserRequestStatus;

import java.util.stream.Stream;

public class SelectRequestWirhPageArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(1, 4, "", "", "", "", 4),
                Arguments.of(2, 4, "", "", "", "", 4),
                Arguments.of(1, 4, "1", "", "", "", 3),
                Arguments.of(1, 4, "2", "", "", "", 3),
                Arguments.of(1, 4, "3", "", "", "", 2),
                Arguments.of(1, 4, "", "", UserRequestStatus.OPEN.toString(), "", 4),
                Arguments.of(2, 4, "", "", UserRequestStatus.OPEN.toString(), "", 1),
                Arguments.of(1, 4, "", "", UserRequestStatus.IN_PROGRESS.toString(), "", 1),
                Arguments.of(1, 4, "", "", UserRequestStatus.CLOSED.toString(), "", 2),
                Arguments.of(1, 4, "", "", "", "Request", 4),
                Arguments.of(2, 4, "", "", "", "Request", 4),
                Arguments.of(1, 4, "", "", "", "Request data", 4),
                Arguments.of(2, 4, "", "", "", "Request data", 4),
                Arguments.of(1, 4, "", "", "", "Request data 1", 1),
                Arguments.of(1, 4, "1", "", UserRequestStatus.OPEN.toString(), "", 1),
                Arguments.of(1, 4, "1", "", UserRequestStatus.IN_PROGRESS.toString(), "", 1),
                Arguments.of(1, 4, "1", "", UserRequestStatus.CLOSED.toString(), "", 1),
                Arguments.of(1, 4, "2", "", UserRequestStatus.OPEN.toString(), "", 2),
                Arguments.of(1, 4, "2", "", UserRequestStatus.IN_PROGRESS.toString(), "", 0),
                Arguments.of(1, 4, "2", "", UserRequestStatus.CLOSED.toString(), "", 1),
                Arguments.of(1, 4, "3", "", UserRequestStatus.OPEN.toString(), "", 2),
                Arguments.of(1, 4, "3", "", UserRequestStatus.IN_PROGRESS.toString(), "", 0),
                Arguments.of(1, 4, "3", "", UserRequestStatus.CLOSED.toString(), "", 0),
                Arguments.of(1, 4, "1", "", "", "Request", 3),
                Arguments.of(1, 4, "1", "", "", "Request data", 3),
                Arguments.of(1, 4, "1", "", "", "Request data 1", 1),
                Arguments.of(1, 4, "2", "", "", "Request data 1", 0),
                Arguments.of(1, 4, "", "", UserRequestStatus.OPEN.toString(), "Request", 4),
                Arguments.of(2, 4, "", "", UserRequestStatus.OPEN.toString(), "Request", 1),
                Arguments.of(1, 4, "", "", UserRequestStatus.OPEN.toString(), "Request data", 4),
                Arguments.of(2, 4, "", "", UserRequestStatus.OPEN.toString(), "Request data", 1),
                Arguments.of(1, 4, "", "", UserRequestStatus.OPEN.toString(), "Request data 1", 1),
                Arguments.of(1, 4, "", "", UserRequestStatus.OPEN.toString(), "Request data 2", 1),
                Arguments.of(1, 4, "", "", UserRequestStatus.OPEN.toString(), "Request data 4", 0),
                Arguments.of(1, 4, "", "", UserRequestStatus.IN_PROGRESS.toString(), "Request", 1),
                Arguments.of(1, 4, "", "", UserRequestStatus.IN_PROGRESS.toString(), "Request data", 1),
                Arguments.of(1, 4, "", "", UserRequestStatus.IN_PROGRESS.toString(), "Request data 1", 0),
                Arguments.of(1, 4, "", "", UserRequestStatus.IN_PROGRESS.toString(), "Request data 4", 1),
                Arguments.of(1, 4, "", "", UserRequestStatus.CLOSED.toString(), "Request", 2),
                Arguments.of(1, 4, "", "", UserRequestStatus.CLOSED.toString(), "Request data", 2),
                Arguments.of(1, 4, "", "", UserRequestStatus.CLOSED.toString(), "Request data 1", 0),
                Arguments.of(1, 4, "", "", UserRequestStatus.CLOSED.toString(), "Request data 5", 1),
                Arguments.of(1, 4, "1", "", UserRequestStatus.OPEN.toString(), "Request", 1),
                Arguments.of(1, 4, "1", "", UserRequestStatus.OPEN.toString(), "Request data", 1),
                Arguments.of(1, 4, "1", "", UserRequestStatus.OPEN.toString(), "Request data 1", 1),
                Arguments.of(1, 4, "1", "", UserRequestStatus.OPEN.toString(), "Request data 2", 0),
                Arguments.of(1, 4, "2", "", UserRequestStatus.OPEN.toString(), "Request", 1),
                Arguments.of(1, 4, "2", "", UserRequestStatus.OPEN.toString(), "Request data 2", 1),
                Arguments.of(1, 4, "2", "", UserRequestStatus.OPEN.toString(), "Request data 1", 0),
                Arguments.of(1, 4, "1", "", UserRequestStatus.IN_PROGRESS.toString(), "Request", 1),
                Arguments.of(1, 4, "1", "", UserRequestStatus.IN_PROGRESS.toString(), "Request data 1", 0),
                Arguments.of(1, 4, "1", "", UserRequestStatus.IN_PROGRESS.toString(), "Request data 4", 1),
                Arguments.of(1, 4, "2", "", UserRequestStatus.IN_PROGRESS.toString(), "Request", 0),
                Arguments.of(1, 4, "1", "", UserRequestStatus.CLOSED.toString(), "Request", 1),
                Arguments.of(1, 4, "1", "", UserRequestStatus.CLOSED.toString(), "Request data 5", 1),
                Arguments.of(1, 4, "1", "", UserRequestStatus.CLOSED.toString(), "Request data 6", 0),
                Arguments.of(1, 4, "2", "", UserRequestStatus.CLOSED.toString(), "Request data 7", 1),
                Arguments.of(1, 4, "2", "", UserRequestStatus.CLOSED.toString(), "Request data 8", 0)
        );
    }
}
