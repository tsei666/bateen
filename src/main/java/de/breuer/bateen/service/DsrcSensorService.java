package de.breuer.bateen.service;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.sensor.DsrcSensor;
import de.breuer.bateen.sensor.dto.DsrcSensorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class DsrcSensorService {

    private final WebClient.Builder webClientBuilder;
    private static final String POST_ENDPOINT = "/koda/st-if/st-data";

    public void sendDsrcSensor() {
        DsrcSensor sensor = ConfigController.getDsrcSensor();
        DsrcSensorDto dto = mapToDto(sensor);
        String url = ConfigController.getUrl();
        if (url == null) {
            log.error("No VM configured. Cannot send DSRC sensor data.");
            throw new IllegalStateException("No VM configured");
        }

        String uri = ConfigController.getUrl() + "/koda/st-if/st-data";
        try {
            ResponseEntity<Void> response = webClientBuilder.build().post()
                    .uri(uri)
                    .bodyValue(dto)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            if (response != null && response.getStatusCode() == HttpStatus.CREATED) {
                log.info("DSRC sensor data sent successfully (201 CREATED)");
            } else {
                log.warn("DSRC sensor data send failed: {}", response != null ? response.getStatusCode() : "No response");
            }
        } catch (Exception e) {
            log.error("Exception occurred while sending DSRC sensor data", e);
        }
    }

    private DsrcSensorDto mapToDto(DsrcSensor sensor) {
        DsrcSensorDto dto = new DsrcSensorDto();
        dto.setRecordTimeStamp(sensor.getRecordTimeStamp());
        dto.setVehicleRegistrationPlate(sensor.getVehicleRegistrationPlate());
        dto.setSpeedingEvent(sensor.isSpeedingEvent());
        dto.setDrivingWithoutValidCard(sensor.isDrivingWithoutValidCard());
        dto.setDriverCard(sensor.isDriverCard());
        dto.setCardInsertion(sensor.isCardInsertion());
        dto.setMotionDataError(sensor.isMotionDataError());
        dto.setVehicleMotionConflict(sensor.isVehicleMotionConflict());
        dto.setSecondDriverCard(sensor.isSecondDriverCard());
        dto.setCurrentActivityDriving(sensor.isCurrentActivityDriving());
        dto.setLastSessionClosed(sensor.isLastSessionClosed());
        dto.setPowerSupplyInterruption(sensor.isPowerSupplyInterruption());

        dto.setSensorFault(sensor.getSensorFault());
        dto.setTimeAdjustment(sensor.getTimeAdjustment());
        dto.setLatestBreachAttempt(sensor.getLatestBreachAttempt());
        dto.setLastCalibrationData(sensor.getLastCalibrationData());
        dto.setPrevCalibrationData(sensor.getPrevCalibrationData());
        dto.setDateTachoConnected(sensor.getDateTachoConnected());
        dto.setCurrentSpeed(sensor.getCurrentSpeed());
        dto.setTimeStamp(sensor.getTimeStamp());
        dto.setLatestAuthenticatedPosition(sensor.getLatestAuthenticatedPosition());

        dto.setContinuousDrivingTime(sensor.getContinuousDrivingTime());
        dto.setDailyDrivingTimeShift(sensor.getDailyDrivingTimeShift());
        dto.setDailyDrivingTimeWeek(sensor.getDailyDrivingTimeWeek());
        dto.setWeeklyDrivingTime(sensor.getWeeklyDrivingTime());
        dto.setFortnightlyDrivingTime(sensor.getFortnightlyDrivingTime());

        return dto;
    }

}
