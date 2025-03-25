package de.breuer.bateen.controller;

import de.breuer.bateen.model.Officer;
import de.breuer.bateen.service.KCKService;
import de.breuer.bateen.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class LoginController {

    LoginService loginService;
    KCKService kckService;

    public LoginController(LoginService loginService, KCKService kckService) {
        this.loginService = loginService;
        this.kckService = kckService;
    }

    public Officer loginOfficer(String officerId, int pin) {
        kckService.postKckData();
        kckService.getKckData();
        Officer officer = loginService.performLogin(officerId, pin);
        ConfigController.addOfficer(officer);
        return officer;
    }

    public void removeOfficer(String officerId) {
        log.info("Remove Officer");
    }

}
