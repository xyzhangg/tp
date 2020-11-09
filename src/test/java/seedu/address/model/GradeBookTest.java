package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_B;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.COM_ORG;
import static seedu.address.testutil.TypicalModules.getTypicalGradeBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.ModuleBuilder;

public class GradeBookTest {

    private final GradeBook gradeBook = new GradeBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), gradeBook.getModuleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> gradeBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyGradeBook_replacesData() {
        GradeBook newData = getTypicalGradeBook();
        gradeBook.resetData(newData);
        assertEquals(newData, gradeBook);
    }

    @Test
    public void resetData_withEmptySampleGradeBook_replacesData() {
        ReadOnlyGradeBook sampleGradeBook = SampleDataUtil.getSampleGradeBook();
        gradeBook.addModule(COM_ORG);
        gradeBook.resetData(sampleGradeBook);
        assertEquals(sampleGradeBook, gradeBook);
    }

    @Test
    public void resetData_withDuplicateModules_throwsDuplicateModuleException() {
        // Two persons with the same identity fields
        Module updatedModule = new ModuleBuilder(COM_ORG).withGrade(VALID_GRADE_B)
                .build();
        List<Module> newModules = Arrays.asList(COM_ORG, updatedModule);
        GradeBookStub newData = new GradeBookStub(newModules);

        assertThrows(DuplicateModuleException.class, () -> gradeBook.resetData(newData));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> gradeBook.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInGradeBook_returnsFalse() {
        assertFalse(gradeBook.hasModule(COM_ORG));
    }

    @Test
    public void hasModule_moduleInGradeBook_returnsTrue() {
        gradeBook.addModule(COM_ORG);
        assertTrue(gradeBook.hasModule(COM_ORG));
    }

    @Test
    public void hasModule_moduleWithSameIdentityFieldsInGradeBook_returnsTrue() {
        gradeBook.addModule(COM_ORG);
        Module updatedModule = new ModuleBuilder(COM_ORG).withGrade(VALID_GRADE_B)
                .build();
        assertTrue(gradeBook.hasModule(updatedModule));
    }

    @Test
    public void getModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> gradeBook.getModuleList().remove(0));
    }

    /**
     * A stub ReadOnlyGradeBook whose persons list can violate interface constraints.
     */
    private static class GradeBookStub implements ReadOnlyGradeBook {
        private final ObservableList<Module> modules = FXCollections.observableArrayList();

        GradeBookStub(Collection<Module> modules) {
            this.modules.setAll(modules);
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }
    }

}
