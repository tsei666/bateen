package de.breuer.bateen.service.impl;

import de.breuer.bateen.model.akls.SensorConfigModel;
import de.breuer.bateen.model.akls.SensorStatusModel;
import de.breuer.bateen.model.akls.SensorUpdateInfoModel;
import de.breuer.bateen.service.SensorService;
import de.breuer.bateen.controller.ConfigController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
}
