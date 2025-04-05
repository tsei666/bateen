package de.breuer.bateen.model;

import lombok.Data;

@Data
public class Display {
    private ControlModeModel controlMode;
    private DeviceStatusModel deviceStatus;

    public void setControlMode(int controlMode) {
        this.controlMode = ControlModeModel.fromValue(controlMode);
    }

    public int getControlModeValue() {
        return this.controlMode.getMode();
    }
}
