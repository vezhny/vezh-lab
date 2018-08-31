package vezh_bank.persistence.providers.user;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.Role;

import java.util.stream.Stream;

import static vezh_bank.util.TypeConverter.booleanToInt;

public class SelectUserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of("", "", "", "", 10),
                Arguments.of("Login", "", "", "", 10),
                Arguments.of("Login1", "", "", "", 1),
                Arguments.of("", Role.CLIENT.toString(), "", "", 4),
                Arguments.of("", Role.EMPLOYEE.toString(), "", "", 3),
                Arguments.of("", Role.ADMIN.toString(), "", "", 3),
                Arguments.of("", "", String.valueOf(booleanToInt(false)), "", 10),
                Arguments.of("", "", String.valueOf(booleanToInt(true)), "", 0),
                Arguments.of("", "", "", "User data", 10),
                Arguments.of("", "", "", "User", 10),
                Arguments.of("", "", "", "User data 1", 1),
                Arguments.of("Login", Role.CLIENT.toString(), "", "", 4),
                Arguments.of("Login1", Role.CLIENT.toString(), "", "", 1),
                Arguments.of("Login2", Role.CLIENT.toString(), "", "", 0),
                Arguments.of("Login", Role.EMPLOYEE.toString(), "", "", 3),
                Arguments.of("Login1", Role.EMPLOYEE.toString(), "", "", 0),
                Arguments.of("Login2", Role.EMPLOYEE.toString(), "", "", 1),
                Arguments.of("Login", Role.ADMIN.toString(), "", "", 3),
                Arguments.of("Login1", Role.ADMIN.toString(), "", "", 0),
                Arguments.of("Login3", Role.ADMIN.toString(), "", "", 1),
                Arguments.of("Login", "", "", "User", 10),
                Arguments.of("Login", "", "", "User data", 10),
                Arguments.of("Login1", "", "", "User data", 1),
                Arguments.of("Login", "", "", "User data 1", 1),
                Arguments.of("Login1", "", "", "User data 1", 1),
                Arguments.of("", Role.CLIENT.toString(), "", "User", 4),
                Arguments.of("", Role.CLIENT.toString(), "", "User data", 4),
                Arguments.of("", Role.CLIENT.toString(), "", "User data 1", 1),
                Arguments.of("", Role.CLIENT.toString(), "", "User data 2", 0),
                Arguments.of("", Role.EMPLOYEE.toString(), "", "User", 3),
                Arguments.of("", Role.EMPLOYEE.toString(), "", "User data", 3),
                Arguments.of("", Role.EMPLOYEE.toString(), "", "User data 2", 1),
                Arguments.of("", Role.EMPLOYEE.toString(), "", "User data 1", 0),
                Arguments.of("", Role.ADMIN.toString(), "", "User", 3),
                Arguments.of("", Role.ADMIN.toString(), "", "User data", 3),
                Arguments.of("", Role.ADMIN.toString(), "", "User data 3", 1),
                Arguments.of("", Role.ADMIN.toString(), "", "User data 2", 0),
                Arguments.of("Login", Role.CLIENT.toString(), "", "User", 4),
                Arguments.of("Login1", Role.CLIENT.toString(), "", "User", 1),
                Arguments.of("Login2", Role.CLIENT.toString(), "", "User", 0),
                Arguments.of("Login", Role.EMPLOYEE.toString(), "", "User", 3),
                Arguments.of("Login1", Role.EMPLOYEE.toString(), "", "User", 0),
                Arguments.of("Login2", Role.EMPLOYEE.toString(), "", "User", 1),
                Arguments.of("Login", Role.ADMIN.toString(), "", "User", 3),
                Arguments.of("Login3", Role.ADMIN.toString(), "", "User", 1),
                Arguments.of("Login2", Role.ADMIN.toString(), "", "User", 0),
                Arguments.of("Login", Role.CLIENT.toString(), "", "User data", 4),
                Arguments.of("Login1", Role.CLIENT.toString(), "", "User data", 1),
                Arguments.of("Login", Role.EMPLOYEE.toString(), "", "User data", 3),
                Arguments.of("Login2", Role.EMPLOYEE.toString(), "", "User data", 1),
                Arguments.of("Login", Role.ADMIN.toString(), "", "User data", 3),
                Arguments.of("Login3", Role.ADMIN.toString(), "", "User data", 1),
                Arguments.of("Login", Role.CLIENT.toString(), "", "User data 1", 1),
                Arguments.of("Login1", Role.CLIENT.toString(), "", "User data 1", 1),
                Arguments.of("Login2", Role.CLIENT.toString(), "", "User data 1", 0),
                Arguments.of("Login1", Role.CLIENT.toString(), "", "User data 2", 0),
                Arguments.of("Login1", Role.EMPLOYEE.toString(), "", "User data 1", 0),
                Arguments.of("Login2", Role.EMPLOYEE.toString(), "", "User data 2", 1),
                Arguments.of("Login2", Role.EMPLOYEE.toString(), "", "User data 1", 0),
                Arguments.of("Login1", Role.EMPLOYEE.toString(), "", "User data 2", 0),
                Arguments.of("Login1", Role.CLIENT.toString(), String.valueOf(booleanToInt(false)), "User data 1", 1),
                Arguments.of("Login1", Role.CLIENT.toString(), String.valueOf(booleanToInt(true)), "User data 1", 0)
        );
    }
}
