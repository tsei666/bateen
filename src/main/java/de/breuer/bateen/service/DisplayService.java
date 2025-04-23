package de.breuer.bateen.service;

import de.breuer.bateen.model.ControlModeModel;
import de.breuer.bateen.model.DeviceStatusModel;

public interface DisplayService {
    ControlModeModel getControlMode();
    void putControlMode(ControlModeModel controlModeModel);
    DeviceStatusModel getDeviceStatus();
    void putDeviceStatusModel(DeviceStatusModel deviceStatusModel);
}
