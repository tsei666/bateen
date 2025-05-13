package de.breuer.bateen.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.breuer.bateen.model.VehicleType;
import de.breuer.bateen.model.ir.IrImageModel;
import de.breuer.bateen.sensor.AklsSensor;
import de.breuer.bateen.sensor.DsrcSensor;
import de.breuer.bateen.sensor.IrCameraSensor;
import de.breuer.bateen.sensor.VehicleSensor;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SensorFactoryDiffblueTest {
    /**
     * Test {@link SensorFactory#createDefaultAklsSensor()}.
     * <p>
     * Method under test: {@link SensorFactory#createDefaultAklsSensor()}
     */
    @Test
    @DisplayName("Test createDefaultAklsSensor()")
    @Tag("MaintainedByDiffblue")
    void testCreateDefaultAklsSensor() {
        // Arrange and Act
        AklsSensor actualCreateDefaultAklsSensorResult = (new SensorFactory()).createDefaultAklsSensor();

        // Assert
        assertEquals("", actualCreateDefaultAklsSensorResult.getSensorRecordId());
        assertEquals("", actualCreateDefaultAklsSensorResult.getAnprCountry());
        assertEquals("", actualCreateDefaultAklsSensorResult.getAnprNumberPlate());
        assertEquals("", actualCreateDefaultAklsSensorResult.getAnprOverviewPicture());
        assertEquals("", actualCreateDefaultAklsSensorResult.getAnprPlatePicture());
        assertEquals("", actualCreateDefaultAklsSensorResult.getAnprSideviewPicture());
        assertEquals(0, actualCreateDefaultAklsSensorResult.getAnprCountryConfidence());
        assertEquals(0, actualCreateDefaultAklsSensorResult.getAnprNumberPlateConfidence());
    }

    /**
     * Test {@link SensorFactory#createDefaultVehicleSensor()}.
     * <p>
     * Method under test: {@link SensorFactory#createDefaultVehicleSensor()}
     */
    @Test
    @DisplayName("Test createDefaultVehicleSensor()")
    @Tag("MaintainedByDiffblue")
    void testCreateDefaultVehicleSensor() {
        // Arrange and Act
        VehicleSensor actualCreateDefaultVehicleSensorResult = (new SensorFactory()).createDefaultVehicleSensor();

        // Assert
        assertEquals("", actualCreateDefaultVehicleSensorResult.getSensorRecordId());
        assertEquals("", actualCreateDefaultVehicleSensorResult.getDetails());
        assertEquals("", actualCreateDefaultVehicleSensorResult.getUnNumber());
        assertEquals(0, actualCreateDefaultVehicleSensorResult.getVehicleAxleCount());
        assertEquals(0, actualCreateDefaultVehicleSensorResult.getVehicleHeight());
        assertEquals(0, actualCreateDefaultVehicleSensorResult.getVehicleLength());
        assertEquals(0, actualCreateDefaultVehicleSensorResult.getVehicleLengthConfidence());
        assertEquals(0, actualCreateDefaultVehicleSensorResult.getVehicleSpeed());
        assertEquals(0, actualCreateDefaultVehicleSensorResult.getVehicleWeightClass());
        assertEquals(0, actualCreateDefaultVehicleSensorResult.getVehicleWidth());
        assertEquals(VehicleType.NONE, actualCreateDefaultVehicleSensorResult.getVehicleType());
        assertFalse(actualCreateDefaultVehicleSensorResult.isExtraWideVehicle());
        assertFalse(actualCreateDefaultVehicleSensorResult.isHasWasteSign());
    }

    /**
     * Test {@link SensorFactory#createDefaultDsrcSensor()}.
     * <p>
     * Method under test: {@link SensorFactory#createDefaultDsrcSensor()}
     */
    @Test
    @DisplayName("Test createDefaultDsrcSensor()")
    @Tag("MaintainedByDiffblue")
    void testCreateDefaultDsrcSensor() {
        // Arrange and Act
        DsrcSensor actualCreateDefaultDsrcSensorResult = (new SensorFactory()).createDefaultDsrcSensor();

        // Assert
        assertEquals("", actualCreateDefaultDsrcSensorResult.getSensorRecordId());
        assertEquals("", actualCreateDefaultDsrcSensorResult.getRecordTimeStamp());
        assertEquals("", actualCreateDefaultDsrcSensorResult.getVehicleRegistrationPlate());
        assertEquals(0, actualCreateDefaultDsrcSensorResult.getCurrentSpeed());
        assertEquals(0, actualCreateDefaultDsrcSensorResult.getSensorFault());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getContinuousDrivingTime());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getDailyDrivingTimeShift());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getDailyDrivingTimeWeek());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getDateTachoConnected());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getFortnightlyDrivingTime());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getLastCalibrationData());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getLatestAuthenticatedPosition());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getLatestBreachAttempt());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getPrevCalibrationData());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getTimeAdjustment());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getTimeStamp());
        assertEquals(0L, actualCreateDefaultDsrcSensorResult.getWeeklyDrivingTime());
        assertFalse(actualCreateDefaultDsrcSensorResult.isCardInsertion());
        assertFalse(actualCreateDefaultDsrcSensorResult.isCurrentActivityDriving());
        assertFalse(actualCreateDefaultDsrcSensorResult.isDriverCard());
        assertFalse(actualCreateDefaultDsrcSensorResult.isDrivingWithoutValidCard());
        assertFalse(actualCreateDefaultDsrcSensorResult.isLastSessionClosed());
        assertFalse(actualCreateDefaultDsrcSensorResult.isMotionDataError());
        assertFalse(actualCreateDefaultDsrcSensorResult.isPowerSupplyInterruption());
        assertFalse(actualCreateDefaultDsrcSensorResult.isSecondDriverCard());
        assertFalse(actualCreateDefaultDsrcSensorResult.isSpeedingEvent());
        assertFalse(actualCreateDefaultDsrcSensorResult.isVehicleMotionConflict());
    }

    /**
     * Test {@link SensorFactory#createDefaultIrCameraSensor()}.
     * <p>
     * Method under test: {@link SensorFactory#createDefaultIrCameraSensor()}
     */
    @Test
    @DisplayName("Test createDefaultIrCameraSensor()")
    @Tag("MaintainedByDiffblue")
    void testCreateDefaultIrCameraSensor() {
        // Arrange and Act
        IrCameraSensor actualCreateDefaultIrCameraSensorResult = (new SensorFactory()).createDefaultIrCameraSensor();

        // Assert
        assertEquals("", actualCreateDefaultIrCameraSensorResult.getSensorRecordId());
        List<IrImageModel> irImages = actualCreateDefaultIrCameraSensorResult.getIrImages();
        assertTrue(irImages.isEmpty());
        assertSame(irImages, actualCreateDefaultIrCameraSensorResult.getIrMaxTempValues());
        assertSame(irImages, actualCreateDefaultIrCameraSensorResult.getIrMeanTempValues());
    }
}
