package seedu.address.model.util;

import java.util.List;

import seedu.address.model.module.Cap;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;

/**
 * Supports the function to calculate CAP
 * given a list of modules.
 */
public class CapCalculator {

    /**
     * Calculates the CAP given a list of modules
     * by deriving the total sum of the grade points
     * of all the modules in the moduleList and
     * subsequently dividing the sum by the number
     * of modules in the moduleList.
     *
     * @param moduleList the list of modules to calculate CAP for.
     * @return the calculated CAP from the list of modules.
     */
    public static double calculateCap(List<Module> moduleList) {
        double totalPoints = 0.0;
        int totalModularCredits = 0;
        for (Module m : moduleList) {
            if (m.hasGrade() && !m.getGrade().toString().equals(Cap.SU.toString())) {
                int modularCredit = m.getModularCredit().modularCredit;
                Grade currentGrade = m.getGrade();
                double currentGradePoint = currentGrade.getGradePoint();
                totalPoints += modularCredit * currentGradePoint;
                totalModularCredits += modularCredit;
            }
        }
        double cap = totalPoints / totalModularCredits;

        if (totalModularCredits == 0) {
            return 0;
        }

        return cap;
    }
}
