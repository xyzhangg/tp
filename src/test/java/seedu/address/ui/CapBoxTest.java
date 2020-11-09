package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for CapBox
 */
public class CapBoxTest {

    @Test
    public void setCapDisplayMethodTestSuccess() {
        CapBox capBox = new CapBox("0");
        String expectedDisplaySuccess = "Current CAP: 5";

        capBox.setCapDisplay("5");
        String testDisplay = capBox.getCurrentCapDisplay().getText();

        assertEquals(testDisplay, expectedDisplaySuccess);

    }

    @Test
    public void setCapDisplayMethodTestRemainSameFalse() {
        CapBox capBox = new CapBox("0");
        String expectedDisplayFail = "Current CAP: 0";

        capBox.setCapDisplay("5");
        String testDisplay = capBox.getCurrentCapDisplay().getText();

        assertNotEquals(expectedDisplayFail, testDisplay);
    }
}
