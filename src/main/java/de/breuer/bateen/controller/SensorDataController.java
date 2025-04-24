package de.breuer.bateen.controller;

import de.breuer.bateen.sensor.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SensorDataController {

    public List<Sensor<?>> getSensorData() {
        return List.of(
                ConfigController.getAklsSensor(),
                ConfigController.getIrCameraSensor(),
                ConfigController.getVehicleSensor(),
                ConfigController.getDsrcSensor()
        );
    }
}
