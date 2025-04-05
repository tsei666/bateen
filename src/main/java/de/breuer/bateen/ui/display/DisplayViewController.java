package de.breuer.bateen.ui.display;

import de.breuer.bateen.controller.DisplayController;
import de.breuer.bateen.model.ControlModeModel;
import de.breuer.bateen.model.DeviceStatusModel;
import de.breuer.bateen.model.Display;
import org.springframework.stereotype.Controller;

@Controller
public class DisplayViewController {

    private final DisplayController displayController;

    public DisplayViewController(DisplayController displayController) {
        this.displayController = displayController;
    }

    public Display getDisplay() {
        return displayController.getDisplay();
    }

    public void toggleControlMode(Display display) {
        if (display.getControlMode() == ControlModeModel.CONTROL_MODE_STATIONARY) {
            display.setControlMode(ControlModeModel.CONTROL_MODE_MOBIL.getMode());
        } else {
            display.setControlMode(ControlModeModel.CONTROL_MODE_STATIONARY.getMode());
        }
        displayController.updateControlMode(display.getControlMode());
    }

    public void toggleWifi(Display display) {
        DeviceStatusModel deviceStatus = display.getDeviceStatus();
        deviceStatus.setWifiApOn(!deviceStatus.isWifiApOn());
        display.setDeviceStatus(deviceStatus);
        displayController.updateDeviceStatus(display.getDeviceStatus());
    }

    public void toggleAkls(Display display) {
        DeviceStatusModel deviceStatus = display.getDeviceStatus();
        deviceStatus.setAklsOn(!deviceStatus.isAklsOn());
        display.setDeviceStatus(deviceStatus);
        displayController.updateDeviceStatus(display.getDeviceStatus());
    }

    public void toggleDsrc(Display display) {
        DeviceStatusModel deviceStatus = display.getDeviceStatus();
        deviceStatus.setDsrcOn(!deviceStatus.isDsrcOn());
        display.setDeviceStatus(deviceStatus);
        displayController.updateDeviceStatus(display.getDeviceStatus());
    }
}
