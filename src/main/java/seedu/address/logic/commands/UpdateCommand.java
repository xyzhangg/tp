package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MODULE_CANNOT_BE_SU;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.GetModuleIndex;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Grade;
import seedu.address.model.module.ModularCredit;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleName;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterManager;
import seedu.address.model.util.ModuleInfoRetriever;

/**
 * Updates the details of an existing module in the address book.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the module identified "
            + "by the module name displayed in the module list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_MOD_NAME + "MODULE_CODE "
            + "[" + PREFIX_GRADE + "GRADE] "
            + "[" + PREFIX_SEMESTER + "SEMESTER] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MOD_NAME + "CS2103T" + " "
            + PREFIX_GRADE + "A" + " "
            + PREFIX_SEMESTER + "Y2S1";

    public static final String MESSAGE_UPDATE_MODULE_SUCCESS = "Updated Module: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to update must be provided.";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the address book.";

    private final ModuleName moduleName;
    private final UpdateModNameDescriptor updateModNameDescriptor;

    /**
     * @param moduleName              of the module in the filtered module list to update
     * @param updateModNameDescriptor details to update the module with
     */
    public UpdateCommand(ModuleName moduleName, UpdateModNameDescriptor updateModNameDescriptor) {
        requireNonNull(moduleName);
        requireNonNull(updateModNameDescriptor);

        this.moduleName = moduleName;
        this.updateModNameDescriptor = new UpdateModNameDescriptor(updateModNameDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();
        SemesterManager semesterManager = SemesterManager.getInstance();
        Semester currentSemester = semesterManager.getCurrentSemester();
        if (currentSemester == Semester.NA) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_SEQUENCE);
        }
        Index index;
        try {
            index = GetModuleIndex.getIndex(model.getFilteredModuleList(), moduleName);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_NAME);
        }

        Module moduleToUpdate = lastShownList.get(index.getZeroBased());
        Module updatedModule = createUpdatedModule(moduleToUpdate, updateModNameDescriptor);
        Semester semesterOfModuleToUpdate = moduleToUpdate.getSemester();
        if (semesterOfModuleToUpdate != currentSemester) {
            throw new CommandException(
                    Messages.MESSAGE_UPDATE_MODULE_IN_WRONG_SEMESTER + semesterOfModuleToUpdate + ".\n"
                            + Messages.MESSAGE_CURRENT_SEMESTER + currentSemester + ".\n"
                            + Messages.MESSAGE_DIRECT_TO_CORRECT_SEMESTER + semesterOfModuleToUpdate
                            + Messages.MESSAGE_DIRECT_TO_CORRECT_SEMESTER_TO_UPDATE);
        }
        if (!moduleToUpdate.isSameModule(updatedModule) && model.hasModule(updatedModule)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        if ((updateModNameDescriptor.grade != null)
                && updateModNameDescriptor.grade.toString() == "SU") {
            // checks if module can be SU from database
            if (!UpdateCommand.isModSuAble(moduleName)) {
                throw new CommandException(String.format(MESSAGE_MODULE_CANNOT_BE_SU, moduleName.fullModName));
            }
        }

        model.setModule(moduleToUpdate, updatedModule);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(String.format(MESSAGE_UPDATE_MODULE_SUCCESS, updatedModule));
    }

    /**
     * Creates and returns a {@code Module} with the details of {@code moduleToUpdate}
     * updated with {@code updatedModuleDescriptor}.
     */
    private static Module createUpdatedModule(Module moduleToUpdate, UpdateModNameDescriptor updateModNameDescriptor) {
        assert moduleToUpdate != null;

        ModuleName updatedModuleName = updateModNameDescriptor.getName().orElse(moduleToUpdate.getModuleName());
        Grade updatedGrade = updateModNameDescriptor.getGrade().orElse(moduleToUpdate.getGrade());
        // modularCredit is not edited
        ModularCredit modularCredit = moduleToUpdate.getModularCredit();
        Semester semester = updateModNameDescriptor.getSemester().orElse(moduleToUpdate.getSemester());
        return new Module(updatedModuleName, updatedGrade, modularCredit, semester);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        // state check
        UpdateCommand e = (UpdateCommand) other;
        return moduleName.equals(e.moduleName)
                && updateModNameDescriptor.equals(e.updateModNameDescriptor);
    }

    /**
     * Stores the details to update the module with. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class UpdateModNameDescriptor {
        private ModuleName moduleName;
        private Grade grade;
        private Semester semester;

        public UpdateModNameDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code toCopy} is used internally.
         */
        public UpdateModNameDescriptor(UpdateModNameDescriptor toCopy) {
            setName(toCopy.moduleName);
            setGrade(toCopy.grade);
            setSemester(toCopy.semester);
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean isAnyFieldUpdated() {
            return CollectionUtil.isAnyNonNull(moduleName, grade, semester);
        }

        /**
         * Sets {@code moduleName} to this object's {@code moduleName}.
         * A defensive copy of {@code moduleName} is used internally.
         */
        public void setName(ModuleName moduleName) {
            this.moduleName = moduleName;
        }

        /**
         * Returns an unmodifiable moduleName set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code moduleName} is null.
         */
        public Optional<ModuleName> getName() {
            return Optional.ofNullable(moduleName);
        }

        /**
         * Sets {@code grade} to this object's {@code grade}.
         * A defensive copy of {@code grade} is used internally.
         */
        public void setGrade(Grade grade) {
            this.grade = grade;
        }

        /**
         * Returns an unmodifiable grade set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code grade} is null.
         */
        public Optional<Grade> getGrade() {
            return Optional.ofNullable(grade);
        }

        /**
         * Sets {@code semester} to this object's {@code semester}.
         * A defensive copy of {@code semester} is used internally.
         */
        public void setSemester(Semester semester) {
            this.semester = semester;
        }

        /**
         * Returns an unmodifiable semester set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code semester} is null.
         */
        public Optional<Semester> getSemester() {
            return Optional.ofNullable(semester);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateModNameDescriptor)) {
                return false;
            }

            // state check
            UpdateModNameDescriptor e = (UpdateModNameDescriptor) other;

            return getName().equals(e.getName())
                    && getGrade().equals(e.getGrade())
                    && getSemester().equals((e.getSemester()));
        }
    }

    /**
     * Returns true if module can be S/U from data.
     *
     * @param moduleName Module to be checked.
     * @return True if module can be S/U, false otherwise.
     */
    public static boolean isModSuAble(ModuleName moduleName) {
        String status = ModuleInfoRetriever.retrieve(moduleName.fullModName).get("su");
        return status.contains("true");
    }
}
