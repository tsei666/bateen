package de.breuer.bateen.ui.animation;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.model.ControlModeModel;

public class AnimationModeResolver {

    public static AnimationBuilder resolve() {
        ControlModeModel controlMode = ConfigController.getDisplay().getControlMode();

        if (controlMode == null) {
            return new DefaultAnimation();
        }

        return switch (controlMode) {
            case CONTROL_MODE_STATIONARY -> new StationaryAnimation();
            case CONTROL_MODE_MOBIL -> new MobileAnimation();
            default -> new DefaultAnimation();
        };
    }
}
