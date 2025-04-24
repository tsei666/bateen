package de.breuer.bateen.ui.automatic;

import de.breuer.bateen.controller.SensorDataController;
import de.breuer.bateen.factory.SensorFactory;
import de.breuer.bateen.sensor.Sensor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutomaticTestViewController {

    private final SensorFactory sensorFactory;
    private final SensorDataController sensorDataController;

    public AutomaticTestViewController(SensorFactory sensorFactory, SensorDataController sensorDataController) {
        this.sensorFactory = sensorFactory;
        this.sensorDataController = sensorDataController;
    }

    public void generateTestCase(String caseId) {
        sensorFactory.generateTestCase(caseId);
    }

    public List<Sensor<?>> getSensorData() {
        return sensorDataController.getSensorData();
    }
}

