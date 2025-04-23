package de.breuer.bateen.sensor;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public abstract class AbstractSensor<T> implements Sensor<T> {

    @Pattern(regexp = "^.{36}$", message = "sensorRecordId must be 36 characters long")
    private String sensorRecordId;
}
