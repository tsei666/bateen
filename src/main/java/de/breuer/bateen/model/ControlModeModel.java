package de.breuer.bateen.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ControlModeModel {
    CONTROL_MODE_STATIONARY(1),
    CONTROL_MODE_MOBIL(2),
    CONTROL_MODE_INVALID(0);

    private final int mode;

    ControlModeModel(int mode) {
        this.mode = mode;
    }

    @JsonValue
    public int getMode() {
        return mode;
    }

    @JsonCreator
    public static ControlModeModel fromValue(@JsonProperty("mode") int value) {
        for (ControlModeModel mode : values()) {
            if (mode.mode == value) {
                return mode;
            }
        }
        return CONTROL_MODE_INVALID;
    }
}
