package de.breuer.bateen.model;

import de.breuer.bateen.sensor.AklsSensor;
import de.breuer.bateen.sensor.IrCameraSensor;
import de.breuer.bateen.sensor.VehicleSensor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SensorDataModel {
    private UUID sensorRecordId;
    private String sensorRecordState;
    private boolean isComplete;
    private List<Integer> incompleteReason;
    private String recordTimeStamp;

    private AklsSensor aklsSensor;
    private VehicleSensor vehicleSensor;
    private IrCameraSensor irCameraSensor;

    private List<UUID> sensorRecordIdRef;

    // Getter, Setter, Konstruktoren hier oder über Lombok generieren
}
