package de.breuer.bateen.service.impl;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.model.SensorRecordState;
import de.breuer.bateen.model.akls.SensorConfigModel;
import de.breuer.bateen.model.akls.SensorDataModel;
import de.breuer.bateen.model.akls.SensorStatusModel;
import de.breuer.bateen.model.akls.SensorUpdateInfoModel;
import de.breuer.bateen.sensor.AklsSensor;
import de.breuer.bateen.sensor.DsrcSensor;
import de.breuer.bateen.sensor.IrCameraSensor;
import de.breuer.bateen.sensor.VehicleSensor;
import de.breuer.bateen.service.SensorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SensorServiceImpl implements SensorService {

    private final WebClient.Builder webClientBuilder;

    public SensorServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public SensorConfigModel getSensorConfig() {
        String uri = ConfigController.getUrl().concat("/koda/sensors-if/config");
        return webClientBuilder.build().get().uri(uri).retrieve().bodyToMono(SensorConfigModel.class).block();
    }

    @Override
    public boolean updateStatus(SensorStatusModel sensorStatus) {
        String uri = ConfigController.getUrl().concat("/koda/sensors-if/status");
        ResponseEntity<Void> response = webClientBuilder.build().post().uri(uri).bodyValue(sensorStatus).retrieve().toBodilessEntity().block();
        return response != null && response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public SensorUpdateInfoModel getSensorUpdateInfo() {
        String uri = ConfigController.getUrl().concat("/koda/sensors-if/update-info");
        return webClientBuilder.build().get().uri(uri).retrieve().bodyToMono(SensorUpdateInfoModel.class).block();
    }

    @Override
    public void postSensorData() {
        // Standardablauf: START -> END -> COMPLETED -> Abschluss (6)
        postSensorData(SensorRecordState.START);
        sleep(500); // Kurze Pause
        postSensorData(SensorRecordState.END);
        sleep(500); // Kurze Pause
        postSensorData(SensorRecordState.COMPLETED);
        sleep(500); // Abschlusszustand
        postSensorData(SensorRecordState.getById(6));
    }

    public void postSensorData(SensorRecordState state) {
        AklsSensor akls = ConfigController.getAklsSensor();
        VehicleSensor vehicle = ConfigController.getVehicleSensor();
        IrCameraSensor irCamera = ConfigController.getIrCameraSensor();
        DsrcSensor dsrcSensor = ConfigController.getDsrcSensor();
        String url = ConfigController.getUrl();

        if (url == null || url.isEmpty()) {
            log.error("Cannot send sensor data: URL is not configured");
            return;
        }

        String uri = url + "/koda/sensors-if/sensor-data";

        SensorDataModel dataModel = SensorDataModel.builder()
                .sensorRecordId(akls.getSensorRecordId())
                .sensorRecordState(state.getId())
                .recordTimeStamp(dsrcSensor.getRecordTimeStamp())
                .anprNumberPlate(akls.getAnprNumberPlate())
                .anprNumberPlateConfidence(akls.getAnprNumberPlateConfidence())
                .anprCountry(akls.getAnprCountry())
                .anprCountryConfidence(akls.getAnprCountryConfidence())
                .anprPlatePicture(akls.getAnprPlatePicture())
                .anprOverviewPicture(akls.getAnprOverviewPicture())
                .anprSideviewPicture(akls.getAnprSideviewPicture())
                .vehicleWeightClass(vehicle.getVehicleWeightClass())
                .vehicleType(vehicle.getVehicleType().getId())
                .vehicleHeight(vehicle.getVehicleHeight())
                .vehicleWidth(vehicle.getVehicleWidth())
                .vehicleLength(vehicle.getVehicleLength())
                .vehicleLengthConfidence(vehicle.getVehicleLengthConfidence())
                .vehicleAxleCount(vehicle.getVehicleAxleCount())
                .vehicleSpeed(vehicle.getVehicleSpeed())
                .details(vehicle.getDetails())
                .unNumber(vehicle.getUnNumber())
                .hasWasteSign(vehicle.isHasWasteSign())
                .isExtraWideVehicle(vehicle.isExtraWideVehicle())
                .sensorRecordIdRef(new ArrayList<>())
                .isComplete(false)
                .incompleteReason(List.of(3))
                .build();

        log.info("DATAMODEL: " + dataModel.toString() );

        try {
            ResponseEntity<Void> response = webClientBuilder.build()
                    .post()
                    .uri(uri)
                    .bodyValue(dataModel)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            if (response != null && response.getStatusCode() == HttpStatus.CREATED) {
                log.info("SensorDataModel (state {}) sent successfully", state);
            } else {
                log.warn("SensorDataModel send failed for state {}: {}", state, response != null ? response.getStatusCode() : "No response");
            }

        } catch (Exception e) {
            log.error("Failed to send SensorDataModel with state {}", state, e);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Sleep interrupted", e);
        }
    }


}
