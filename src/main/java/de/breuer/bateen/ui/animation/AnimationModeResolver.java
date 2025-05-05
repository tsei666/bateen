package de.breuer.bateen.ui.animation;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.model.ControlModeModel;

import static org.reflections.Reflections.log;

public class AnimationModeResolver {

    public static AnimationBuilder resolve() {
        ControlModeModel controlMode = null;
        try {
            controlMode = getControlMode();
        } catch (Exception e) {
            log.error("Could not resolve control mode", e);
            return new DefaultAnimation();
        }

        if (controlMode == null) {
            return new DefaultAnimation();
        }

        return switch (controlMode) {
            case CONTROL_MODE_STATIONARY -> new StationaryAnimation();
            case CONTROL_MODE_MOBIL -> new MobileAnimation();
            default -> new DefaultAnimation();
        };
    }

    private static ControlModeModel getControlMode(){
        return ConfigController.getDisplay().getControlMode();
    }

    public static String getCurrentControlModeName(){
        return getControlMode().name();
    }
}
