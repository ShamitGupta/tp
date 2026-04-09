package seedu.pharmatracker.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.pharmatracker.exceptions.PharmaTrackerException;

public class AuthServiceTest {

    @Test
    public void register_validCredentials_registersSuccessfully() throws PharmaTrackerException {
        AuthService authService = new AuthService(new HashMap<>(), null);

        authService.register("alice", "Strong1!");
        authService.login("alice", "Strong1!");

        assertTrue(authService.isAuthenticated());
        assertEquals("alice", authService.getCurrentUsername());
        assertFalse(authService.getUsersSnapshot().get("alice").contains("Strong1!"));
    }

    @Test
    public void register_duplicateUsername_throwsException() throws PharmaTrackerException {
        AuthService authService = new AuthService(new HashMap<>(), null);
        authService.register("alice", "Strong1!");

        assertThrows(PharmaTrackerException.class, () -> authService.register("alice", "Another1!"));
    }

    @Test
    public void register_weakPassword_throwsException() {
        AuthService authService = new AuthService(new HashMap<>(), null);

        assertThrows(PharmaTrackerException.class, () -> authService.register("alice", "weak"));
    }

    @Test
    public void login_invalidPassword_throwsException() throws PharmaTrackerException {
        AuthService authService = new AuthService(new HashMap<>(), null);
        authService.register("alice", "Strong1!");

        assertThrows(PharmaTrackerException.class, () -> authService.login("alice", "Wrong1!"));
    }

    @Test
    public void sessionRestoredFromPersistedUser_restoresAuthenticationState() throws PharmaTrackerException {
        AuthService first = new AuthService(new HashMap<>(), null);
        first.register("alice", "Strong1!");

        AuthService restored = new AuthService(first.getUsersSnapshot(), "alice");
        assertTrue(restored.isAuthenticated());
        assertEquals("alice", restored.getCurrentUsername());

        restored.logout();
        assertFalse(restored.isAuthenticated());
    }
}
