package de.breuer.bateen.ui.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.breuer.bateen.sensor.DsrcSensor;
import de.breuer.bateen.sensor.Sensor;
import de.breuer.bateen.ui.manually.ManuallyTestViewController;

import static de.breuer.bateen.util.FormFieldHelper.*;

public class DsrcSensorForm implements SensorFormRenderer {

    @Override
    public Component render(Sensor<?> sensor, ManuallyTestViewController controller) {
        DsrcSensor dsrc = (DsrcSensor) sensor.getData();
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        H3 title = new H3("DSRC Sensor");

        addLiveTextField(form, "Record Timestamp", dsrc::getRecordTimeStamp, dsrc::setRecordTimeStamp, "Example: 2023-03-31T13:31:10.003+01:00");
        addLiveTextField(form, "Vehicle Registration Plate", dsrc::getVehicleRegistrationPlate, dsrc::setVehicleRegistrationPlate, "Example: BT.ST 133348");

        addLiveCheckbox(form, "Speeding Event", dsrc::isSpeedingEvent, dsrc::setSpeedingEvent);
        addLiveCheckbox(form, "Driving Without Valid Card", dsrc::isDrivingWithoutValidCard, dsrc::setDrivingWithoutValidCard);
        addLiveCheckbox(form, "Driver Card", dsrc::isDriverCard, dsrc::setDriverCard);
        addLiveCheckbox(form, "Card Insertion", dsrc::isCardInsertion, dsrc::setCardInsertion);
        addLiveCheckbox(form, "Motion Data Error", dsrc::isMotionDataError, dsrc::setMotionDataError);
        addLiveCheckbox(form, "Vehicle Motion Conflict", dsrc::isVehicleMotionConflict, dsrc::setVehicleMotionConflict);
        addLiveCheckbox(form, "Second Driver Card", dsrc::isSecondDriverCard, dsrc::setSecondDriverCard);
        addLiveCheckbox(form, "Current Activity Driving", dsrc::isCurrentActivityDriving, dsrc::setCurrentActivityDriving);
        addLiveCheckbox(form, "Last Session Closed", dsrc::isLastSessionClosed, dsrc::setLastSessionClosed);
        addLiveCheckbox(form, "Power Supply Interruption", dsrc::isPowerSupplyInterruption, dsrc::setPowerSupplyInterruption);

        addLiveIntegerField(form, "Sensor Fault", dsrc::getSensorFault, dsrc::setSensorFault, "Example: 0");
        addLiveLongField(form, "Time Adjustment", dsrc::getTimeAdjustment, dsrc::setTimeAdjustment, "Example: 1234567");
        addLiveLongField(form, "Latest Breach Attempt", dsrc::getLatestBreachAttempt, dsrc::setLatestBreachAttempt, "Example: 1234567");
        addLiveLongField(form, "Last Calibration Data", dsrc::getLastCalibrationData, dsrc::setLastCalibrationData, "Example: 1732273177");
        addLiveLongField(form, "Previous Calibration Data", dsrc::getPrevCalibrationData, dsrc::setPrevCalibrationData, "Example: 1732273177");
        addLiveLongField(form, "Date Tacho Connected", dsrc::getDateTachoConnected, dsrc::setDateTachoConnected, "Example: 1234567");
        addLiveIntegerField(form, "Current Speed", dsrc::getCurrentSpeed, dsrc::setCurrentSpeed, "Example: 140");
        addLiveLongField(form, "Timestamp", dsrc::getTimeStamp, dsrc::setTimeStamp, "Example: 1234567");
        addLiveLongField(form, "Latest Authenticated Position", dsrc::getLatestAuthenticatedPosition, dsrc::setLatestAuthenticatedPosition, "Example: -1");

        addLiveLongField(form, "Continuous Driving Time", dsrc::getContinuousDrivingTime, dsrc::setContinuousDrivingTime, "Example: 14");
        addLiveLongField(form, "Daily Driving Time (Shift)", dsrc::getDailyDrivingTimeShift, dsrc::setDailyDrivingTimeShift, "Example: 0");
        addLiveLongField(form, "Daily Driving Time (Week)", dsrc::getDailyDrivingTimeWeek, dsrc::setDailyDrivingTimeWeek, "Example: 0");
        addLiveLongField(form, "Weekly Driving Time", dsrc::getWeeklyDrivingTime, dsrc::setWeeklyDrivingTime, "Example: 0");
        addLiveLongField(form, "Fortnightly Driving Time", dsrc::getFortnightlyDrivingTime, dsrc::setFortnightlyDrivingTime, "Example: 0");

        VerticalLayout wrapper = new VerticalLayout();
        wrapper.add(title, form);
        return wrapper;
    }
}
