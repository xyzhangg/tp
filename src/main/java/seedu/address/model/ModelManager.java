package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.GoalTarget;
import seedu.address.model.module.Module;
import seedu.address.model.semester.SemesterManager;
import seedu.address.model.util.CapCalculator;
import seedu.address.model.util.McCalculator;
import seedu.address.model.util.ModuleListFilter;
import seedu.address.model.util.ModuleListSorter;

/**
 * Represents the in-memory model of the grade book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final GradeBook gradeBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Module> filteredModules;
    private GoalTarget goalTarget;

    /**
     * Initializes a ModelManager with the given gradeBook and userPrefs.
     */
    public ModelManager(ReadOnlyGradeBook gradeBook, ReadOnlyUserPrefs userPrefs, GoalTarget goalTarget) {
        super();
        requireAllNonNull(gradeBook, userPrefs);

        logger.fine("Initializing with grade book: " + gradeBook + " and user prefs " + userPrefs);

        this.gradeBook = new GradeBook(gradeBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.goalTarget = goalTarget;
        filteredModules = new FilteredList<>(this.gradeBook.getModuleList());
    }

    public ModelManager() {
        this(new GradeBook(), new UserPrefs(), new GoalTarget());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getGradeBookFilePath() {
        return userPrefs.getGradeBookFilePath();
    }

    @Override
    public void setGradeBookFilePath(Path gradeBookFilePath) {
        requireNonNull(gradeBookFilePath);
        userPrefs.setGradeBookFilePath(gradeBookFilePath);
    }

    //=========== GradeBook ================================================================================

    @Override
    public void setGradeBook(ReadOnlyGradeBook gradeBook) {
        this.gradeBook.resetData(gradeBook);
    }

    @Override
    public ReadOnlyGradeBook getGradeBook() {
        return gradeBook;
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return gradeBook.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        gradeBook.removeModule(target);
    }

    @Override
    public void addModule(Module module) {
        gradeBook.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module updatedModule) {
        requireAllNonNull(target, updatedModule);

        gradeBook.setModule(target, updatedModule);
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedGradeBook}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    /**
     * Filters the module list according to semester.
     *
     * @return the filtered list of modules by semester.
     */
    @Override
    public FilteredList<Module> filterModuleListBySem() {
        return ModuleListSorter.sortModuleList(ModuleListFilter.filterModulesBySemester(filteredModules));
    }

    /**
     * Sorts the module list according to semester.
     *
     * @return the filtered list of modules sorted by semester.
     */
    @Override
    public FilteredList<Module> sortModuleListBySem() {
        return ModuleListSorter.sortModuleList(filteredModules);
    }

    /**
     * Resets the filtered list to contain all modules.
     */
    @Override
    public void resetFilteredList() {
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return gradeBook.equals(other.gradeBook)
                && userPrefs.equals(other.userPrefs)
                && filteredModules.equals(other.filteredModules);
    }

    //=========== CAP Calculation ============================================================================

    /**
     * Calculates the CAP of the current list of modules and return it as a string.
     *
     * @return a string representation of the CAP to 2 significant figures.
     */
    @Override
    public String generateCapAsString() {
        double cap = generateCap();
        return String.format("%.2f", cap);
    }

    /**
     * Calculates the CAP of the current list of modules and returns it as a double.
     *
     * @return the CAP as a double value.
     */
    @Override
    public double generateCap() {
        return CapCalculator.calculateCap(filteredModules);
    }

    //=========== MC Calculation =============================================================================

    @Override
    public int getCurrentMc() {
        return McCalculator.calculateMcTaken(filteredModules);
    }

    @Override
    public int getMcFromSu() {
        return McCalculator.calculateMcFromSu(filteredModules);
    }

    //=========== Goal Setting ===============================================================================
    @Override
    public void setGoalTarget(GoalTarget goalTarget) {
        requireAllNonNull(goalTarget);
        this.goalTarget = goalTarget;
    }

    @Override
    public GoalTarget getGoalTarget() {
        return goalTarget;
    }

    @Override
    public String generateSem() {
        SemesterManager semester = SemesterManager.getInstance();
        return semester.getCurrentSemester().toString();
    }
}
