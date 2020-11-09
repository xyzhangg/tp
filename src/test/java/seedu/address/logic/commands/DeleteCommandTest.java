package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.setInvalidSemester;
import static seedu.address.logic.commands.CommandTestUtil.setValidCorrectSemester;
import static seedu.address.logic.commands.CommandTestUtil.setValidWrongSemester;
import static seedu.address.logic.commands.CommandTestUtil.showModuleAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.COM_ORG;
import static seedu.address.testutil.TypicalModules.EFF_COM;
import static seedu.address.testutil.TypicalModules.GER;
import static seedu.address.testutil.TypicalModules.getTypicalGradeBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.GetModuleIndex;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.GoalTarget;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleName;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterManager;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalGradeBook(), new UserPrefs(), new GoalTarget());

    private final ModuleName nameFirstModule = COM_ORG.getModuleName();
    private final ModuleName nameSecondModule = EFF_COM.getModuleName();

    private final Index indexFirstModule =
            GetModuleIndex.getIndex(model.getFilteredModuleList(), nameFirstModule);
    private final Index indexSecondModule =
            GetModuleIndex.getIndex(model.getFilteredModuleList(), nameSecondModule);

    @Test
    public void constructor_nullModuleName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCommand(null));
    }

    @Test
    public void execute_validModuleNameUnfilteredList_success() {
        setValidCorrectSemester();

        Module moduleToDelete = model.getFilteredModuleList().get(indexFirstModule.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(nameFirstModule);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getGradeBook(), new UserPrefs(), new GoalTarget());

        expectedModel.deleteModule(moduleToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_moduleCodeNotInListUnfilteredList_throwsCommandException() {
        setValidCorrectSemester();

        String moduleNotInList = GER.getModuleName().fullModName;
        ModuleName invalidModuleName = new ModuleName(moduleNotInList);
        DeleteCommand deleteCommand = new DeleteCommand(invalidModuleName);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_NAME);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        setValidCorrectSemester();

        showModuleAtIndex(model, indexFirstModule);

        Module moduleToDelete = model.getFilteredModuleList().get(indexFirstModule.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(nameFirstModule);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        Model expectedModel = new ModelManager(model.getGradeBook(), new UserPrefs(), new GoalTarget());
        expectedModel.deleteModule(moduleToDelete);
        showNoModule(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showModuleAtIndex(model, indexFirstModule);

        Index outOfBoundIndex = indexSecondModule;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getGradeBook().getModuleList().size());

        DeleteCommand deleteCommand = new DeleteCommand(nameSecondModule);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_NAME);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(COM_ORG.getModuleName());
        DeleteCommand deleteSecondCommand = new DeleteCommand(EFF_COM.getModuleName());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(COM_ORG.getModuleName());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoModule(Model model) {
        model.updateFilteredModuleList(p -> false);

        assertTrue(model.getFilteredModuleList().isEmpty());
    }

    @Test
    public void execute_invalidSemester_throwsCommandException() {
        setInvalidSemester();

        DeleteCommand deleteCommand = new DeleteCommand(nameFirstModule);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_COMMAND_SEQUENCE);
    }

    @Test
    public void execute_wrongSemester_throwsCommandException() {
        SemesterManager semesterManager = SemesterManager.getInstance();
        setValidWrongSemester();

        DeleteCommand deleteCommand = new DeleteCommand(nameFirstModule);
        Semester semesterOfFirstModule = COM_ORG.getSemester();

        String expectedMessage = Messages.MESSAGE_DELETE_MODULE_IN_WRONG_SEMESTER + semesterOfFirstModule + ".\n"
                + Messages.MESSAGE_CURRENT_SEMESTER + semesterManager.getCurrentSemester() + ".\n"
                + Messages.MESSAGE_DIRECT_TO_CORRECT_SEMESTER + semesterOfFirstModule
                + Messages.MESSAGE_DIRECT_TO_CORRECT_SEMESTER_TO_DELETE;

        assertCommandFailure(deleteCommand, model, expectedMessage);
    }
}
