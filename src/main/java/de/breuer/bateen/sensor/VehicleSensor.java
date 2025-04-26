package de.breuer.bateen.sensor;

import de.breuer.bateen.model.VehicleType;
import lombok.Data;

@Data
public class VehicleSensor extends AbstractSensor<VehicleSensor>{
    private int vehicleWeightClass;
    private VehicleType vehicleType;
    private int vehicleHeight;
    private int vehicleWidth;
    private int vehicleLength;
    private int vehicleLengthConfidence;
    private int vehicleAxleCount;
    private int vehicleSpeed;
    private String details;
    private String unNumber;
    private boolean hasWasteSign;
    private boolean isExtraWideVehicle;

    @Override
    public VehicleSensor getData() {
        return this;
    }

    @Override
    public void setData(VehicleSensor data) {
        this.vehicleWeightClass = data.vehicleWeightClass;
        this.vehicleType = data.vehicleType;
        this.vehicleHeight = data.vehicleHeight;
        this.vehicleWidth = data.vehicleWidth;
        this.vehicleLength = data.vehicleLength;
        this.vehicleLengthConfidence = data.vehicleLengthConfidence;
        this.vehicleAxleCount = data.vehicleAxleCount;
        this.vehicleSpeed = data.vehicleSpeed;
        this.details = data.details;
        this.unNumber = data.unNumber;
        this.hasWasteSign = data.hasWasteSign;
        this.isExtraWideVehicle = data.isExtraWideVehicle;
    }
}
