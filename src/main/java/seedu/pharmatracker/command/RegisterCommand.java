package seedu.pharmatracker.command;

import seedu.pharmatracker.auth.AuthService;
import seedu.pharmatracker.core.AppServices;
import seedu.pharmatracker.customer.CustomerList;
import seedu.pharmatracker.data.Inventory;
import seedu.pharmatracker.exceptions.PharmaTrackerException;
import seedu.pharmatracker.ui.Ui;

/**
 * Registers a new user account with username and password.
 */
public class RegisterCommand extends Command {
    public static final String COMMAND_WORD = "register";

    private final String username;
    private final String password;

    public RegisterCommand(String username, String password) {
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
            authService.register(username, password);
            ui.printMessage("Registration successful for user '" + username + "'. You can now login.");
        } catch (PharmaTrackerException e) {
            ui.printMessage(e.getMessage());
        }
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }
}
