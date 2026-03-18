package utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encryption utility for securing IRCTC credentials.
 * Provides both Base64 (for backward compatibility) and AES encryption.
 */
public class EncryptionUtils {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;
    private static final String SECRET_KEY_ENV = "IRCTC_SECRET_KEY";

    /**
     * Legacy Base64 encoding - kept for backward compatibility with existing configs
     */
    public static String encode(String plainText) {
        return Base64.getEncoder().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64 decoding for stored passwords
     */
    public static String decode(String encodedText) {
        if (encodedText == null || encodedText.isEmpty()) {
            return "";
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            System.out.println("[WARN] Base64 decoding failed: " + e.getMessage());
            return "";
        }
    }

    /**
     * Generate a new AES secret key for encryption
     */
    public static String generateAesKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE);
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Encrypt password using AES encryption
     */
    public static String encryptAes(String plainText, String encodedKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, originalKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypt password using AES encryption
     */
    public static String decryptAes(String encryptedText, String encodedKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, originalKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Get AES key from environment variable
     */
    public static String getAesKeyFromEnv() {
        String key = System.getenv(SECRET_KEY_ENV);
        if (key == null || key.isEmpty()) {
            throw new RuntimeException("[ERROR] AES key not found in environment variable: " + SECRET_KEY_ENV);
        }
        return key;
    }
}