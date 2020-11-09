package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidModName(String)}
 */
public class ModuleName {

    public static final String MESSAGE_CONSTRAINTS =
            "Module codes should only contain alphanumeric characters, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullModName;

    /**
     * Constructs a {@code ModuleName}.
     *
     * @param modName A valid module name.
     */
    public ModuleName(String modName) {
        requireNonNull(modName);
        checkArgument(isValidModName(modName), MESSAGE_CONSTRAINTS);
        fullModName = modName;
    }

    /**
     * Returns true if a given string is a valid modName.
     */
    public static boolean isValidModName(String test) {
        return test.matches(VALIDATION_REGEX) && !test.contains(" ");
    }


    @Override
    public String toString() {
        return fullModName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleName // instanceof handles nulls
                && fullModName.equals(((ModuleName) other).fullModName)); // state check
    }

    @Override
    public int hashCode() {
        return fullModName.hashCode();
    }

}
