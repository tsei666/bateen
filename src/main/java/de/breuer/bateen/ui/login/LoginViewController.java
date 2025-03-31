package de.breuer.bateen.ui.login;

import de.breuer.bateen.controller.LoginController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoginViewController {

    private final LoginController loginController;
    private final LoginViewModel model;

    public enum LoginResult {
        LOGIN_SUCCESS, LOGIN_FAILED, LOGOUT
    }

    public LoginResult toggleOfficerLogin(String officerId, int pin) {
        if (model.isOfficerLoggedIn(officerId)) {
            loginController.removeOfficer(officerId);
            model.setOfficerLoggedIn(officerId, false);
            return LoginResult.LOGOUT;
        }

        var officer = loginController.loginOfficer(officerId, pin);
        if (officer != null) {
            model.setOfficerLoggedIn(officerId, true);
            return LoginResult.LOGIN_SUCCESS;
        } else {
            return LoginResult.LOGIN_FAILED;
        }
    }
}
