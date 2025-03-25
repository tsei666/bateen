package de.breuer.bateen.service;

import de.breuer.bateen.util.OfficerResponseWrapper;
import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.model.Officer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
public class LoginService {

    private final WebClient.Builder webClientBuilder;

    @Value("classpath:graphql/loginMutation.graphql")
    private Resource graphqlMutationResource;

    public LoginService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Officer performLogin(String officerId, int pin) {
        String graphqlQuery;
        try {
            graphqlQuery = loadGraphqlQuery();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }

        Map<String, Object> variables = Map.of(
                "officerId", officerId,
                "pin", pin
        );

        Map<String, Object> requestBody = Map.of(
                "query", graphqlQuery,
                "variables", variables
        );

        String uri = ConfigController.getGraphqlUrl();

        Officer officer = webClientBuilder.build()
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(OfficerResponseWrapper.class)
                .map(wrapper -> {
                    if (wrapper == null || wrapper.getData() == null || wrapper.getData().getOfficer() == null) {
                        throw new RuntimeException("No officer found");
                    }
                    return wrapper.getData().getOfficer();
                })
                .block();

        log.info("Authenticated user: {}", officer);
        return officer;
    }

    private String loadGraphqlQuery() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                graphqlMutationResource.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        }
    }
}
