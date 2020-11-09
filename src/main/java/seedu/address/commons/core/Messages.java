package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_MODULE_CANNOT_BE_SU = "%1$s cannot be S/U-ed!";
    public static final String MESSAGE_INVALID_MODULE_DISPLAYED_NAME = "Please enter the module code of a module"
            + " which is currently in your grade book.";
    public static final String MESSAGE_MODULES_LISTED_OVERVIEW = "%1$d modules listed!";
    public static final String MESSAGE_NO_MODULES_FOUND = "No modules were found!";
    public static final String MESSAGE_INVALID_COMMAND_SEQUENCE = "Start a semester before modifying the module list.";
    public static final String MESSAGE_INVALID_DONE_COMMAND = "There is no semester being modified.";
    public static final String MESSAGE_INVALID_SEMESTER = "The semester you have entered is invalid.\n"
            + "Only Y1S1, Y1S2, Y2S1, Y2S2, Y3S1, Y3S2, Y4S1, Y4S2, Y5S1, and Y5S2 are accepted.";
    public static final String MESSAGE_DELETE_MODULE_IN_WRONG_SEMESTER = "The module you are trying to delete is in ";
    public static final String MESSAGE_UPDATE_MODULE_IN_WRONG_SEMESTER = "The module you are trying to edit is in ";
    public static final String MESSAGE_CURRENT_SEMESTER = "The semester you are currently editing is ";
    public static final String MESSAGE_DIRECT_TO_CORRECT_SEMESTER = "Please navigate to ";
    public static final String MESSAGE_DIRECT_TO_CORRECT_SEMESTER_TO_DELETE = " to delete the module.";
    public static final String MESSAGE_DIRECT_TO_CORRECT_SEMESTER_TO_UPDATE = " to edit the module.";
}
