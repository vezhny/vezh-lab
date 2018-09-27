package vezh_bank.controller.providers.users.get;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.Role;

import java.util.stream.Stream;

public class GetUsersArgumentsProvider implements ArgumentsProvider {

    /**
     * params: requiredPage, login, role, blocked, data
     * expectedUsersCount, expectedCurrentPage, expectedNumberOfPages
     * @param extensionContext
     * @return
     * @throws Exception
     */
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(1, "", "", "", "", 12, 1, 1),
                Arguments.of(2, "", "", "", "", 12, 1, 1),
                Arguments.of(0, "", "", "", "", 12, 1, 1),
                Arguments.of(-1, "", "", "", "", 12, 1, 1),
                Arguments.of(1, "login", "", "", "", 11, 1, 1),
                Arguments.of(1, "login1", "", "", "", 3, 1, 1),
                Arguments.of(1, null, "", "", "", 12, 1, 1),
                Arguments.of(1, "", Role.CLIENT.toString(), "", "", 4, 1, 1),
                Arguments.of(1, "", Role.ADMIN.toString(), "", "", 4, 1, 1),
                Arguments.of(1, "", Role.EMPLOYEE.toString(), "", "", 4, 1, 1),
                Arguments.of(1, "", null, "", "", 12, 1, 1),
                Arguments.of(1, "", "", "", "firstName", 12, 1, 1),
                Arguments.of(1, "", "", "", "firstName1", 4, 1, 1),
                Arguments.of(1, "", "", "", "1999", 11, 1, 1),
                Arguments.of(1, "", "", "", "middleName", 12, 1, 1),
                Arguments.of(1, "", "", "", "middleName1", 4, 1, 1),
                Arguments.of(1, "", "", "", "patronymic", 12, 1, 1),
                Arguments.of(1, "", "", "", "patronymic1", 4, 1, 1),
                Arguments.of(1, "", "", "", "10.10.1999", 4, 1, 1),
                Arguments.of(1, "", "", "", "country", 12, 1, 1),
                Arguments.of(1, "", "", "", "country1", 4, 1, 1),
                Arguments.of(1, "", "", "", "+36985219", 3, 1, 1),
                Arguments.of(1, "", "", "", "test3@test.com", 3, 1, 1)
        );
    }
}
