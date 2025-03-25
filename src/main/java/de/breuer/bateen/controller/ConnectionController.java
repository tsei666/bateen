package de.breuer.bateen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.breuer.bateen.service.ConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public boolean isAlive() {
        try {
            return connectionService.getAlive();
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return false;
    }
}

