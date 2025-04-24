package de.breuer.bateen.ui.forms;

import com.vaadin.flow.component.Component;
import de.breuer.bateen.sensor.*;
import de.breuer.bateen.ui.manually.ManuallyTestViewController;

public class SensorFormFactory {

    public static Component createForm(Sensor<?> sensor, ManuallyTestViewController controller) {
        Object data = sensor.getData();

        if (data instanceof AklsSensor) {
            return new AklsSensorForm().render(sensor, controller);
        }
        if (data instanceof DsrcSensor) {
            return new DsrcSensorForm().render(sensor, controller);
        }
        if (data instanceof IrCameraSensor) {
            return new IrCameraSensorForm().render(sensor, controller);
        }
        if (data instanceof VehicleSensor) {
            return new VehicleSensorForm().render(sensor, controller);
        }

        throw new IllegalArgumentException("No form renderer available for: " + data.getClass().getSimpleName());
    }
}
