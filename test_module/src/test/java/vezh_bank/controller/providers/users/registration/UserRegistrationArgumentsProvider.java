package vezh_bank.controller.providers.users.registration;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.Role;

import java.util.stream.Stream;

import static vezh_bank.constants.ExceptionMessages.ACCESS_DENIED;

public class UserRegistrationArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(Role.CLIENT.toString(), Role.CLIENT.toString(), 400, ACCESS_DENIED, 1, 0),
                Arguments.of(Role.EMPLOYEE.toString(), Role.CLIENT.toString(), 400, ACCESS_DENIED, 1, 0),
                Arguments.of(Role.ADMIN.toString(), Role.CLIENT.toString(), 200, null, 2, 1),
                Arguments.of(Role.CLIENT.toString(), Role.EMPLOYEE.toString(), 400, ACCESS_DENIED, 1, 0),
                Arguments.of(Role.EMPLOYEE.toString(), Role.EMPLOYEE.toString(), 400, ACCESS_DENIED, 1, 0),
                Arguments.of(Role.ADMIN.toString(), Role.EMPLOYEE.toString(), 200, null, 2, 1),
                Arguments.of(Role.CLIENT.toString(), Role.ADMIN.toString(), 400, ACCESS_DENIED, 1, 0),
                Arguments.of(Role.EMPLOYEE.toString(), Role.ADMIN.toString(), 400, ACCESS_DENIED, 1, 0),
                Arguments.of(Role.ADMIN.toString(), Role.ADMIN.toString(), 200, null, 2, 1)
        );
    }
}
