package de.breuer.bateen.service;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.model.ir.IrConfigModel;
import de.breuer.bateen.model.ir.IrDataModel;
import de.breuer.bateen.model.ir.IrStatusModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class IrSensorService {

    private final WebClient.Builder webClientBuilder;

    public void postIrData(String caseId, IrDataModel irData) {
        String url = ConfigController.getUrl();
        if (url == null || url.isEmpty()) {
            log.error("Cannot send IR sensor data: URL is not configured");
            return;
        }

        String endpoint = url + "/koda/ir-if/sensor-data";

        try {
            ResponseEntity<Void> response = webClientBuilder.build()
                    .post()
                    .uri(endpoint)
                    .bodyValue(irData)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            if (response != null && response.getStatusCode() == HttpStatus.CREATED) {
                log.info("IR sensor data sent successfully (201 CREATED)");
            } else {
                log.warn("Failed to send IR sensor data: {}", response != null ? response.getStatusCode() : "No response");
            }

        } catch (Exception e) {
            log.error("Exception while sending IR sensor data", e);
        }
    }

    public IrConfigModel fetchIrConfig() {
        String url = ConfigController.getUrl();
        if (url == null || url.isEmpty()) {
            log.error("Cannot fetch IR config: URL is not configured");
            return null;
        }

        String endpoint = url + "/koda/ir-if/config";

        try {
            IrConfigModel config = webClientBuilder.build()
                    .get()
                    .uri(endpoint)
                    .retrieve()
                    .bodyToMono(IrConfigModel.class)
                    .block();

            log.info("Fetched IR config successfully");
            return config;

        } catch (Exception e) {
            log.error("Failed to fetch IR config", e);
            return null;
        }
    }

    public boolean postIrStatus(IrStatusModel status) {
        String url = ConfigController.getUrl();
        if (url == null || url.isEmpty()) {
            log.error("Cannot post IR status: URL is not configured");
            return false;
        }

        String endpoint = url + "/koda/ir-if/status";

        try {
            ResponseEntity<Void> response = webClientBuilder.build()
                    .post()
                    .uri(endpoint)
                    .bodyValue(status)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            if (response != null && response.getStatusCode() == HttpStatus.CREATED) {
                log.info("IR status posted successfully (201 CREATED)");
                return true;
            } else {
                log.warn("Failed to post IR status: {}", response != null ? response.getStatusCode() : "No response");
                return false;
            }

        } catch (Exception e) {
            log.error("Exception while posting IR status", e);
            return false;
        }
    }
}
