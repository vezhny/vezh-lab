package vezh_bank.persistence.providers.user;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import vezh_bank.enums.Role;

import java.util.stream.Stream;

import static vezh_bank.util.TypeConverter.booleanToInt;

public class SelectUserPageArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(1, 5, "", "", "", "", 5),
                Arguments.of(2, 5, "", "", "", "", 5),
                Arguments.of(1, 5, "Login", "", "", "", 5),
                Arguments.of(2, 5, "Login", "", "", "", 5),
                Arguments.of(1, 5, "Login1", "", "", "", 1),
                Arguments.of(1, 5, "", Role.CLIENT.toString(), "", "", 4),
                Arguments.of(1, 5, "", Role.EMPLOYEE.toString(), "", "", 3),
                Arguments.of(1, 5, "", Role.ADMIN.toString(), "", "", 3),
                Arguments.of(1, 5, "", "", String.valueOf(booleanToInt(false)), "", 5),
                Arguments.of(2, 5, "", "", String.valueOf(booleanToInt(false)), "", 5),
                Arguments.of(1, 5, "", "", String.valueOf(booleanToInt(true)), "", 0),
                Arguments.of(1, 5, "", "", "", "User data", 5),
                Arguments.of(2, 5, "", "", "", "User data", 5),
                Arguments.of(1, 5, "", "", "", "User", 5),
                Arguments.of(2, 5, "", "", "", "User", 5),
                Arguments.of(1, 5, "", "", "", "User data 1", 1),
                Arguments.of(1, 5, "Login", Role.CLIENT.toString(), "", "", 4),
                Arguments.of(1, 5, "Login1", Role.CLIENT.toString(), "", "", 1),
                Arguments.of(1, 5, "Login2", Role.CLIENT.toString(), "", "", 0),
                Arguments.of(1, 5, "Login", Role.EMPLOYEE.toString(), "", "", 3),
                Arguments.of(1, 5, "Login1", Role.EMPLOYEE.toString(), "", "", 0),
                Arguments.of(1, 5, "Login2", Role.EMPLOYEE.toString(), "", "", 1),
                Arguments.of(1, 5, "Login", Role.ADMIN.toString(), "", "", 3),
                Arguments.of(1, 5, "Login1", Role.ADMIN.toString(), "", "", 0),
                Arguments.of(1, 5, "Login3", Role.ADMIN.toString(), "", "", 1),
                Arguments.of(1, 5, "Login", "", "", "User", 5),
                Arguments.of(1, 5, "Login", "", "", "User data", 5),
                Arguments.of(1, 5, "Login1", "", "", "User data", 1),
                Arguments.of(1, 5, "Login", "", "", "User data 1", 1),
                Arguments.of(1, 5, "Login1", "", "", "User data 1", 1),
                Arguments.of(1, 5, "", Role.CLIENT.toString(), "", "User", 4),
                Arguments.of(1, 5, "", Role.CLIENT.toString(), "", "User data", 4),
                Arguments.of(1, 5, "", Role.CLIENT.toString(), "", "User data 1", 1),
                Arguments.of(1, 5, "", Role.CLIENT.toString(), "", "User data 2", 0),
                Arguments.of(1, 5, "", Role.EMPLOYEE.toString(), "", "User", 3),
                Arguments.of(1, 5, "", Role.EMPLOYEE.toString(), "", "User data", 3),
                Arguments.of(1, 5, "", Role.EMPLOYEE.toString(), "", "User data 2", 1),
                Arguments.of(1, 5, "", Role.EMPLOYEE.toString(), "", "User data 1", 0),
                Arguments.of(1, 5, "", Role.ADMIN.toString(), "", "User", 3),
                Arguments.of(1, 5, "", Role.ADMIN.toString(), "", "User data", 3),
                Arguments.of(1, 5, "", Role.ADMIN.toString(), "", "User data 3", 1),
                Arguments.of(1, 5, "", Role.ADMIN.toString(), "", "User data 2", 0),
                Arguments.of(1, 5, "Login", Role.CLIENT.toString(), "", "User", 4),
                Arguments.of(1, 5, "Login1", Role.CLIENT.toString(), "", "User", 1),
                Arguments.of(1, 5, "Login2", Role.CLIENT.toString(), "", "User", 0),
                Arguments.of(1, 5, "Login", Role.EMPLOYEE.toString(), "", "User", 3),
                Arguments.of(1, 5, "Login1", Role.EMPLOYEE.toString(), "", "User", 0),
                Arguments.of(1, 5, "Login2", Role.EMPLOYEE.toString(), "", "User", 1),
                Arguments.of(1, 5, "Login", Role.ADMIN.toString(), "", "User", 3),
                Arguments.of(1, 5, "Login3", Role.ADMIN.toString(), "", "User", 1),
                Arguments.of(1, 5, "Login2", Role.ADMIN.toString(), "", "User", 0),
                Arguments.of(1, 5, "Login", Role.CLIENT.toString(), "", "User data", 4),
                Arguments.of(1, 5, "Login1", Role.CLIENT.toString(), "", "User data", 1),
                Arguments.of(1, 5, "Login", Role.EMPLOYEE.toString(), "", "User data", 3),
                Arguments.of(1, 5, "Login2", Role.EMPLOYEE.toString(), "", "User data", 1),
                Arguments.of(1, 5, "Login", Role.ADMIN.toString(), "", "User data", 3),
                Arguments.of(1, 5, "Login3", Role.ADMIN.toString(), "", "User data", 1),
                Arguments.of(1, 5, "Login", Role.CLIENT.toString(), "", "User data 1", 1),
                Arguments.of(1, 5, "Login1", Role.CLIENT.toString(), "", "User data 1", 1),
                Arguments.of(1, 5, "Login2", Role.CLIENT.toString(), "", "User data 1", 0),
                Arguments.of(1, 5, "Login1", Role.CLIENT.toString(), "", "User data 2", 0),
                Arguments.of(1, 5, "Login1", Role.EMPLOYEE.toString(), "", "User data 1", 0),
                Arguments.of(1, 5, "Login2", Role.EMPLOYEE.toString(), "", "User data 2", 1),
                Arguments.of(1, 5, "Login2", Role.EMPLOYEE.toString(), "", "User data 1", 0),
                Arguments.of(1, 5, "Login1", Role.EMPLOYEE.toString(), "", "User data 2", 0),
                Arguments.of(1, 5, "Login1", Role.CLIENT.toString(), String.valueOf(booleanToInt(false)), "User data 1", 1),
                Arguments.of(1, 5, "Login1", Role.CLIENT.toString(), String.valueOf(booleanToInt(true)), "User data 1", 0)
        );
    }
}
