package de.breuer.bateen.ui.components;

import com.vaadin.flow.component.Component;
import de.breuer.bateen.sensor.*;

public class SensorCardFactory {

    public static Component create(Sensor<?> sensor) {
        Object data = sensor.getData();

        if (data instanceof AklsSensor akls) {
            return new AklsSensorCard(akls);
        }

        if (data instanceof DsrcSensor dsrc) {
            return new DsrcSensorCard(dsrc);
        }

        if (data instanceof IrCameraSensor ir) {
            return new IrCameraSensorCard(ir);
        }

        if (data instanceof VehicleSensor vehicle) {
            return new VehicleSensorCard(vehicle);
        }

        throw new IllegalArgumentException("Unknown sensor type: " + data.getClass().getName());
    }
}
