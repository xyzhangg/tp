package seedu.address.logic.commands;

import seedu.address.model.module.ModuleName;

/**
 * SUs the module indicated.
 */
public class SuCommand extends UpdateCommand {
    public static final String COMMAND_WORD = "su";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": SUs the module identified "
            + "by the module name displayed in the module list.\n"
            + "Parameters: "
            + "MODULE_CODE \n"
            + "Example: " + COMMAND_WORD
            + " CS2103T";

    /**
     * @param moduleName              of the module in the filtered module list to update
     * @param updateModNameDescriptor details to update the module with
     */
    public SuCommand(ModuleName moduleName, UpdateModNameDescriptor updateModNameDescriptor) {
        super(moduleName, updateModNameDescriptor);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof SuCommand; // instanceof handles nulls
    }
}
