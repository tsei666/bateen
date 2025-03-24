package de.breuer.bateen.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ConnectionService {

    private final WebClient.Builder webClientBuilder;

    public ConnectionService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public boolean getAlive() {
        WebClient.Builder client = WebClient.builder();

        String sout = client.build().get()
                .uri("http://localhost:50000/koda/test/alive")
                .retrieve()
                .bodyToMono(String.class).block();
        System.out.println(sout);
        return false; // Synchronous call
    }
}
