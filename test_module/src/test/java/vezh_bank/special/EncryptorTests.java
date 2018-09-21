package vezh_bank.special;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import vezh_bank.extended_tests.RootTest;
import vezh_bank.util.Encryptor;

@Feature("Encrypting")
public class EncryptorTests extends RootTest {

    @Description("Encryptor test")
    @Test
    public void encryptorTest() {
        testUtils.logTestStart("Encryptor test");

        Encryptor encryptor = new Encryptor();

        String value = "some value";
        String encryptedValue = encryptor.encrypt(value);

        Assertions.assertEquals(value, encryptor.decrypt(encryptedValue));
    }
}
