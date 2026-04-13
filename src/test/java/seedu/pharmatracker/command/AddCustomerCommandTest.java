package seedu.pharmatracker.command;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import seedu.pharmatracker.customer.Customer;
import seedu.pharmatracker.customer.CustomerList;
import seedu.pharmatracker.data.Inventory;
import seedu.pharmatracker.exceptions.PharmaTrackerException;
import seedu.pharmatracker.ui.Ui;

//@@author karthikkathiresh
public class AddCustomerCommandTest {

    @Test
    void constructor_validInputs_createsCommandSuccessfully() {
        assertDoesNotThrow(() -> new AddCustomerCommand("C001", "John Doe", "91234567",
                "123 Clementi Road", new ArrayList<>()));
    }

    @Test
    void constructor_nullId_throwsAssertionError() {
        assertThrows(AssertionError.class,
                () -> new AddCustomerCommand(null, "John Doe", "91234567", "123 Clementi Road", new ArrayList<>()));
    }

    @Test
    void constructor_emptyId_throwsAssertionError() {
        assertThrows(AssertionError.class,
                () -> new AddCustomerCommand("", "John Doe", "91234567", "123 Clementi Road", new ArrayList<>()));
    }

    @Test
    void constructor_nullName_throwsAssertionError() {
        assertThrows(AssertionError.class,
                () -> new AddCustomerCommand("C001", null, "91234567", "123 Clementi Road", new ArrayList<>()));
    }

    @Test
    void constructor_emptyName_throwsAssertionError() {
        assertThrows(AssertionError.class,
                () -> new AddCustomerCommand("C001", "", "91234567", "123 Clementi Road", new ArrayList<>()));
    }

    @Test
    void constructor_nullPhone_throwsAssertionError() {
        assertThrows(AssertionError.class,
                () -> new AddCustomerCommand("C001", "John Doe", null, "123 Clementi Road", new ArrayList<>()));
    }

    @Test
    void constructor_emptyPhone_throwsAssertionError() {
        assertThrows(AssertionError.class,
                () -> new AddCustomerCommand("C001", "John Doe", "", "123 Clementi Road", new ArrayList<>()));
    }

    @Test
    void constructor_nullAddress_throwsAssertionError() {
        assertThrows(AssertionError.class,
                () -> new AddCustomerCommand("C001", "John Doe", "91234567", null, new ArrayList<>()));
    }

    void constructor_nullAllergies_throwsAssertionError() {
        assertThrows(AssertionError.class,
                () -> new AddCustomerCommand("C001", "John Doe", "91234567", "123 Clementi Road", null));
    }

    @Test
    void execute_validParameters_addsCustomerSuccessfully() throws PharmaTrackerException {
        // Setup
        AddCustomerCommand command = new AddCustomerCommand("C001", "John Doe", "91234567",
                "123 Clementi Road", new ArrayList<>());
        Inventory inventory = new Inventory();
        Ui ui = new Ui();
        CustomerList customerList = new CustomerList();

        command.execute(inventory, ui, customerList);

        assertEquals(1, customerList.getCustomerCount());

        Customer addedCustomer = customerList.getCustomer(0);
        assertEquals("C001", addedCustomer.getCustomerId());
        assertEquals("John Doe", addedCustomer.getName());
        assertEquals("91234567", addedCustomer.getPhone());
        assertEquals("123 Clementi Road", addedCustomer.getAddress());
    }

    @Test
    void execute_duplicateCustomerId_throwsPharmaTrackerException() throws PharmaTrackerException {
        // Command to add the first customer
        AddCustomerCommand firstCommand = new AddCustomerCommand("C001", "John Doe", "91234567",
                "123 Clementi Road", new ArrayList<>());

        // Command to add a different customer but with the SAME ID ("C001")
        AddCustomerCommand duplicateCommand = new AddCustomerCommand("C001", "Jane Smith", "98765432",
                "456 Kent Ridge", new ArrayList<>());

        Inventory inventory = new Inventory();
        Ui ui = new Ui();
        CustomerList customerList = new CustomerList();

        firstCommand.execute(inventory, ui, customerList);

        PharmaTrackerException thrown = assertThrows(PharmaTrackerException.class,
                () -> duplicateCommand.execute(inventory, ui, customerList));

        assertEquals("Failed to add customer: A customer with ID 'C001' already exists.", thrown.getMessage());

        assertEquals(1, customerList.getCustomerCount());
    }

    @Test
    void execute_nullUi_throwsAssertionError() {
        AddCustomerCommand command = new AddCustomerCommand("C001", "John Doe", "91234567",
                "123 Clementi Road", new ArrayList<>());
        Inventory inventory = new Inventory();
        CustomerList customerList = new CustomerList();

        assertThrows(AssertionError.class,
                () -> command.execute(inventory, null, customerList));
    }

    @Test
    void execute_nullCustomerList_throwsAssertionError() {
        AddCustomerCommand command = new AddCustomerCommand("C001", "John Doe", "91234567",
                "123 Clementi Road", new ArrayList<>());
        Inventory inventory = new Inventory();
        Ui ui = new Ui();

        assertThrows(AssertionError.class,
                () -> command.execute(inventory, ui, null));
    }
}
