package seedu.pharmatracker.auth;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import seedu.pharmatracker.exceptions.PharmaTrackerException;

/**
 * Provides secure password hashing and verification using PBKDF2.
 */
public class PasswordHasher {
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 120_000;
    private static final int SALT_BYTES = 16;
    private static final int HASH_BITS = 256;

    /**
     * Hashes a raw password with a random salt.
     *
     * @param rawPassword Password in plain text.
     * @return Encoded hash in format iterations:salt:hash.
     * @throws PharmaTrackerException If hashing fails.
     */
    public String hashPassword(String rawPassword) throws PharmaTrackerException {
        byte[] salt = new byte[SALT_BYTES];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        byte[] hash = pbkdf2(rawPassword.toCharArray(), salt, ITERATIONS, HASH_BITS);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encodedHash = Base64.getEncoder().encodeToString(hash);
        return ITERATIONS + ":" + encodedSalt + ":" + encodedHash;
    }

    /**
     * Verifies whether a raw password matches a stored encoded hash.
     *
     * @param rawPassword Raw user-provided password.
     * @param storedHash Stored hash in format iterations:salt:hash.
     * @return True if password matches, false otherwise.
     */
    public boolean verifyPassword(String rawPassword, String storedHash) {
        try {
            String[] parts = storedHash.split(":");
            if (parts.length != 3) {
                return false;
            }
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[2]);
            byte[] providedHash = pbkdf2(rawPassword.toCharArray(), salt, iterations, expectedHash.length * 8);
            return constantTimeEquals(expectedHash, providedHash);
        } catch (Exception e) {
            return false;
        }
    }

    private byte[] pbkdf2(char[] password, byte[] salt, int iterations, int hashBits)
            throws PharmaTrackerException {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, hashBits);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new PharmaTrackerException("Unable to process password securely.");
        }
    }

    private boolean constantTimeEquals(byte[] left, byte[] right) {
        if (left.length != right.length) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < left.length; i++) {
            result |= left[i] ^ right[i];
        }
        return result == 0;
    }
}
