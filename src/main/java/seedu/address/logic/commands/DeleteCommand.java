package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.GetModuleIndex;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleName;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterManager;

/**
 * Deletes a module identified using it's displayed name from the module list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by the module name used in the displayed module list.\n"
            + "Parameters: MODULE_NAME\n"
            + "Example: " + COMMAND_WORD + " CS2103T";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";

    private final ModuleName targetModuleName;
    
    public DeleteCommand(ModuleName targetModuleName) {
        requireNonNull(targetModuleName);
        this.targetModuleName = targetModuleName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();
        SemesterManager semesterManager = SemesterManager.getInstance();
        Semester semester = semesterManager.getCurrentSemester();
        if (semester == Semester.NA) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_SEQUENCE);
        }
        Index targetModuleIndex;
        try {
            targetModuleIndex = GetModuleIndex.getIndex(lastShownList, targetModuleName);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_NAME);
        }
        Module moduleToDelete = lastShownList.get(targetModuleIndex.getZeroBased());
        model.deleteModule(moduleToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetModuleName.equals(((DeleteCommand) other).targetModuleName)); // state check
    }
}
