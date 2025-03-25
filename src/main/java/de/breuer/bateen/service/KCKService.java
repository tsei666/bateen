package de.breuer.bateen.service;

import de.breuer.bateen.controller.ConfigController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KCKService {
    WebClient.Builder webClientBuilder;

    public KCKService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public void postKckData() {
        String uri = ConfigController.getUrl() + "/koda/kck-if/kck-data";
        String json = """
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

        webClientBuilder.build()
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json);
    }

    public boolean getKckData() {
        String uri = ConfigController.getUrl() + "/koda/kck-if/kck-data";
        String kckDataList = webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return !kckDataList.isEmpty();
    }
}
