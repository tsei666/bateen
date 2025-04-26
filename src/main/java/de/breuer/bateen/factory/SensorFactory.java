package de.breuer.bateen.factory;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.model.VehicleType;
import de.breuer.bateen.sensor.AklsSensor;
import de.breuer.bateen.sensor.DsrcSensor;
import de.breuer.bateen.sensor.IrCameraSensor;
import de.breuer.bateen.sensor.VehicleSensor;
import de.breuer.bateen.testcases.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
public class SensorFactory {

    @PostConstruct
    public void init() {
        String sharedSensorRecordId = generateSensorRecordId();

        AklsSensor akls = createDefaultAklsSensor();
        akls.setSensorRecordId(sharedSensorRecordId);

        VehicleSensor vehicle = createDefaultVehicleSensor();
        vehicle.setSensorRecordId(sharedSensorRecordId);

        IrCameraSensor ir = createDefaultIrCameraSensor();
        ir.setSensorRecordId(sharedSensorRecordId);

        DsrcSensor dsrc = createDefaultDsrcSensor();
        dsrc.setSensorRecordId(sharedSensorRecordId);

        ConfigController.setAklsSensor(akls);
        ConfigController.setVehicleSensor(vehicle);
        ConfigController.setIrCameraSensor(ir);
        ConfigController.setDsrcSensor(dsrc);
    }

    public String generateSensorRecordId() {
        return UUID.randomUUID().toString();
    }

    public AklsSensor createDefaultAklsSensor() {
        AklsSensor sensor = new AklsSensor();
        sensor.setSensorRecordId("");
        sensor.setAnprNumberPlate("");
        sensor.setAnprNumberPlateConfidence(0);
        sensor.setAnprCountry("");
        sensor.setAnprCountryConfidence(0);
        sensor.setAnprPlatePicture("");
        sensor.setAnprOverviewPicture("");
        sensor.setAnprSideviewPicture("");
        return sensor;
    }

    public VehicleSensor createDefaultVehicleSensor() {
        VehicleSensor sensor = new VehicleSensor();
        sensor.setSensorRecordId("");
        sensor.setVehicleWeightClass(0);
        sensor.setVehicleType(VehicleType.NONE);
        sensor.setVehicleHeight(0);
        sensor.setVehicleWidth(0);
        sensor.setVehicleLength(0);
        sensor.setVehicleLengthConfidence(0);
        sensor.setVehicleAxleCount(0);
        sensor.setVehicleSpeed(0);
        sensor.setDetails("");
        sensor.setUnNumber("");
        sensor.setHasWasteSign(false);
        sensor.setExtraWideVehicle(false);
        return sensor;
    }

    public DsrcSensor createDefaultDsrcSensor() {
        DsrcSensor sensor = new DsrcSensor();
        sensor.setSensorRecordId("");
        sensor.setRecordTimeStamp("");
        sensor.setVehicleRegistrationPlate("");
        sensor.setSpeedingEvent(false);
        sensor.setDrivingWithoutValidCard(false);
        sensor.setDriverCard(false);
        sensor.setCardInsertion(false);
        sensor.setMotionDataError(false);
        sensor.setVehicleMotionConflict(false);
        sensor.setSecondDriverCard(false);
        sensor.setCurrentActivityDriving(false);
        sensor.setLastSessionClosed(false);
        sensor.setPowerSupplyInterruption(false);

        sensor.setSensorFault(0);
        sensor.setTimeAdjustment(0L);
        sensor.setLatestBreachAttempt(0L);
        sensor.setLastCalibrationData(0L);
        sensor.setPrevCalibrationData(0L);
        sensor.setDateTachoConnected(0L);
        sensor.setCurrentSpeed(0);
        sensor.setTimeStamp(0L);
        sensor.setLatestAuthenticatedPosition(0L);

        sensor.setContinuousDrivingTime(0L);
        sensor.setDailyDrivingTimeShift(0L);
        sensor.setDailyDrivingTimeWeek(0L);
        sensor.setWeeklyDrivingTime(0L);
        sensor.setFortnightlyDrivingTime(0L);

        return sensor;
    }

    public IrCameraSensor createDefaultIrCameraSensor() {
        IrCameraSensor sensor = new IrCameraSensor();
        sensor.setSensorRecordId("");
        sensor.setIrImages(Collections.emptyList());
        sensor.setIrMaxTempValues(Collections.emptyList());
        sensor.setIrMeanTempValues(Collections.emptyList());
        return sensor;
    }

    public void generateTestCase(String caseId) {
        TestCaseGenerator testCase = switch (caseId.toUpperCase()) {
            case "GREEN" -> new TestCaseGreen();
            case "B" -> new TestCaseB();
            case "RANDOM" -> new TestCaseRandom();
            case "D" -> new TestCaseD();
            default -> throw new IllegalArgumentException("Unknown test case: " + caseId);
        };
        testCase.generateTestCase();
    }

    public void setExampleData() {
        new TestCaseExample().generateTestCase();
    }

}
