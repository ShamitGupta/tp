package seedu.pharmatracker.parser;

import java.util.ArrayList;

import seedu.pharmatracker.command.AddCommand;
import seedu.pharmatracker.command.Command;
import seedu.pharmatracker.command.DeleteCommand;
import seedu.pharmatracker.command.ListCommand;
import seedu.pharmatracker.command.SortCommand;
import seedu.pharmatracker.command.FindCommand;
import seedu.pharmatracker.command.ViewCommand;
import seedu.pharmatracker.command.DispenseCommand;
import seedu.pharmatracker.command.HelpCommand;
import seedu.pharmatracker.command.ExitCommand;
import seedu.pharmatracker.exceptions.PharmaTrackerException;

public class Parser {

    public static final String FLAG_NAME = "/n";
    public static final String FLAG_DOSAGE = "/d";
    public static final String FLAG_QUANTITY = "/q";
    public static final String FLAG_EXPIRY_DATE = "/e";
    public static final String FLAG_TAG = "/t";
    public static final String FLAG_DOSAGE_FORM = "/df";
    public static final String FLAG_MANUFACTURER = "/mfr";
    public static final String FLAG_DIRECTION = "/dir";
    public static final String FLAG_FREQUENCY = "/freq";
    public static final String FLAG_ROUTE = "/route";
    public static final String FLAG_MAX_DOSAGE = "/max";
    public static final String FLAG_WARNINGS = "/warn";

    private static final String[] ALL_FLAGS = {
            FLAG_NAME, FLAG_DOSAGE, FLAG_QUANTITY, FLAG_EXPIRY_DATE, FLAG_TAG,
            FLAG_DOSAGE_FORM, FLAG_MANUFACTURER, FLAG_DIRECTION, FLAG_FREQUENCY,
            FLAG_ROUTE, FLAG_MAX_DOSAGE, FLAG_WARNINGS
    };

    private static int findNextFlagIndex(String description, int afterIndex) throws PharmaTrackerException {
        if (afterIndex < 0 || afterIndex > description.length()) {
            throw new PharmaTrackerException("Error parsing command flags: Invalid search index.");
        }

        int earliest = description.length();
        for (String flag : ALL_FLAGS) {
            int idx = description.indexOf(flag, afterIndex);
            if (idx != -1 && idx < earliest) {
                earliest = idx;
            }
        }
        return earliest;
    }

    private static String extractFlag(String description, String flag) throws PharmaTrackerException {
        int flagIndex = description.indexOf(flag);
        if (flagIndex == -1) {
            return "";
        }

        int valueStart = flagIndex + flag.length();
        if (valueStart >= description.length()) {
            throw new PharmaTrackerException("Value for '" + flag + "' cannot be empty!");
        }

        int valueEnd = findNextFlagIndex(description, valueStart);
        String extractedValue = description.substring(valueStart, valueEnd).trim();

        if (extractedValue.isEmpty()) {
            throw new PharmaTrackerException("Value for '" + flag + "' cannot be empty!");
        }

        return extractedValue;
    }

    public static String extractName(String description) throws PharmaTrackerException {
        int nameIndex = description.indexOf(FLAG_NAME);
        int dosageIndex = description.indexOf(FLAG_DOSAGE);

        if (nameIndex == -1 || dosageIndex == -1 || nameIndex >= dosageIndex) {
            throw new PharmaTrackerException("Invalid format! Please ensure you include '/n' followed by '/d'.");
        }

        String name = description.substring(nameIndex + 2, dosageIndex).trim();
        if (name.isEmpty()) {
            throw new PharmaTrackerException("Medication name cannot be empty!");
        }

        return name;
    }

    public static String extractDosage(String description) throws PharmaTrackerException {
        int dosageIndex = description.indexOf(FLAG_DOSAGE);
        int quantityIndex = description.indexOf(FLAG_QUANTITY);

        if (dosageIndex == -1 || quantityIndex == -1 || dosageIndex > quantityIndex) {
            throw new PharmaTrackerException("Invalid format! Please ensure you include '/d' followed by '/q'.");
        }

        String dosage = description.substring(dosageIndex + 2, quantityIndex).trim();
        if (dosage.isEmpty()) {
            throw new PharmaTrackerException("Dosage cannot be empty!");
        }

        return dosage;
    }

    public static int extractQuantity(String description) throws PharmaTrackerException {
        int quantityIndex = description.indexOf(FLAG_QUANTITY);
        int expiryIndex = description.indexOf(FLAG_EXPIRY_DATE);

        if (quantityIndex == -1 || expiryIndex == -1 || quantityIndex > expiryIndex) {
            throw new PharmaTrackerException("Invalid format! Please ensure you include '/q' followed by '/e'.");
        }

        String quantityString = description.substring(quantityIndex + 2, expiryIndex).trim();
        if (quantityString.isEmpty()) {
            throw new PharmaTrackerException("Quantity cannot be empty.");
        }

        try {
            int quantity = Integer.parseInt(quantityString);
            if (quantity < 0) {
                throw new PharmaTrackerException("Quantity cannot be negative!");
            }
            return quantity;
        } catch (NumberFormatException e) {
            throw new PharmaTrackerException("Invalid quantity! Please enter a valid whole number.");
        }
    }

    public static String extractExpiryDate(String description) throws PharmaTrackerException {
        int expiryIndex = description.indexOf(FLAG_EXPIRY_DATE);
        if (expiryIndex == -1) {
            throw new PharmaTrackerException("Invalid format! Please ensure you include the '/e' flag.");
        }

        int valueStart = expiryIndex + 2;
        int valueEnd = findNextFlagIndex(description, valueStart);

        if (valueStart > description.length()) {
            throw new PharmaTrackerException("Expiry date cannot be empty!");
        }

        String expiryDate = description.substring(valueStart, valueEnd);

        if (expiryDate.isEmpty()) {
            throw new PharmaTrackerException("Expiry date cannot be empty!");
        }

        return expiryDate;
    }

    private static ArrayList<String> extractWarnings(String description) throws PharmaTrackerException {
        ArrayList<String> warnings = new ArrayList<>();
        int searchFrom = 0;
        while (true) {
            int idx = description.indexOf(FLAG_WARNINGS, searchFrom);
            if (idx == -1) {
                break;
            }
            int valueStart = idx + FLAG_WARNINGS.length();
            int valueEnd = findNextFlagIndex(description, valueStart);
            String value = description.substring(valueStart, valueEnd).trim();
            if (!value.isEmpty()) {
                warnings.add(value);
            }
            searchFrom = valueStart;
        }
        return warnings;
    }

    public static Command parse(String userInput) throws PharmaTrackerException {
        String[] inputParts = userInput.trim().split("\\s+", 2);
        String commandWord = inputParts[0].toLowerCase();
        String description = (inputParts.length > 1) ? inputParts[1] : "";

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            String name = extractName(description);
            String dosage = extractDosage(description);
            int quantity = extractQuantity(description);
            String expiryDate = extractExpiryDate(description);
            String tag = extractFlag(description, FLAG_TAG);
            String dosageForm = extractFlag(description, FLAG_DOSAGE_FORM);
            String manufacturer = extractFlag(description, FLAG_MANUFACTURER);
            String directions = extractFlag(description, FLAG_DIRECTION);
            String frequency = extractFlag(description, FLAG_FREQUENCY);
            String route = extractFlag(description, FLAG_ROUTE);
            String maxDailyDose = extractFlag(description, FLAG_MAX_DOSAGE);
            ArrayList<String> warnings = extractWarnings(description);
            return new AddCommand(name, dosage, quantity, expiryDate, tag,
                                  dosageForm, manufacturer, directions, frequency,
                                  route, maxDailyDose, warnings);

        case "delete":
            System.out.println("Delete command triggered.");
            return new DeleteCommand(description);

        case "list":
            System.out.println("List command triggered.");
            return new ListCommand();

        case "find":
            System.out.println("Find command triggered.");
            if (description.isEmpty()) {
                System.out.println("Please provide a keyword to search for.");
                break;
            }
            return new FindCommand(description);

        case "view":
            System.out.println("View command triggered.");
            if (description.isEmpty()) {
                System.out.println("Please provide an index to view.");
                break;
            }
            try {
                int index = Integer.parseInt(description.trim());
                return new ViewCommand(index);
            } catch (NumberFormatException e) {
                System.out.println("Invalid index. Please enter a valid number.");
                break;
            }

        case "dispense":
            System.out.println("Dispense command triggered.");
            if (description.isEmpty()) {
                System.out.println("Please provide an index and quantity.");
                break;
            }
            try {
                String[] parts = description.trim().split("q/");
                int dispenseIndex = Integer.parseInt(parts[0].trim());
                int dispenseQuantity = Integer.parseInt(parts[1].trim());
                return new DispenseCommand(dispenseIndex, dispenseQuantity);
            } catch (Exception e) {
                System.out.println("Invalid format. Use: dispense INDEX q/QUANTITY");
                break;
            }

        case "sort":
            System.out.println("Sort command triggered.");
            return new SortCommand();

        case "label":
            System.out.println("Label command triggered.");
            break;

        case "help":
            return new HelpCommand();

        case "exit":
            return new ExitCommand();

        default:
            throw new PharmaTrackerException("Unknown command! " +
                    "Please type 'help' to see the list of available commands.");
        }

        return null;
    }
}
