package de.breuer.bateen.ui.components;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.breuer.bateen.sensor.VehicleSensor;

public class VehicleSensorCard extends VerticalLayout {

    public VehicleSensorCard(VehicleSensor vehicle) {
        setWidth("350px");
        setPadding(false);
        setSpacing(false);
        setStyle();
        H3 title = new H3("Vehicle Sensor");

        add(
                title,
                new Paragraph("Weight Class: " + vehicle.getVehicleWeightClass()),
                new Paragraph("Vehicle Type: " + vehicle.getVehicleType()),
                new Paragraph("Height: " + vehicle.getVehicleHeight()),
                new Paragraph("Width: " + vehicle.getVehicleWidth()),
                new Paragraph("Length: " + vehicle.getVehicleLength()),
                new Paragraph("Length Confidence: " + vehicle.getVehicleLengthConfidence()),
                new Paragraph("Axle Count: " + vehicle.getVehicleAxleCount()),
                new Paragraph("Speed: " + vehicle.getVehicleSpeed()),
                new Paragraph("Details: " + vehicle.getDetails()),
                new Paragraph("UN Number: " + vehicle.getUnNumber()),
                new Paragraph("Has Waste Sign: " + vehicle.isHasWasteSign()),
                new Paragraph("Extra Wide Vehicle: " + vehicle.isExtraWideVehicle())
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
