package de.breuer.bateen.ui.components;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.breuer.bateen.sensor.DsrcSensor;

public class DsrcSensorCard extends VerticalLayout {

    public DsrcSensorCard(DsrcSensor dsrc) {
        setWidth("350px");
        setPadding(false);
        setSpacing(false);
        setStyle();
        H3 title = new H3("DSRC Sensor");

        add(
                title,
                new Paragraph("Record Timestamp: " + dsrc.getRecordTimeStamp()),
                new Paragraph("Registration Plate: " + dsrc.getVehicleRegistrationPlate()),
                new Paragraph("Speeding Event: " + dsrc.isSpeedingEvent()),
                new Paragraph("Driving Without Valid Card: " + dsrc.isDrivingWithoutValidCard()),
                new Paragraph("Driver Card: " + dsrc.isDriverCard()),
                new Paragraph("Card Insertion: " + dsrc.isCardInsertion()),
                new Paragraph("Motion Data Error: " + dsrc.isMotionDataError()),
                new Paragraph("Vehicle Motion Conflict: " + dsrc.isVehicleMotionConflict()),
                new Paragraph("Second Driver Card: " + dsrc.isSecondDriverCard()),
                new Paragraph("Current Activity Driving: " + dsrc.isCurrentActivityDriving()),
                new Paragraph("Last Session Closed: " + dsrc.isLastSessionClosed()),
                new Paragraph("Power Supply Interruption: " + dsrc.isPowerSupplyInterruption()),
                new Paragraph("Sensor Fault: " + dsrc.getSensorFault()),
                new Paragraph("Time Adjustment: " + dsrc.getTimeAdjustment()),
                new Paragraph("Latest Breach Attempt: " + dsrc.getLatestBreachAttempt()),
                new Paragraph("Last Calibration Data: " + dsrc.getLastCalibrationData()),
                new Paragraph("Previous Calibration Data: " + dsrc.getPrevCalibrationData()),
                new Paragraph("Date Tacho Connected: " + dsrc.getDateTachoConnected()),
                new Paragraph("Current Speed: " + dsrc.getCurrentSpeed()),
                new Paragraph("Timestamp: " + dsrc.getTimeStamp()),
                new Paragraph("Latest Authenticated Position: " + dsrc.getLatestAuthenticatedPosition()),
                new Paragraph("Continuous Driving Time: " + dsrc.getContinuousDrivingTime()),
                new Paragraph("Daily Driving Time (Shift): " + dsrc.getDailyDrivingTimeShift()),
                new Paragraph("Daily Driving Time (Week): " + dsrc.getDailyDrivingTimeWeek()),
                new Paragraph("Weekly Driving Time: " + dsrc.getWeeklyDrivingTime()),
                new Paragraph("Fortnightly Driving Time: " + dsrc.getFortnightlyDrivingTime())
        );
    }

    private void setStyle() {
        getStyle()
                .set("border", "1px solid #ddd")
                .set("border-radius", "8px")
                .set("padding", "1em")
                .set("box-shadow", "0 2px 4px rgba(0,0,0,0.05)")
                .set("background-color", "#fafafa");
    }
}
