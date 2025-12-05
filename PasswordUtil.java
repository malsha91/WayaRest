import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordUtil {
    
    /**
     * Hashes a password using SHA-256 algorithm
     * @param password The plain text password to hash
     * @return The hashed password encoded in Base64
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error hashing password: " + e.getMessage());
            return null;
        }
    }

    /**
     * Verifies a plain text password against a hashed password
     * @param plainPassword The plain text password to verify
     * @param hashedPassword The hashed password to compare against
     * @return True if passwords match, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String plainPasswordHash = hashPassword(plainPassword);
        return plainPasswordHash != null && plainPasswordHash.equals(hashedPassword);
    }
}
