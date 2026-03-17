package utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptionUtils {

    /**
     * Use this to generate the encoded string for your serenity.conf
     */
    public static String encode(String plainText) {
        return Base64.getEncoder().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Use this in your BaseStepDefinition to decrypt the password before sending to the browser
     */
    public static String decode(String encodedText) {
        if (encodedText == null || encodedText.isEmpty()) {
            return "";
        }
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    // Quick runner to generate your encoded password
    public static void main(String[] args) {
        String myPassword = "Paperpaper@2001";
        System.out.println("Encoded String: " + encode(myPassword));
    }
}