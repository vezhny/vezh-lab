package vezh_bank.controller.user.providers.get;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.constants.ExceptionMessages;
import vezh_bank.enums.Role;

import java.util.stream.Stream;

public class GetUserWithoutAccessArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(Role.ADMIN, Role.ADMIN, 200, null),
                Arguments.of(Role.ADMIN, Role.EMPLOYEE, 200, null),
                Arguments.of(Role.ADMIN, Role.CLIENT, 200, null),
                Arguments.of(Role.EMPLOYEE, Role.ADMIN, 400, ExceptionMessages.ACCESS_DENIED),
                Arguments.of(Role.EMPLOYEE, Role.EMPLOYEE, 400, ExceptionMessages.ACCESS_DENIED),
                Arguments.of(Role.EMPLOYEE, Role.CLIENT, 400, ExceptionMessages.ACCESS_DENIED),
                Arguments.of(Role.CLIENT, Role.ADMIN, 400, ExceptionMessages.ACCESS_DENIED),
                Arguments.of(Role.CLIENT, Role.EMPLOYEE, 400, ExceptionMessages.ACCESS_DENIED),
                Arguments.of(Role.CLIENT, Role.CLIENT, 400, ExceptionMessages.ACCESS_DENIED)
        );
    }
}
