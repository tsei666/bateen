package de.breuer.bateen.service;

import de.breuer.bateen.model.akls.SensorConfigModel;
import de.breuer.bateen.model.akls.SensorStatusModel;
import de.breuer.bateen.model.akls.SensorUpdateInfoModel;

public interface SensorService {
    SensorConfigModel getSensorConfig();
    boolean updateStatus(SensorStatusModel sensorStatus);
    SensorUpdateInfoModel getSensorUpdateInfo();
    void postSensorData();
}