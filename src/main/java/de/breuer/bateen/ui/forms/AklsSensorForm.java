package de.breuer.bateen.ui.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.breuer.bateen.sensor.AklsSensor;
import de.breuer.bateen.sensor.Sensor;
import de.breuer.bateen.ui.manually.ManuallyTestViewController;

import static de.breuer.bateen.util.FormFieldHelper.*;

public class AklsSensorForm implements SensorFormRenderer {

    @Override
    public Component render(Sensor<?> sensor, ManuallyTestViewController controller) {
        AklsSensor akls = (AklsSensor) sensor.getData();
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        addLiveTextField(formLayout, "License Plate", akls::getAnprNumberPlate, akls::setAnprNumberPlate, "Example: ST 133348");
        addLiveIntegerField(formLayout, "License Plate Confidence", akls::getAnprNumberPlateConfidence, akls::setAnprNumberPlateConfidence, "Example: 60");
        addLiveTextField(formLayout, "Country", akls::getAnprCountry, akls::setAnprCountry, "Example: BT");
        addLiveIntegerField(formLayout, "Country Confidence", akls::getAnprCountryConfidence, akls::setAnprCountryConfidence, "Example: 60");
        addImageSelector("anprPlatePicture", akls::getAnprPlatePicture, akls::setAnprPlatePicture, formLayout);
        addImageSelector("anprOverviewPicture", akls::getAnprOverviewPicture, akls::setAnprOverviewPicture, formLayout);
        addImageSelector("anprSideviewPicture", akls::getAnprSideviewPicture, akls::setAnprSideviewPicture, formLayout);

        VerticalLayout wrapper = new VerticalLayout();
        wrapper.add(formLayout);
        return wrapper;
    }
}
