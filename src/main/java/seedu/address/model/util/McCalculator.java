package seedu.address.model.util;

import java.util.List;

import seedu.address.model.module.Cap;
import seedu.address.model.module.Module;

/**
 * Supports the function to calculate the total number of MCs taken currently.
 */
public class McCalculator {

    /**
     * Calculates the total number of MCs from all modules with grades in the module list.
     *
     * @param moduleList list of modules
     * @return sum of MCs of all modules with grades in the list.
     */
    public static int calculateMcTaken(List<Module> moduleList) {
        int totalMc = 0;
        for (Module m : moduleList) {
            if (m.hasGrade()) {
                totalMc += m.getModularCredit().modularCredit;
            }
        }
        return totalMc;
    }

    /**
     * Calculates the total number of MCs S/U-ed from modules in the module list.
     *
     * @param moduleList list of modules
     * @return total MCs S/U-ed.
     */
    public static int calculateMcFromSu(List<Module> moduleList) {
        int mcFromSu = 0;
        for (Module m : moduleList) {
            if (m.getGrade().toString().equals(Cap.SU.toString())) {
                mcFromSu += m.getModularCredit().modularCredit;
            }
        }
        return mcFromSu;
    }
}
