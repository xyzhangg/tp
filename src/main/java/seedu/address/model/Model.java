package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.module.GoalTarget;
import seedu.address.model.module.Module;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' grade book file path.
     */
    Path getGradeBookFilePath();

    /**
     * Sets the user prefs' grade book file path.
     */
    void setGradeBookFilePath(Path gradeBookFilePath);

    /**
     * Replaces grade book data with the data in {@code gradeBook}.
     */
    void setGradeBook(ReadOnlyGradeBook gradeBook);

    /**
     * Returns the GradeBook
     */
    ReadOnlyGradeBook getGradeBook();

    /**
     * Returns true if a module with the same identity as {@code module} exists in the grade book.
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given module.
     * The module must exist in the grade book.
     */
    void deleteModule(Module target);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the grade book.
     */
    void addModule(Module module);

    /**
     * Replaces the given module {@code target} with {@code updatedModule}.
     * {@code target} must exist in the address book.
     * The module identity of {@code updatedModule} must not be the same as another existing module in the address book.
     */
    void setModule(Module target, Module updatedModule);

    /**
     * Returns an unmodifiable view of the filtered module list
     */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Filters the module list by semester.
     *
     * @return the filtered list of modules by semester.
     */
    ObservableList<Module> filterModuleListBySem();

    ObservableList<Module> sortModuleListBySem();

    /**
     * Returns the current CAP as a string.
     */
    String generateCapAsString();

    /**
     * Returns the current CAP as a double.
     */
    double generateCap();

    /**
     * Returns the current sem as a String.
     */
    String generateSem();

    /**
     * Returns the total number of MCs as an integer.
     */
    int getCurrentMc();

    /**
     * Returns total number of MCs that are S/U-ed.
     */
    int getMcFromSu();

    void setGoalTarget(GoalTarget goalTarget);

    GoalTarget getGoalTarget();

    /**
     * Resets the filtered list to contain all modules.
     */
    void resetFilteredList();
}
