package de.breuer.bateen.model.akls;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorConfigModel {
    private boolean sensorsOn;
    private String command;
    private boolean demoOn;
    private boolean doUpdate;
    private boolean anprRedactionModeOn;
}