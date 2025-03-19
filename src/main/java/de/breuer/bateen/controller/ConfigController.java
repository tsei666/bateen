package de.breuer.bateen.controller;

import de.breuer.bateen.BateenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigController {

    static BateenConfig bateenConfig;

    @Autowired
    public ConfigController(BateenConfig bateenConfig) {
        ConfigController.bateenConfig = bateenConfig;
    }

    public static String getUrl(){
        return bateenConfig != null ? bateenConfig.getUrl() : "";
    }
}
