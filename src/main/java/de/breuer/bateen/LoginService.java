package de.breuer.bateen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class LoginService {

    private final WebClient.Builder webClientBuilder;
    private static String authToken;

    private String officerId;

    private int pin;

    @Value("classpath:graphql/loginMutation.graphql")
    private Resource graphqlMutationResource;

    public LoginService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

//    public Mono<String> performLogin() {
//        String graphqlQuery;
//        try {
//            graphqlQuery = loadGraphqlQuery();
//        } catch (IOException e) {
//            return Mono.just("Fehler: GraphQL-Query konnte nicht geladen werden!");
//        }
//
//        Map<String, Object> variables = Map.of(
//                "officerId", officerId,
//                "pin", pin
//        );
//
//        Map<String, Object> requestBody = Map.of(
//                "query", graphqlQuery,
//                "variables", variables
//        );
//
//        return webClient.post()
//                .uri("/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(Map.class)
//                .map(response -> {
//                    Map<String, Object> data = (Map<String, Object>) response.get("data");
//                    if (data != null && data.get("login") instanceof Map) {
//                        Map<String, Object> loginData = (Map<String, Object>) data.get("login");
//                        authToken = (String) loginData.get("token");
//                        return "Login erfolgreich! Token: " + authToken;
//                    }
//                    return "Login fehlgeschlagen!";
//                });
//    }

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

    public static String getAuthToken() {
        return authToken;
    }
}
