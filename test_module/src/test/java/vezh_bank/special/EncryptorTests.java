package vezh_bank.special;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import vezh_bank.extended_tests.RootTest;
import vezh_bank.util.Encryptor;

@Epic("Special")
@Story("Encrypting")
public class EncryptorTests extends RootTest {

    @Severity(SeverityLevel.BLOCKER)
    @Feature("Encrypting password")
    @Link(name = "Issue", value = "https://github.com/vezhny/vezh-lab/issues/15", url = "https://github.com/vezhny/vezh-lab/issues/15")
    @Description("Encryptor test")
    @Test
    public void encryptorTest() {
        testUtils.logTestStart("Encryptor test");

        Encryptor encryptor = new Encryptor();

        String value = "some value";
        String encryptedValue = encryptor.encrypt(value);

        asserts.checkObject(value, encryptor.decrypt(encryptedValue), "decrypted value");
    }
}
