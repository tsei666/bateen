package de.breuer.bateen.sensor.dto;

import lombok.Data;

@Data
public class DsrcSensorDto {
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
}
