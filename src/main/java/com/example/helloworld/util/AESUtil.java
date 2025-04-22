package com.example.helloworld.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {
    public static final String SHA_CRYPT = "SHA-256";
    private static final String SECRET_KEY = "3mtree8u51n33ss501ut10nm33n6v33r"; // 32 chars for AES-256
    private static final String ALGORITHM = "AES";
    public static final String AES_ALGORITHM_GCM = "AES/GCM/NoPadding";

    /// Encrypt any type data
    public static String encrypt(String data) throws Exception {
        /// instantiating the algorithm method from the Cipher Java in-built method
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        /// Construct the Secret key with the given Key bytes
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        ///  Initialize the Java Cipher Object with Key
        cipher.init(Cipher.ENCRYPT_MODE, key);
        /// Encrypt the data into single final file
        byte[] encrypted = cipher.doFinal(data.getBytes());
        ///Encode the encrypted data into String type
        return Base64.getEncoder().encodeToString(encrypted);
    }

    ///  Decrypt the data from the API request from the User
    public static String decrypt(String encryptedData) throws Exception {
        /// Define the Cipher where it contains all the algorithm to encrypt
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        /// Construct the Secret key with the given Key bytes
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        ///  Initialize the Java Cipher Object with Key
        cipher.init(Cipher.DECRYPT_MODE, key);
        /// Decode the encrypted data
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(decoded));
    }
}
