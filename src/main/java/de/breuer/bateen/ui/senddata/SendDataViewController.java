package de.breuer.bateen.ui.senddata;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.sensor.AklsSensor;
import de.breuer.bateen.sensor.DsrcSensor;
import de.breuer.bateen.sensor.VehicleSensor;
import de.breuer.bateen.service.DsrcSensorService;
import de.breuer.bateen.service.SensorService;
import org.springframework.stereotype.Component;

@Component
public class SendDataViewController {

    private final DsrcSensorService dsrcSensorService;
    private SensorService sensorService;

    public SendDataViewController(DsrcSensorService dsrcSensorService, SensorService sensorService) {
        this.dsrcSensorService = dsrcSensorService;
        this.sensorService = sensorService;
    }

    public void sendSensorData() {
        DsrcSensor sensor = ConfigController.getDsrcSensor();
        AklsSensor aklsSensor = ConfigController.getAklsSensor();
        VehicleSensor vehicleSensor = ConfigController.getVehicleSensor();
        if (sensor != null && aklsSensor != null && vehicleSensor != null ) {
            sensorService.postSensorData();
        } else {
            throw new IllegalStateException("No Sensor available");
        }
    }

    public void sendDsrcSensorData() {
        DsrcSensor sensor = ConfigController.getDsrcSensor();
        if (sensor != null) {
            dsrcSensorService.sendDsrcSensor();
        } else {
            throw new IllegalStateException("No Sensor available");
        }
    }
}
