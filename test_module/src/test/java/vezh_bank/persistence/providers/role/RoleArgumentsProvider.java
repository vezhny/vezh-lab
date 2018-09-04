package vezh_bank.persistence.providers.role;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.Role;

import java.util.stream.Stream;

public class RoleArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
            Arguments.of(1, Role.CLIENT),
            Arguments.of(2, Role.EMPLOYEE),
            Arguments.of(3, Role.ADMIN)
        );
    }
}
