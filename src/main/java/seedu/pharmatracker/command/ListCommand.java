package seedu.pharmatracker.command;

import java.util.ArrayList;

import seedu.pharmatracker.data.Inventory;
import seedu.pharmatracker.data.Medication;

public class ListCommand extends Command {

    @Override
    public void execute(Inventory inventory) {
        ArrayList<Medication> medicationList = inventory.getMedications();
        if (medicationList.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        for (int i = 0; i < medicationList.size(); i++) {
            Medication med = medicationList.get(i);
            System.out.println((i + 1) + ". " + med.toString());
        }

    }
}
