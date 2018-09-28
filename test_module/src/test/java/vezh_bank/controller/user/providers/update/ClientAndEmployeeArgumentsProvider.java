package vezh_bank.controller.user.providers.update;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.Role;

import java.util.stream.Stream;

public class ClientAndEmployeeArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(Role.CLIENT),
                Arguments.of(Role.EMPLOYEE)
        );
    }
}
