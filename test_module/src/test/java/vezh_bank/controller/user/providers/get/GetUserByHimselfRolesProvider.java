package vezh_bank.controller.user.providers.get;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.Role;

import java.util.stream.Stream;

public class GetUserByHimselfRolesProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(Role.EMPLOYEE),
                Arguments.of(Role.ADMIN),
                Arguments.of(Role.CLIENT)
        );
    }
}
