package de.breuer.bateen.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DisplayDiffblueTest {
    /**
     * Test {@link Display#setControlMode(int)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then {@link Display} (default constructor) ControlModeValue is one.</li>
     * </ul>
     * <p>
     * Method under test: {@link Display#setControlMode(int)}
     */
    @Test
    @DisplayName("Test setControlMode(int); when one; then Display (default constructor) ControlModeValue is one")
    @Tag("MaintainedByDiffblue")
    void testSetControlMode_whenOne_thenDisplayControlModeValueIsOne() {
        // Arrange
        Display display = new Display();

        // Act
        display.setControlMode(1);

        // Assert
        assertEquals(1, display.getControlModeValue());
        assertEquals(ControlModeModel.CONTROL_MODE_STATIONARY, display.getControlMode());
    }

    /**
     * Test {@link Display#setControlMode(int)}.
     * <ul>
     *   <li>When three.</li>
     *   <li>Then {@link Display} (default constructor) ControlModeValue is zero.</li>
     * </ul>
     * <p>
     * Method under test: {@link Display#setControlMode(int)}
     */
    @Test
    @DisplayName("Test setControlMode(int); when three; then Display (default constructor) ControlModeValue is zero")
    @Tag("MaintainedByDiffblue")
    void testSetControlMode_whenThree_thenDisplayControlModeValueIsZero() {
        // Arrange
        Display display = new Display();

        // Act
        display.setControlMode(3);

        // Assert that nothing has changed
        assertEquals(0, display.getControlModeValue());
        assertEquals(ControlModeModel.CONTROL_MODE_INVALID, display.getControlMode());
    }

    /**
     * Test {@link Display#getControlModeValue()}.
     * <p>
     * Method under test: {@link Display#getControlModeValue()}
     */
    @Test
    @DisplayName("Test getControlModeValue()")
    @Tag("MaintainedByDiffblue")
    void testGetControlModeValue() {
        // Arrange, Act and Assert
        assertEquals(0, (new Display()).getControlModeValue());
    }
}
