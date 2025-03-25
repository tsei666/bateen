package de.breuer.bateen.ui.login;

import lombok.Data;

@Data
public class LoginViewModel {
    private boolean officerOneLoggedIn = false;
    private boolean officerTwoLoggedIn = false;

    public boolean isOfficerLoggedIn(String id) {
        return switch (id) {
            case "kfz0001" -> officerOneLoggedIn;
            case "kfz0002" -> officerTwoLoggedIn;
            default -> false;
        };
    }

    public void setOfficerLoggedIn(String id, boolean value) {
        switch (id) {
            case "kfz0001" -> officerOneLoggedIn = value;
            case "kfz0002" -> officerTwoLoggedIn = value;
        }
    }
}
