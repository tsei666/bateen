package de.breuer.bateen.sensor;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalTime;

@Data
public class DsrcSensor implements Sensor<DsrcSensor> {
    @Pattern(regexp = "^.{36}$", message = "StDataModel -> sensorRecordId must be 36 characters long")
    private String sensorRecordId;

    private String recordTimeStamp;
    private String vehicleRegistrationPlate;
    private boolean speedingEvent;
    private boolean drivingWithoutValidCard;
    private boolean driverCard;
    private boolean cardInsertion;
    private boolean motionDataError;
    private boolean vehicleMotionConflict;
    private boolean secondDriverCard;
    private boolean currentActivityDriving;
    private boolean lastSessionClosed;
    private boolean powerSupplyInterruption;

    private int sensorFault;
    private long timeAdjustment;
    private long latestBreachAttempt;
    private long lastCalibrationData;
    private long prevCalibrationData;
    private long dateTachoConnected;
    private int currentSpeed;
    private long timeStamp;
    private long latestAuthenticatedPosition;

    private long continuousDrivingTime;
    private long dailyDrivingTimeShift;
    private long dailyDrivingTimeWeek;
    private long weeklyDrivingTime;
    private long fortnightlyDrivingTime;

    @Override
    public DsrcSensor getData() {
        return this;
    }

    @Override
    public void setData(DsrcSensor data) {
        this.recordTimeStamp = data.recordTimeStamp;
        this.vehicleRegistrationPlate = data.vehicleRegistrationPlate;
        this.speedingEvent = data.speedingEvent;
        this.drivingWithoutValidCard = data.drivingWithoutValidCard;
        this.driverCard = data.driverCard;
        this.cardInsertion = data.cardInsertion;
        this.motionDataError = data.motionDataError;
        this.vehicleMotionConflict = data.vehicleMotionConflict;
        this.secondDriverCard = data.secondDriverCard;
        this.currentActivityDriving = data.currentActivityDriving;
        this.lastSessionClosed = data.lastSessionClosed;
        this.powerSupplyInterruption = data.powerSupplyInterruption;

        this.sensorFault = data.sensorFault;
        this.timeAdjustment = data.timeAdjustment;
        this.latestBreachAttempt = data.latestBreachAttempt;
        this.lastCalibrationData = data.lastCalibrationData;
        this.prevCalibrationData = data.prevCalibrationData;
        this.dateTachoConnected = data.dateTachoConnected;
        this.currentSpeed = data.currentSpeed;
        this.timeStamp = data.timeStamp;
        this.latestAuthenticatedPosition = data.latestAuthenticatedPosition;

        this.continuousDrivingTime = data.continuousDrivingTime;
        this.dailyDrivingTimeShift = data.dailyDrivingTimeShift;
        this.dailyDrivingTimeWeek = data.dailyDrivingTimeWeek;
        this.weeklyDrivingTime = data.weeklyDrivingTime;
        this.fortnightlyDrivingTime = data.fortnightlyDrivingTime;
    }

    @Override
    public String getTimestamp() {
        return LocalTime.now().toString();
    }
}
