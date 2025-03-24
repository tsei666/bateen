package de.breuer.bateen.controller;

import org.springframework.stereotype.Service;

@Service
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public boolean isAlive() {
        return connectionService.getAlive(); // Warten auf das Ergebnis (synchron)
    }
}

