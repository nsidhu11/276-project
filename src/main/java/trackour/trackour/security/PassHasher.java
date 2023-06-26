package trackour.trackour.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PassHasher {
    
    private String rawPassString;
    final String hashedRes;
    PassHasher(String rawPassString) {
        this.rawPassString = rawPassString;
        // hash it
        this.hashedRes = encoderawPassString();
        
    }

    private String encoderawPassString() {

        MessageDigest hashingAlgos;
        StringBuilder strBuilder = new StringBuilder();
        try
        {
            // Select SHA-256 hashing
            hashingAlgos = MessageDigest.getInstance("SHA-256");

            // Generate the random salt passcode
            SecureRandom randomNum = new SecureRandom();
            byte[] saltByte = new byte[16];
            randomNum.nextBytes(saltByte);

            // Passing the salt to the digest for the computation
            hashingAlgos.update(saltByte);

            // Generate the salted hash
            byte[] hashedPassword = hashingAlgos.digest(rawPassString.getBytes(StandardCharsets.UTF_8));

            for (byte b : hashedPassword)
                strBuilder.append(String.format("%02x", b));
        } catch (NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strBuilder.toString();
    }
}
