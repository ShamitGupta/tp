package seedu.pharmatracker.auth;

import java.util.HashMap;
import java.util.Map;

import seedu.pharmatracker.exceptions.PharmaTrackerException;

/**
 * Manages registration, authentication, and current session user state.
 */
public class AuthService {
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 32;
    private static final String USERNAME_PATTERN = "^[A-Za-z0-9_.-]+$";

    private final Map<String, String> usernameToPasswordHash;
    private final PasswordHasher hasher;
    private final PasswordValidator validator;

    private String currentUsername;

    /**
     * Constructs an AuthService with persisted users and prior session username.
     *
     * @param persistedUsers Existing users loaded from storage.
     * @param persistedSessionUsername Username loaded from session storage.
     */
    public AuthService(Map<String, String> persistedUsers, String persistedSessionUsername) {
        this.usernameToPasswordHash = new HashMap<>();
        if (persistedUsers != null) {
            this.usernameToPasswordHash.putAll(persistedUsers);
        }
        this.hasher = new PasswordHasher();
        this.validator = new PasswordValidator();

        if (persistedSessionUsername != null && this.usernameToPasswordHash.containsKey(persistedSessionUsername)) {
            this.currentUsername = persistedSessionUsername;
        }
    }

    /**
     * Registers a new account using username and password.
     *
     * @param username Desired username.
     * @param password Raw password.
     * @throws PharmaTrackerException If validation fails or username already exists.
     */
    public void register(String username, String password) throws PharmaTrackerException {
        String normalizedUsername = validateAndNormalizeUsername(username);
        if (usernameToPasswordHash.containsKey(normalizedUsername)) {
            throw new PharmaTrackerException("Username already exists. Please choose another username.");
        }

        validator.validate(password);
        String hash = hasher.hashPassword(password);
        usernameToPasswordHash.put(normalizedUsername, hash);
    }

    /**
     * Attempts to authenticate a user.
     *
     * @param username Username to login with.
     * @param password Raw password.
     * @throws PharmaTrackerException If credentials are invalid.
     */
    public void login(String username, String password) throws PharmaTrackerException {
        String normalizedUsername = normalizeUsername(username);
        if (normalizedUsername == null || !usernameToPasswordHash.containsKey(normalizedUsername)) {
            throw new PharmaTrackerException("Invalid username or password.");
        }

        String storedHash = usernameToPasswordHash.get(normalizedUsername);
        if (!hasher.verifyPassword(password, storedHash)) {
            throw new PharmaTrackerException("Invalid username or password.");
        }

        this.currentUsername = normalizedUsername;
    }

    /**
     * Clears the current logged-in user.
     */
    public void logout() {
        this.currentUsername = null;
    }

    /**
     * Returns true when there is a logged-in user.
     *
     * @return Authentication state.
     */
    public boolean isAuthenticated() {
        return currentUsername != null;
    }

    /**
     * Returns the username of the current logged-in user, or null when none.
     *
     * @return Logged-in username.
     */
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * Returns a copy of all persisted users and password hashes.
     *
     * @return User map snapshot.
     */
    public Map<String, String> getUsersSnapshot() {
        return new HashMap<>(usernameToPasswordHash);
    }

    private String validateAndNormalizeUsername(String username) throws PharmaTrackerException {
        String normalized = normalizeUsername(username);
        if (normalized == null) {
            throw new PharmaTrackerException("Username cannot be empty.");
        }

        if (normalized.length() < MIN_USERNAME_LENGTH || normalized.length() > MAX_USERNAME_LENGTH) {
            throw new PharmaTrackerException("Username must be between 3 and 32 characters.");
        }

        if (!normalized.matches(USERNAME_PATTERN)) {
            throw new PharmaTrackerException(
                    "Username can only contain letters, digits, underscores, dots, and hyphens.");
        }

        return normalized;
    }

    private String normalizeUsername(String username) {
        if (username == null) {
            return null;
        }
        String trimmed = username.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        return trimmed;
    }
}
