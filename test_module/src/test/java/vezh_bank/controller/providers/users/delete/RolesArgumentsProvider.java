package vezh_bank.controller.providers.users.delete;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.Role;

import java.util.stream.Stream;

public class RolesArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(Role.CLIENT.toString()), // TODO: only admin has access to users
                Arguments.of(Role.EMPLOYEE.toString()),
                Arguments.of(Role.ADMIN.toString())
        );
    }
}
