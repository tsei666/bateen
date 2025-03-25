package de.breuer.bateen.model;

import lombok.Data;

@Data
public class KCKData {
    private String inspectorId;
    private String forename;
    private String surname;
    private boolean locked;
    private int remainingLoginAttempts;
    private String fieldOffice;
    private String controlDivision;
    private String controlUnit;
}
