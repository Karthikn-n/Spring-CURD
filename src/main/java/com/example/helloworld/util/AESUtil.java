package com.example.helloworld.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {
    private static final String SECRET_KEY = "1234567890123456"; // 16 chars for AES-128
    private static final String ALGORITHM = "AES";

    public static String encrypt(String data) throws Exception {
        /// instantiating the algorithm method from the Cipher Java in-built method
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        ///  Construct the Secret key with the given Key bytes
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        ///  Initialize the Java Cipher Object with Key
        cipher.init(Cipher.ENCRYPT_MODE, key);
        /// Encrypt the data into single final file
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(decoded));
    }
}
