package de.breuer.bateen.ui.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.breuer.bateen.sensor.IrCameraSensor;
import de.breuer.bateen.sensor.Sensor;
import de.breuer.bateen.ui.manually.ManuallyTestViewController;

import static de.breuer.bateen.util.FormFieldHelper.*;

public class IrCameraSensorForm implements SensorFormRenderer {

    @Override
    public Component render(Sensor<?> sensor, ManuallyTestViewController controller) {
        IrCameraSensor ir = (IrCameraSensor) sensor.getData();
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        addImageSelectorList("irImages", ir.getIrImages(), ir::setIrImages, form);
        addLiveFloatListField(form, "IR Max Temperature", ir.getIrMaxTempValues(), ir::setIrMaxTempValues);
        addLiveFloatListField(form, "IR Mean Temperature", ir.getIrMeanTempValues(), ir::setIrMeanTempValues);

        VerticalLayout wrapper = new VerticalLayout();
        wrapper.add(form);
        return wrapper;
    }
}
