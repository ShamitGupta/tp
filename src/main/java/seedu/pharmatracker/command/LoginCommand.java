package seedu.pharmatracker.command;

import seedu.pharmatracker.auth.AuthService;
import seedu.pharmatracker.core.AppServices;
import seedu.pharmatracker.customer.CustomerList;
import seedu.pharmatracker.data.Inventory;
import seedu.pharmatracker.exceptions.PharmaTrackerException;
import seedu.pharmatracker.ui.Ui;

/**
 * Logs in a user with username and password.
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";

    private final String username;
    private final String password;

    public LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute(Inventory inventory, Ui ui, CustomerList customerList) {
        AuthService authService = AppServices.getAuthService();
        if (authService == null) {
            ui.printMessage("Authentication service is unavailable.");
            return;
        }

        try {
            authService.login(username, password);
            ui.printMessage("Login successful. Welcome, " + authService.getCurrentUsername() + "!");
        } catch (PharmaTrackerException e) {
            ui.printMessage(e.getMessage());
        }
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }
}
