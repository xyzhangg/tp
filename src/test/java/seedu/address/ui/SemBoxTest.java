package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for SemBox
 */
public class SemBoxTest {

    @Test
    public void setSemDisplayMethodTestSuccess() {
        SemBox semBox = new SemBox("Y1S1");
        String expectedDisplaySuccess = "Currently editing: Y1S1";

        String testDisplay = semBox.getCurrentSemDisplay().getText();

        assertEquals(testDisplay, expectedDisplaySuccess);

    }

    @Test
    public void setSemDisplayMethodTestRemainSameFalse() {
        SemBox semBox = new SemBox("Y2S2");
        String expectedDisplayFail = "Currently editing: Y1S1";

        String testDisplay = semBox.getCurrentSemDisplay().getText();

        assertNotEquals(expectedDisplayFail, testDisplay);
    }
}
