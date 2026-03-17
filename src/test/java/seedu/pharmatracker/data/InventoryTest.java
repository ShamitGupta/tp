package seedu.pharmatracker.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InventoryTest {
    @Test
    public void inventory_initiallyEmpty() {
        Inventory inventory = new Inventory();
        assertTrue(inventory.getMedications().isEmpty());
    }

    @Test
    public void inventory_addMedication_increasesSize() {
        Inventory inventory = new Inventory();
        Medication med = new Medication("Aspirin", "100mg", 50, "2027-01-01", "painkiller");
        inventory.getMedications().add(med);
        assertEquals(1, inventory.getMedications().size());
    }

    @Test
    public void inventory_getMedication_returnsCorrectMedication() {
        Inventory inventory = new Inventory();
        Medication med = new Medication("Aspirin", "100mg", 50, "2027-01-01", "painkiller");
        inventory.getMedications().add(med);
        assertEquals("Aspirin", inventory.getMedication(0).getName());
    }

    @Test
    public void inventory_multipleMedications_sizeIsCorrect() {
        Inventory inventory = new Inventory();
        inventory.getMedications().add(new Medication("MedA", "10mg", 10, "2026-01-01", "tag"));
        inventory.getMedications().add(new Medication("MedB", "20mg", 20, "2026-06-01", "tag"));
        inventory.getMedications().add(new Medication("MedC", "30mg", 30, "2027-01-01", "tag"));
        assertEquals(3, inventory.getMedications().size());
    }

    @Test
    public void listMedications_emptyInventory_printsEmpty() {
        Inventory inventory = new Inventory();
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        inventory.listMedications();
        assertTrue(out.toString().contains("Inventory is empty."));
        System.setOut(System.out);
    }

    @Test
    public void listMedications_multipleMedications_printsAllNames() {
        Inventory inventory = new Inventory();
        inventory.addMedication(new Medication("Aspirin", "100mg", 50, "2027-01-01", "pain"));
        inventory.addMedication(new Medication("Paracetamol", "500mg", 20, "2026-06-01", "fever"));
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        inventory.listMedications();
        assertTrue(out.toString().contains("Aspirin"));
        assertTrue(out.toString().contains("Paracetamol"));
        System.setOut(System.out);
    }
}
