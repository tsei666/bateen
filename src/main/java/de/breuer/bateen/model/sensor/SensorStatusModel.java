package de.breuer.bateen.model.sensor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SensorStatusModel {

    private boolean sensorsAreOn = false;

    private boolean anprRedactionModeIsOn = false;

    private boolean demoIsOn = false;

    private boolean hasError = false;

    private String errorMessage = "";
    private int updateStatus = 0;
    private String softwareVersion = "";

}