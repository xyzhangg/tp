package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> ModuleName.isValidModName(null));

        // invalid name
        assertFalse(ModuleName.isValidModName("")); // empty string
        assertFalse(ModuleName.isValidModName(" ")); // spaces only
        assertFalse(ModuleName.isValidModName("^")); // only non-alphanumeric characters
        assertFalse(ModuleName.isValidModName("21@0!")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ModuleName.isValidModName("2103")); // numbers only
        assertTrue(ModuleName.isValidModName("2103T")); // uppercase alphanumeric characters
        assertTrue(ModuleName.isValidModName("cs1101s")); // lowercase alphanumeric characters
        assertTrue(ModuleName.isValidModName("cS1101s")); // uppercase and lowercase alphanumeric characters
    }
}
