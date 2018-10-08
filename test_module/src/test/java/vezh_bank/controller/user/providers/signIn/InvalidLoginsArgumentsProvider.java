package vezh_bank.controller.user.providers.signIn;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class InvalidLoginsArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("tes"),
                Arguments.of("BenderBenderBenderBen"),
                Arguments.of("Vezhny!"),
                Arguments.of("login")
        );
    }
}
