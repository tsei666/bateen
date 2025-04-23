package de.breuer.bateen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.breuer.bateen.service.ConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ConnectionController {

    private final ConnectionService connectionService;

    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public boolean isAlive() {
        try {
            boolean isAlive = connectionService.getAlive();
            log.info("Connection status: {}", isAlive ? "Alive" : "Dead");
            return isAlive;
        } catch (JsonProcessingException e) {
            log.error("Error checking connection status: {}", e.getMessage());
            return false;
        }
    }
}
