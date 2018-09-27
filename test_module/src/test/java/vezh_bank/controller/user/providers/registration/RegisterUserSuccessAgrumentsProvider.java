package vezh_bank.controller.user.providers.registration;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class RegisterUserSuccessAgrumentsProvider implements ArgumentsProvider {

    /**
     * Arguments: description, login, role, firstName
     * @param extensionContext
     * @return
     * @throws Exception
     */
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of("Login: \"nagibator3000\"", "nagibator3000", "CLIENT", "Artsiom"),
                Arguments.of("Role: \"CLIENT\"", "vezhny", "CLIENT", "Artsiom"),
                Arguments.of("Role: \"EMPLOYEE\"", "vezhny", "EMPLOYEE", "Artsiom"),
                Arguments.of("Role: \"ADMIN\"", "vezhny", "ADMIN", "Artsiom"),
                Arguments.of("First name: \"Ann-Marya\"", "vezhny", "ADMIN", "An-Marya")
        );
    }
}
