package vezh_bank.util;

import org.jasypt.util.text.StrongTextEncryptor;

public class Encryptor {
    private static final StrongTextEncryptor encryptor = new StrongTextEncryptor();

    public static String encrypt(String value) {
        return encryptor.encrypt(value);
    }

    public static String decrypt(String value) {
        return encryptor.decrypt(value);
    }
}
