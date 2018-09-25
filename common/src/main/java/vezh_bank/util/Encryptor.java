package vezh_bank.util;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.StrongTextEncryptor;
import vezh_bank.constants.ExceptionMessages;

public class Encryptor {
    private StrongTextEncryptor encryptor;
    private Logger logger = Logger.getLogger(Encryptor.class);

    public Encryptor() {
        encryptor = new StrongTextEncryptor();
        encryptor.setPassword("password");
    }

    public String encrypt(String value) {
        logger.info("Encrypting \"" + value + "\"");
        String encryptedValue = encryptor.encrypt(value);
        logger.info("Encrypted value: " + encryptedValue);
        return encryptedValue;
    }

    public String decrypt(String value) {
        logger.info("Decrypting \"" + value + "\"");
        String decryptedValue;
        try {
            decryptedValue = encryptor.decrypt(value);
        } catch (EncryptionOperationNotPossibleException e) {
            logger.error(ExceptionMessages.unableToDecryptValue(value));
            decryptedValue = value;
        }
        logger.info("Decrypted value: " + decryptedValue);
        return decryptedValue;
    }
}
