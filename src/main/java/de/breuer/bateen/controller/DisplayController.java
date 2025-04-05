package de.breuer.bateen.controller;

import de.breuer.bateen.model.ControlModeModel;
import de.breuer.bateen.model.DeviceStatusModel;
import de.breuer.bateen.model.Display;
import de.breuer.bateen.service.DisplayService;
import org.springframework.stereotype.Controller;

@Controller
public class DisplayController {

    DisplayService displayService;

    public DisplayController(DisplayService displayService) {
        this.displayService = displayService;
    }

    public Display getDisplay() {
        Display display = ConfigController.bateenConfig.getDisplay();
        if (display == null) {
            display = new Display();
            display.setControlMode(getControlMode().getMode());
            display.setDeviceStatus(getDeviceStatus());
            updateDisplay(display);
            return display;
        } else if (!areDisplaysEqual(display)) {
            display.setControlMode(getControlMode().getMode());
            display.setDeviceStatus(getDeviceStatus());
            updateDisplay(display);
            return display;
        }
        return display;
    }

    private boolean areDisplaysEqual(Display display) {
        return display.getControlMode() == getControlMode() && display.getDeviceStatus() == getDeviceStatus();
    }

    public void updateDisplay(Display display) {
        ConfigController.bateenConfig.setDisplay(display);
    }

    public ControlModeModel getControlMode() {
        return displayService.getControlMode();
    }

    public void updateControlMode(ControlModeModel controlMode) {
        displayService.putControlMode(controlMode);
        Display display = getDisplay();
        display.setControlMode(controlMode.getMode());
        updateDisplay(display);
    }

    public DeviceStatusModel getDeviceStatus() {
        return displayService.getDeviceStatus();
    }

    public void updateDeviceStatus(DeviceStatusModel deviceStatus) {
        displayService.putDeviceStatusModel(deviceStatus);
        Display display = getDisplay();
        display.setDeviceStatus(deviceStatus);
        updateDisplay(display);
    }
}
