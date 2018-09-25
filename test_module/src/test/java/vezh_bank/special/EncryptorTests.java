package vezh_bank.special;

import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import vezh_bank.extended_tests.RootTest;
import vezh_bank.util.Encryptor;

@Story("Encrypting")
public class EncryptorTests extends RootTest {

    @Description("Encryptor test")
    @Link(url = "https://github.com/vezhny/vezh-lab/issues/15")
    @Test
    public void encryptorTest() {
        testUtils.logTestStart("Encryptor test");

        Encryptor encryptor = new Encryptor();

        String value = "some value";
        String encryptedValue = encryptor.encrypt(value);

        asserts.checkObject(value, encryptor.decrypt(encryptedValue), "decrypted value");
    }
}
