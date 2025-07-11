package de.breuer.bateen.ui.manually;

import de.breuer.bateen.controller.SensorDataController;
import de.breuer.bateen.factory.SensorFactory;
import de.breuer.bateen.sensor.Sensor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManuallyTestViewController {

    private final SensorFactory sensorFactory;
    private final SensorDataController sensorDataController;

    public ManuallyTestViewController(SensorFactory sensorFactory, SensorDataController sensorDataController) {
        this.sensorFactory = sensorFactory;
        this.sensorDataController = sensorDataController;
    }

    public void createExampleData() {
        sensorFactory.setExampleData();
    }

    public List<Sensor<?>> getSensorData() {
        return sensorDataController.getSensorData();
    }
}
