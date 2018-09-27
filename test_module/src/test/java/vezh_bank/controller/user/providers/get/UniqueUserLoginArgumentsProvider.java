package vezh_bank.controller.user.providers.get;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static java.lang.String.format;
import static vezh_bank.constants.ExceptionMessages.INVALID_PARAMETER;
import static vezh_bank.constants.ExceptionMessages.MISSING_PARAMETER;
import static vezh_bank.constants.RequestParams.LOGIN;

public class UniqueUserLoginArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of("", format(INVALID_PARAMETER, LOGIN)),
                Arguments.of("tes", format(INVALID_PARAMETER, LOGIN)),
                Arguments.of("BenderBenderBenderBen", format(INVALID_PARAMETER, LOGIN)),
                Arguments.of("Vezhny!", format(INVALID_PARAMETER, LOGIN))
        );
    }
}
