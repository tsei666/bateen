package de.breuer.bateen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.breuer.bateen.controller.ConfigController;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class ConnectionService {

    private final WebClient.Builder webClientBuilder;
    private static final int TIMEOUT_DURATION = 5;
    private static final String TEST_ALIVE_ENDPOINT = "/koda/test/alive";

    public ConnectionService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public boolean getAlive() throws JsonProcessingException {
        String uri = ConfigController.getUrl().concat(TEST_ALIVE_ENDPOINT);
        String response = webClientBuilder.build().get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(TIMEOUT_DURATION))
                .onErrorReturn("{\"alive\": false}")
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.get("alive").asBoolean();
    }
}
