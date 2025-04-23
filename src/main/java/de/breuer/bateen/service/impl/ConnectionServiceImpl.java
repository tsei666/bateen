package de.breuer.bateen.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.breuer.bateen.service.ConnectionService;
import de.breuer.bateen.controller.ConfigController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

    private final WebClient.Builder webClientBuilder;
    private static final int TIMEOUT_DURATION = 5;
    private static final String TEST_ALIVE_ENDPOINT = "/koda/test/alive";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ConnectionServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public boolean getAlive() throws JsonProcessingException {
        String uri = ConfigController.getUrl().concat(TEST_ALIVE_ENDPOINT);

        try {
            String response = webClientBuilder.build().get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(TIMEOUT_DURATION))
                    .block();

            JsonNode jsonNode = objectMapper.readTree(response);
            boolean isAlive = jsonNode.get("alive").asBoolean();

            log.info("Alive check successful: {}", isAlive);
            return isAlive;

        } catch (Exception e) {
            log.error("Failed to check alive status", e);
            return false;
        }
    }
}
