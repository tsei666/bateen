package de.breuer.bateen.service;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.model.sensor.SensorConfigModel;
import de.breuer.bateen.model.sensor.SensorDataModel;
import de.breuer.bateen.model.sensor.SensorStatusModel;
import de.breuer.bateen.model.sensor.SensorUpdateInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class SensorService {

    private final WebClient.Builder webClientBuilder;
    private static final String SENSOR_CONFIG_ENDPOINT = "/koda/sensors-if/config";
    private static final String SENSOR_DATA_ENDPOINT = "/koda/sensors-if/sensor-data";
    private static final String SENSOR_STATUS_ENDPOINT = "/koda/sensors-if/status";
    private static final String SENSOR_UPDATE_INFO_ENDPOINT = "/koda/sensors-if/update-info";

    @Autowired
    public SensorService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    /**
     * GET /koda/sensors-if/config
     */
    public SensorConfigModel getSensorConfig() {
        String uri = ConfigController.getUrl().concat(SENSOR_CONFIG_ENDPOINT);
        SensorConfigModel response = webClientBuilder.build().get()
                .uri(uri)
                .retrieve()
                .bodyToMono(SensorConfigModel.class)
                .block();
        log.info("SensorConfig response: {}", response);
        return response;
    }

    /**
     * POST /koda/sensors-if/sensor-data
     */
    public boolean updateSensorData(SensorDataModel sensorData) {
        String uri = ConfigController.getUrl().concat(SENSOR_DATA_ENDPOINT);
        ResponseEntity<Void> response = webClientBuilder.build().post()
                .uri(uri)
                .bodyValue(sensorData)
                .retrieve()
                .toBodilessEntity()
                .block();
        
        if (response != null && response.getStatusCode() == HttpStatus.CREATED) {
            log.debug("SENSOR DATA UPDATE: Operation completed successfully, status 201_CREATED received");
            return true;
        }
        
        log.warn("SENSOR DATA UPDATE: Failed, status received: {}", response != null ? response.getStatusCode() : "No Response");
        return false;
    }

    /**
     * POST /koda/sensors-if/status
     */
    public boolean updateStatus(SensorStatusModel sensorStatus) {
        String uri = ConfigController.getUrl().concat(SENSOR_STATUS_ENDPOINT);
        ResponseEntity<Void> response = webClientBuilder.build().post()
                .uri(uri)
                .bodyValue(sensorStatus)
                .retrieve()
                .toBodilessEntity()
                .block();

        if (response != null && response.getStatusCode() == HttpStatus.CREATED) {
            log.debug("SENSOR STATUS UPDATE: Operation completed successfully, status 201_CREATED received");
            return true;
        }

        log.warn("SENSOR STATUS UPDATE: Failed, status received: {}", response != null ? response.getStatusCode() : "No Response");
        return false;
    }

    /**
     * GET /koda/sensors-if/update-info
     */
    public SensorUpdateInfoModel getSensorUpdateInfo() {
        String uri = ConfigController.getUrl().concat(SENSOR_UPDATE_INFO_ENDPOINT);
        SensorUpdateInfoModel response = webClientBuilder.build().get()
                .uri(uri)
                .retrieve()
                .bodyToMono(SensorUpdateInfoModel.class)
                .block();
        
        log.info("SensorUpdateInfo response: {}", response);
        return response;
    }
}
