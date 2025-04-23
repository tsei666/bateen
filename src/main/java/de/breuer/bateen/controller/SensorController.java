package de.breuer.bateen.controller;

import de.breuer.bateen.model.akls.SensorConfigModel;
import de.breuer.bateen.model.akls.SensorStatusModel;
import de.breuer.bateen.model.akls.SensorUpdateInfoModel;
import de.breuer.bateen.service.SensorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class SensorController {

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    public SensorConfigModel getSensorConfig() {
        SensorConfigModel config = sensorService.getSensorConfig();
        log.info("Fetched SensorConfig: {}", config);
        return config;
    }

    public boolean updateSensorStatus(SensorStatusModel sensorStatus) {
        boolean result = sensorService.updateStatus(sensorStatus);
        log.info("Sensor status update: {}", result ? "Successful" : "Failed");
        return result;
    }

    public SensorUpdateInfoModel getSensorUpdateInfo() {
        SensorUpdateInfoModel updateInfo = sensorService.getSensorUpdateInfo();
        log.info("Fetched SensorUpdateInfo: {}", updateInfo);
        return updateInfo;
    }
}
