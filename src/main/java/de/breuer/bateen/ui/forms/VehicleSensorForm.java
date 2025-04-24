package de.breuer.bateen.ui.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.breuer.bateen.sensor.Sensor;
import de.breuer.bateen.sensor.VehicleSensor;
import de.breuer.bateen.ui.manually.ManuallyTestViewController;

import static de.breuer.bateen.util.FormFieldHelper.*;

public class VehicleSensorForm implements SensorFormRenderer {

    @Override
    public Component render(Sensor<?> sensor, ManuallyTestViewController controller) {
        VehicleSensor vehicle = (VehicleSensor) sensor.getData();
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        addLiveIntegerField(form, "Weight Class", vehicle::getVehicleWeightClass, vehicle::setVehicleWeightClass, "Example: 400");
        addLiveIntegerField(form, "Vehicle Type", vehicle::getVehicleType, vehicle::setVehicleType, "Example: 7");
        addLiveIntegerField(form, "Height", vehicle::getVehicleHeight, vehicle::setVehicleHeight, "Example: 200");
        addLiveIntegerField(form, "Width", vehicle::getVehicleWidth, vehicle::setVehicleWidth, "Example: 370");
        addLiveIntegerField(form, "Length", vehicle::getVehicleLength, vehicle::setVehicleLength, "Example: 695");
        addLiveIntegerField(form, "Length Confidence", vehicle::getVehicleLengthConfidence, vehicle::setVehicleLengthConfidence, "Example: 100");
        addLiveIntegerField(form, "Axle Count", vehicle::getVehicleAxleCount, vehicle::setVehicleAxleCount, "Example: 2");
        addLiveIntegerField(form, "Speed", vehicle::getVehicleSpeed, vehicle::setVehicleSpeed, "Example: 80");
        addLiveTextField(form, "Details", vehicle::getDetails, vehicle::setDetails, "Example: -");
        addLiveTextField(form, "UN Number", vehicle::getUnNumber, vehicle::setUnNumber, "Example: -");
        addLiveCheckbox(form, "Has Waste Sign", vehicle::isHasWasteSign, vehicle::setHasWasteSign);
        addLiveCheckbox(form, "Extra Wide Vehicle", vehicle::isExtraWideVehicle, vehicle::setExtraWideVehicle);

        VerticalLayout wrapper = new VerticalLayout();
        wrapper.add(form);
        return wrapper;
    }
}
