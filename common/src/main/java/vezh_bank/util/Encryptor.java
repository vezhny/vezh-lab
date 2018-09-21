package vezh_bank.util;

import org.jasypt.util.text.StrongTextEncryptor;

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
        String decryptedValue = encryptor.decrypt(value);
        logger.info("Decrypted value: " + decryptedValue);
        return decryptedValue;
    }
}
