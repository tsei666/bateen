package de.breuer.bateen.ui.manually;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.sensor.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManuallyTestViewController {

    public List<Sensor<?>> getSensorData() {
        return List.of(
            ConfigController.getAklsSensor(),
            ConfigController.getIrCameraSensor(),
            ConfigController.getVehicleSensor(),
            ConfigController.getDsrcSensor()
        );
    }

    public <T extends Sensor<T>> void saveSensorData(Sensor<?> sensor) {
        if (sensor instanceof AklsSensor akls) {
            ConfigController.setAklsSensor(akls);
        } else if (sensor instanceof IrCameraSensor ir) {
            ConfigController.setIrCameraSensor(ir);
        } else if (sensor instanceof VehicleSensor vehicle) {
            ConfigController.setVehicleSensor(vehicle);
        } else if (sensor instanceof DsrcSensor dsrc) {
            ConfigController.setDsrcSensor(dsrc);
        }
    }
}
