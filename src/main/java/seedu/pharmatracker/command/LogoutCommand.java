package seedu.pharmatracker.command;

import seedu.pharmatracker.auth.AuthService;
import seedu.pharmatracker.core.AppServices;
import seedu.pharmatracker.customer.CustomerList;
import seedu.pharmatracker.data.Inventory;
import seedu.pharmatracker.ui.Ui;

/**
 * Logs out the currently authenticated user.
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";

    @Override
    public void execute(Inventory inventory, Ui ui, CustomerList customerList) {
        AuthService authService = AppServices.getAuthService();
        if (authService == null) {
            ui.printMessage("Authentication service is unavailable.");
            return;
        }

        if (!authService.isAuthenticated()) {
            ui.printMessage("No user is currently logged in.");
            return;
        }

        String username = authService.getCurrentUsername();
        authService.logout();
        ui.printMessage("Logged out successfully: " + username);
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }
}
