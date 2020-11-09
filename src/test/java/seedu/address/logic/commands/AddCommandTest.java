package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.setInvalidSemester;
import static seedu.address.logic.commands.CommandTestUtil.setValidCorrectSemester;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.GradeBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyGradeBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.module.GoalTarget;
import seedu.address.model.module.Module;
import seedu.address.testutil.ModuleBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        setValidCorrectSemester();

        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new AddCommand(validModule).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validModule), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        AddCommand addCommand = new AddCommand(validModule);
        ModelStub modelStub = new ModelStubWithModule(validModule);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_MODULE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Module softwareEngineering = new ModuleBuilder().withName("CS2103T").build();
        Module computerOrganisation = new ModuleBuilder().withName("CS2100").build();
        AddCommand addSoftwareEngineeringCommand = new AddCommand(softwareEngineering);
        AddCommand addComputerOrganisationCommand = new AddCommand(computerOrganisation);

        // same object -> returns true
        assertTrue(addSoftwareEngineeringCommand.equals(addSoftwareEngineeringCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(softwareEngineering);
        assertTrue(addSoftwareEngineeringCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addSoftwareEngineeringCommand.equals(1));

        // null -> returns false
        assertFalse(addSoftwareEngineeringCommand.equals(null));

        // different person -> returns false
        assertFalse(addSoftwareEngineeringCommand.equals(addComputerOrganisationCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getGradeBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGradeBookFilePath(Path gradeBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGradeBook(ReadOnlyGradeBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGradeBook getGradeBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Module target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModule(Module target, Module updatedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> filterModuleListBySem() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String generateCapAsString() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public double generateCap() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getCurrentMc() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getMcFromSu() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGoalTarget(GoalTarget goalTarget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GoalTarget getGoalTarget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> sortModuleListBySem() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetFilteredList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String generateSem() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends ModelStub {
        private final Module module;

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private class ModelStubAcceptingModuleAdded extends ModelStub {
        final ArrayList<Module> modulesAdded = new ArrayList<>();

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSameModule);
        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public ReadOnlyGradeBook getGradeBook() {
            return new GradeBook();
        }

    }

    @Test
    public void execute_invalidSemester_throwsCommandException() {
        setInvalidSemester();

        Module validModule = new ModuleBuilder().build();
        AddCommand addCommand = new AddCommand(validModule);
        ModelStub modelStub = new ModelStubWithModule(validModule);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_COMMAND_SEQUENCE, () ->
                addCommand.execute(modelStub));
    }
}
