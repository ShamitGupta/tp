package seedu.pharmatracker.core;

import seedu.pharmatracker.alert.RestockAlertService;
import seedu.pharmatracker.auth.AuthService;

/**
 * Provides access to shared application services for command execution.
 */
public final class AppServices {
    private static AuthService authService;
    private static RestockAlertService restockAlertService;

    private AppServices() {
    }

    public static void initialize(AuthService auth, RestockAlertService alertService) {
        authService = auth;
        restockAlertService = alertService;
    }

    public static AuthService getAuthService() {
        return authService;
    }

    public static RestockAlertService getRestockAlertService() {
        return restockAlertService;
    }
}
