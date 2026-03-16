package seedu.pharmatracker.data;

import java.util.ArrayList;

public class Medication {
    private String name;
    private String dosage;
    private int quantity;
    private String expiryDate;
    private String tag;

    private String dosageForm;
    private String manufacturer;
    private String directions;
    private String frequency;
    private String route;
    private String maxDailyDose;
    private ArrayList<String> warnings;

    public Medication(String name, String dosage, int quantity, String expiryDate, String tag) {
        this.name = name;
        this.dosage = dosage;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.tag = tag;

        this.dosageForm = "";
        this.manufacturer = "";
        this.directions = "";
        this.frequency = "";
        this.route = "";
        this.maxDailyDose = "";
        this.warnings = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public String getDosage() {
        return this.dosage;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public String getTag() {
        return this.tag;
    }

    public String getDosageForm() {
        return this.dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDirections() {
        return this.directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getRoute() {
        return this.route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getMaxDailyDose() {
        return this.maxDailyDose;
    }

    public void setMaxDailyDose(String maxDailyDose) {
        this.maxDailyDose = maxDailyDose;
    }

    public ArrayList<String> getWarnings() {
        return this.warnings;
    }

    public void addWarning(String warning) {
        this.warnings.add(warning);
    }

    @Override
    public String toString() {
        String s = "Name: " + name +
                   " | Dosage: " + dosage +
                   " | Qty: " + quantity +
                   " | Exp: " + expiryDate;

        if (!tag.isEmpty()) {
            s += " | Tag: " + tag;
        }

        return s;
    }
}
