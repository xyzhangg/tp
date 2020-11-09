package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MOD_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.setInvalidSemester;
import static seedu.address.logic.commands.CommandTestUtil.setValidCorrectSemester;
import static seedu.address.logic.commands.CommandTestUtil.setValidWrongSemester;
import static seedu.address.logic.commands.CommandTestUtil.showModuleAtIndex;
import static seedu.address.testutil.TypicalModules.COM_ORG;
import static seedu.address.testutil.TypicalModules.EFF_COM;
import static seedu.address.testutil.TypicalModules.GER;
import static seedu.address.testutil.TypicalModules.getTypicalGradeBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.GetModuleIndex;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand.UpdateModNameDescriptor;
import seedu.address.model.GradeBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.GoalTarget;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleName;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterManager;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.UpdateModNameDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * UpdateCommand.
 */
public class UpdateCommandTest {

    private Model model = new ModelManager(getTypicalGradeBook(), new UserPrefs(), new GoalTarget());

    private final ModuleName nameFirstModule = COM_ORG.getModuleName();
    private final ModuleName nameSecondModule = EFF_COM.getModuleName();

    private final Index indexFirstModule =
            GetModuleIndex.getIndex(model.getFilteredModuleList(), nameFirstModule);

    private final Index indexSecondModule =
            GetModuleIndex.getIndex(model.getFilteredModuleList(), nameSecondModule);

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        setValidCorrectSemester();

        Module updatedModule = new ModuleBuilder().withName(nameFirstModule.fullModName)
                .withGrade(VALID_GRADE_A).build();
        UpdateModNameDescriptor descriptor = new UpdateModNameDescriptorBuilder(updatedModule).build();
        UpdateCommand updateCommand = new UpdateCommand(nameFirstModule, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_MODULE_SUCCESS, updatedModule);

        Model expectedModel = new ModelManager(new GradeBook(model.getGradeBook()), new UserPrefs(),
                new GoalTarget());
        expectedModel.setModule(model.getFilteredModuleList().get(0), updatedModule);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        setValidCorrectSemester();

        ModuleName firstModuleName = COM_ORG.getModuleName();
        Index indexLastModule = GetModuleIndex.getIndex(model.getFilteredModuleList(), firstModuleName);
        Module firstModule = model.getFilteredModuleList().get(indexLastModule.getZeroBased());

        ModuleBuilder moduleInList = new ModuleBuilder(firstModule);
        Module updatedModule = moduleInList.withName(VALID_MOD_NAME_B).withGrade(VALID_GRADE_A)
                .build();

        UpdateModNameDescriptor descriptor =
                new UpdateModNameDescriptorBuilder().withName(VALID_MOD_NAME_B).withGrade(VALID_GRADE_A)
                        .build();
        UpdateCommand updateCommand = new UpdateCommand(firstModuleName, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_MODULE_SUCCESS, updatedModule);

        Model expectedModel = new ModelManager(new GradeBook(model.getGradeBook()), new UserPrefs(),
                new GoalTarget());
        expectedModel.setModule(firstModule, updatedModule);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        setValidCorrectSemester();

        UpdateCommand updateCommand = new UpdateCommand(nameFirstModule, new UpdateModNameDescriptor());
        Module updatedModule = model.getFilteredModuleList().get(indexFirstModule.getZeroBased());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_MODULE_SUCCESS, updatedModule);

        Model expectedModel = new ModelManager(new GradeBook(model.getGradeBook()), new UserPrefs(),
                new GoalTarget());

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        setValidCorrectSemester();

        showModuleAtIndex(model, indexFirstModule);

        Module moduleInFilteredList = model.getFilteredModuleList().get(indexFirstModule.getZeroBased());
        Module updatedModule = new ModuleBuilder(moduleInFilteredList).withName(VALID_MOD_NAME_B).build();
        UpdateCommand updateCommand = new UpdateCommand(nameFirstModule,
                new UpdateModNameDescriptorBuilder().withName(VALID_MOD_NAME_B).build());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_MODULE_SUCCESS, updatedModule);

        Model expectedModel = new ModelManager(new GradeBook(model.getGradeBook()), new UserPrefs(),
                new GoalTarget());
        expectedModel.setModule(model.getFilteredModuleList().get(0), updatedModule);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_moduleCodeNotInListUnfilteredList_failure() {
        setValidCorrectSemester();

        String moduleNotInList = GER.getModuleName().fullModName;
        ModuleName invalidModuleName = new ModuleName(moduleNotInList);
        UpdateCommand.UpdateModNameDescriptor descriptor =
                new UpdateModNameDescriptorBuilder().withName(VALID_MOD_NAME_B).build();
        UpdateCommand updateCommand = new UpdateCommand(invalidModuleName, descriptor);

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_NAME);
    }

    /**
     * update filtered list where module name is not in filtered list,
     * but still in address book
     */
    @Test
    public void execute_invalidModuleNameFilteredList_failure() {
        setValidCorrectSemester();

        showModuleAtIndex(model, indexFirstModule);
        Index outOfBoundIndex = indexSecondModule;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(model.getGradeBook().getModuleList().get(indexSecondModule.getZeroBased()).getModuleName()
                .equals(nameSecondModule));

        UpdateCommand updateCommand = new UpdateCommand(nameSecondModule,
                new UpdateModNameDescriptorBuilder().withName(VALID_MOD_NAME_B).build());

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_NAME);
    }

    @Test
    public void equals() {
        final UpdateCommand standardCommand = new UpdateCommand(nameFirstModule, DESC_A);

        // same values -> returns true
        UpdateModNameDescriptor copyDescriptor = new UpdateModNameDescriptor(DESC_A);
        UpdateCommand commandWithSameValues = new UpdateCommand(nameFirstModule, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(nameSecondModule, DESC_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(nameFirstModule, DESC_B)));
    }

    @Test
    public void execute_invalidSemester_throwsCommandException() {
        setInvalidSemester();

        Module updatedModule = new ModuleBuilder().withName(nameFirstModule.fullModName)
                .withGrade(VALID_GRADE_A).build();
        UpdateCommand.UpdateModNameDescriptor descriptor = new UpdateModNameDescriptorBuilder(updatedModule).build();
        UpdateCommand updateCommand = new UpdateCommand(nameFirstModule, descriptor);

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_COMMAND_SEQUENCE);
    }

    @Test
    public void execute_wrongSemester_throwsCommandException() {
        SemesterManager semesterManager = SemesterManager.getInstance();
        setValidWrongSemester();

        Module updatedModule = new ModuleBuilder().withName(nameFirstModule.fullModName)
                .withGrade(VALID_GRADE_A).build();
        UpdateCommand.UpdateModNameDescriptor descriptor = new UpdateModNameDescriptorBuilder(updatedModule).build();
        UpdateCommand updateCommand = new UpdateCommand(nameFirstModule, descriptor);

        Semester semesterOfFirstModule = COM_ORG.getSemester();

        String expectedMessage = Messages.MESSAGE_UPDATE_MODULE_IN_WRONG_SEMESTER + semesterOfFirstModule + ".\n"
                + Messages.MESSAGE_CURRENT_SEMESTER + semesterManager.getCurrentSemester() + ".\n"
                + Messages.MESSAGE_DIRECT_TO_CORRECT_SEMESTER + semesterOfFirstModule
                + Messages.MESSAGE_DIRECT_TO_CORRECT_SEMESTER_TO_UPDATE;

        assertCommandFailure(updateCommand, model, expectedMessage);
    }
}
