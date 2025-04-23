package de.breuer.bateen.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ConnectionService {
    boolean getAlive() throws JsonProcessingException;
}
