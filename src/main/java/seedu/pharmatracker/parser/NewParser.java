package seedu.pharmatracker.parser;

import seedu.pharmatracker.command.Command;
import seedu.pharmatracker.exceptions.PharmaTrackerException;

public interface NewParser<T extends Command> {
    T parse(String args) throws PharmaTrackerException;
}