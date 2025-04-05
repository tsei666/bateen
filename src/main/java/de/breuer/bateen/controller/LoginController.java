package de.breuer.bateen.controller;

import de.breuer.bateen.model.Officer;
import de.breuer.bateen.service.KCKService;
import de.breuer.bateen.service.LoginService;
import de.breuer.bateen.service.OfficerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class LoginController {

    OfficerService officerService;
    LoginService loginService;
    KCKService kckService;

    @Autowired
    public LoginController(OfficerService officerService, LoginService loginService, KCKService kckService) {
        this.officerService = officerService;
        this.loginService = loginService;
        this.kckService = kckService;
    }

    public Officer loginOfficer(String officerId, int pin) {
        kckService.postOfficerWithId(officerId);
        Officer officer = loginService.performLogin(officerId, pin);
        officerService.addOfficer(officer);
        return officer;
    }

    public void removeOfficer(String officerId) {
        Officer remainingOfficer = officerService.removeOfficerAndGetRemainOfficerOrNull(officerId);
        if (remainingOfficer != null) {
            kckService.postReminingOfficerWithId(remainingOfficer.getOfficerId());
        } else {
            kckService.postNoOfficers();
        }
    }

}
