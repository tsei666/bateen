package de.breuer.bateen.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SensorRecordStateDiffblueTest {
    /**
     * Test {@link SensorRecordState#getById(int)}.
     * <p>
     * Method under test: {@link SensorRecordState#getById(int)}
     */
    @Test
    @DisplayName("Test getById(int)")
    @Tag("MaintainedByDiffblue")
    void testGetById() {
        // Arrange, Act and Assert
        assertEquals(SensorRecordState.START, SensorRecordState.getById(1));
    }

    /**
     * Test {@link SensorRecordState#isStart()}.
     * <ul>
     *   <li>Given {@code NA}.</li>
     *   <li>Then return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test: {@link SensorRecordState#isStart()}
     */
    @Test
    @DisplayName("Test isStart(); given 'NA'; then return 'false'")
    @Tag("MaintainedByDiffblue")
    void testIsStart_givenNa_thenReturnFalse() {
        // Arrange, Act and Assert
        assertFalse(SensorRecordState.NA.isStart());
    }

    /**
     * Test {@link SensorRecordState#isStart()}.
     * <ul>
     *   <li>Given {@link SensorRecordState#START}.</li>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test: {@link SensorRecordState#isStart()}
     */
    @Test
    @DisplayName("Test isStart(); given START; then return 'true'")
    @Tag("MaintainedByDiffblue")
    void testIsStart_givenStart_thenReturnTrue() {
        // Arrange, Act and Assert
        assertTrue(SensorRecordState.START.isStart());
    }

    /**
     * Test {@link SensorRecordState#isCompleted()}.
     * <ul>
     *   <li>Given {@link SensorRecordState#COMPLETED}.</li>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test: {@link SensorRecordState#isCompleted()}
     */
    @Test
    @DisplayName("Test isCompleted(); given COMPLETED; then return 'true'")
    @Tag("MaintainedByDiffblue")
    void testIsCompleted_givenCompleted_thenReturnTrue() {
        // Arrange, Act and Assert
        assertTrue(SensorRecordState.COMPLETED.isCompleted());
    }

    /**
     * Test {@link SensorRecordState#isCompleted()}.
     * <ul>
     *   <li>Given {@code NA}.</li>
     *   <li>Then return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test: {@link SensorRecordState#isCompleted()}
     */
    @Test
    @DisplayName("Test isCompleted(); given 'NA'; then return 'false'")
    @Tag("MaintainedByDiffblue")
    void testIsCompleted_givenNa_thenReturnFalse() {
        // Arrange, Act and Assert
        assertFalse(SensorRecordState.NA.isCompleted());
    }
}
