package trackour.trackour.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PassHasherSHA256 {

    private final String rawPassString;
    private String hashedPassword;
    private String hashedSalt;

    public PassHasherSHA256(String rawPassString) {
        this.rawPassString = rawPassString;
        // this.hashedPassword = rawPassString;
        // Select SHA-256 hashing
        MessageDigest hashingDigest;
        byte[] salt;
        try {
            hashingDigest = MessageDigest.getInstance("SHA-256");
            salt = getSalt();
            this.hashedSalt = byteToHex(salt); // saltHashStr
            this.hashedPassword = hashRawPassString(hashingDigest, salt);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getHashedSalt() {
        return this.hashedSalt;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    private String byteToHex(byte[] byteValArray) {
        String retHex = "";
        // Iterating through each byte in the array
        for (byte i : byteValArray) {
            retHex += String.format("%02X", i);
        }
        return retHex;
    }

    private String hashRawPassString(MessageDigest hashingDigest, byte salt[]) throws NoSuchAlgorithmException {

        StringBuilder strBuilder = new StringBuilder();
        byte[] hashedSalt = getSaltHash(hashingDigest, rawPassString, salt);
        for (byte b : hashedSalt)
            strBuilder.append(String.format("%02x", b));
        return strBuilder.toString();
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        // Generate the random salt passcode
        SecureRandom randomNum = new SecureRandom();
        byte[] saltByte = new byte[16];
        randomNum.nextBytes(saltByte);
        return saltByte;
    }

    private byte[] getSaltHash(MessageDigest hashDigest, String rawPass, byte[] salt) {
        // Passing the salt to the digest for the computation
            hashDigest.update(salt);

            // Generate the salted hash
            return hashDigest.digest(rawPass.getBytes(StandardCharsets.UTF_8));
    }

}
