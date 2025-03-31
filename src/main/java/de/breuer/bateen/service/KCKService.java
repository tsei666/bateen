package de.breuer.bateen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.breuer.bateen.controller.ConfigController;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

import static org.reflections.Reflections.log;

@Service
public class KCKService {

    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseJson = """
            [
                {
                    "inspectorId": "kfz0001",
                    "forename": "Horst",
                    "surname": "Müller",
                    "locked": false,
                    "remainingLoginAttempts": 3,
                    "fieldOffice": "Münster",
                    "controlDivision": "SKgD",
                    "controlUnit": "00"
                },
                {
                    "inspectorId": "kfz0002",
                    "forename": "John",
                    "surname": "Doe",
                    "locked": false,
                    "remainingLoginAttempts": 3,
                    "fieldOffice": "Münster",
                    "controlDivision": "SKD",
                    "controlUnit": "01"
                }
            ]
            """;

    public KCKService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public void postOfficerWithId(String inspectorId) {
        String officerJson = extractOfficerJsonById(inspectorId);
        if (officerJson != null) {
            if (getKckData().trim().equals("[]")) {
                postKckData(officerJson);
            } else {
                postAllOfficers();
            }
        } else {
            log.error(String.format("Officer not found with id: %s",inspectorId));
        }
    }

    public void postReminingOfficerWithId(String reminingOfficerId) {
        String officerJson = extractOfficerJsonById(reminingOfficerId);
        if (officerJson != null) {
            postKckData(officerJson);
        } else {
            log.error(String.format("Officer not found with id: %s",reminingOfficerId));
        }
    }

    public void postAllOfficers() {
        postKckData(baseJson);
    }

    public void postNoOfficers() {
        postKckData("[]");
    }

    public String getKckData() {
        String uri = ConfigController.getUrl() + "/koda/kck-if/kck-data";
        return webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private void postKckData(String json) {
        String uri = ConfigController.getUrl() + "/koda/kck-if/kck-data";
        webClientBuilder.build()
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @SneakyThrows
    private String extractOfficerJsonById(String inspectorId) {
        List<Map<String, Object>> allOfficers = objectMapper.readValue(baseJson, new TypeReference<>() {
        });
        return allOfficers.stream()
                .filter(entry -> inspectorId.equals(entry.get("inspectorId")))
                .findFirst()
                .map(single -> {
                    try {
                        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(List.of(single));
                    } catch (JsonProcessingException e) {
                        log.error(e.getMessage());
                    }
                    return "";
                })
                .orElse(null);
    }
}
