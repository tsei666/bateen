package de.breuer.bateen.service.impl;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.model.ControlModeDto;
import de.breuer.bateen.model.ControlModeModel;
import de.breuer.bateen.model.DeviceStatusModel;
import de.breuer.bateen.service.DisplayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class DisplayServiceImpl implements DisplayService {

    private final WebClient.Builder webClientBuilder;
    private static final String DEVICE_STATUS_ENDPOINT = "/koda/sysctrl-if/device-status";
    private static final String CONTROL_MODE_ENDPOINT = "/koda/sysctrl-if/control-mode";

    public DisplayServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public ControlModeModel getControlMode() {
        String uri = ConfigController.getUrl().concat(CONTROL_MODE_ENDPOINT);
        ControlModeModel response = webClientBuilder.build().get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ControlModeModel.class)
                .block();
        log.info("Control mode response: {}", response);
        return response;
    }

    @Override
    public void putControlMode(ControlModeModel controlModeModel) {
        String uri = ConfigController.getUrl().concat(CONTROL_MODE_ENDPOINT);

        ControlModeDto dto = new ControlModeDto(controlModeModel.getMode());

        ResponseEntity<Void> response = webClientBuilder.build().put()
                .uri(uri)
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();

        if (response != null && response.getStatusCode() == HttpStatus.NO_CONTENT) {
            log.debug("CONTROL MODE UPDATE: Operation completed successfully, status 204_NO_CONTENT received");
        }
    }

    @Override
    public void putDeviceStatusModel(DeviceStatusModel deviceStatusModel) {
        String uri = ConfigController.getUrl().concat(DEVICE_STATUS_ENDPOINT);
        ResponseEntity<Void> response = webClientBuilder.build().put()
                .uri(uri)
                .bodyValue(deviceStatusModel)
                .retrieve()
                .toBodilessEntity()
                .block();
        if (response != null && response.getStatusCode() == HttpStatus.NO_CONTENT) {
            log.debug("DEVICE STATUS UPDATE: Operation completed successfully, status 204_NO_CONTENT received");
        }
    }

    @Override
    public DeviceStatusModel getDeviceStatus() {
        String uri = ConfigController.getUrl().concat(DEVICE_STATUS_ENDPOINT);
        DeviceStatusModel response = webClientBuilder.build().get()
                .uri(uri)
                .retrieve()
                .bodyToMono(DeviceStatusModel.class)
                .block();
        log.info("Device status response: {}", response);
        return response;
    }
}
