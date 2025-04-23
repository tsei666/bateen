package de.breuer.bateen.util;

import de.breuer.bateen.model.akls.SensorDataModel;
import de.breuer.bateen.sensor.AklsSensor;
import de.breuer.bateen.sensor.DsrcSensor;
import de.breuer.bateen.sensor.IrCameraSensor;
import de.breuer.bateen.sensor.VehicleSensor;

import java.util.ArrayList;
import java.util.List;

public class SensorDataWrapper {

    private AklsSensor aklsSensor;
    private VehicleSensor vehicleSensor;
    private IrCameraSensor irCameraSensor;
    private DsrcSensor dsrcSensor;

    public SensorDataWrapper akls(AklsSensor sensor) {
        this.aklsSensor = sensor;
        return this;
    }

    public SensorDataWrapper vehicle(VehicleSensor sensor) {
        this.vehicleSensor = sensor;
        return this;
    }

    public SensorDataWrapper irCamera(IrCameraSensor sensor) {
        this.irCameraSensor = sensor;
        return this;
    }

    public SensorDataWrapper dsrc(DsrcSensor sensor) {
        this.dsrcSensor = sensor;
        return this;
    }

    public SensorDataModel toModel() {
        SensorDataModel model = new SensorDataModel();

        if (dsrcSensor != null) {
            model.setSensorRecordId(dsrcSensor.getSensorRecordId());
            model.setRecordTimeStamp(dsrcSensor.getRecordTimeStamp());
            List<String> idRefs = new ArrayList<>();
            idRefs.add(dsrcSensor.getSensorRecordId());
            model.setSensorRecordIdRef(idRefs);
        }

        if (aklsSensor != null) {
            model.setAnprNumberPlate(aklsSensor.getAnprNumberPlate());
            model.setAnprNumberPlateConfidence(aklsSensor.getAnprNumberPlateConfidence());
            model.setAnprCountry(aklsSensor.getAnprCountry());
            model.setAnprCountryConfidence(aklsSensor.getAnprCountryConfidence());
            model.setAnprPlatePicture(aklsSensor.getAnprPlatePicture());
            model.setAnprOverviewPicture(aklsSensor.getAnprOverviewPicture());
            model.setAnprSideviewPicture(aklsSensor.getAnprSideviewPicture());
        }

        if (vehicleSensor != null) {
            model.setVehicleWeightClass(vehicleSensor.getVehicleWeightClass());
            model.setVehicleType(vehicleSensor.getVehicleType());
            model.setVehicleHeight(vehicleSensor.getVehicleHeight());
            model.setVehicleWidth(vehicleSensor.getVehicleWidth());
            model.setVehicleLength(vehicleSensor.getVehicleLength());
            model.setVehicleLengthConfidence(vehicleSensor.getVehicleLengthConfidence());
            model.setVehicleAxleCount(vehicleSensor.getVehicleAxleCount());
            model.setVehicleSpeed(vehicleSensor.getVehicleSpeed());
            model.setDetails(vehicleSensor.getDetails());
            model.setUnNumber(vehicleSensor.getUnNumber());
            model.setHasWasteSign(vehicleSensor.isHasWasteSign());
            model.setIsExtraWideVehicle(vehicleSensor.isExtraWideVehicle());
        }

        // Optional: Vollständigkeitsprüfung
        model.setComplete(true);
        model.setIncompleteReason(new ArrayList<>());

        return model;
    }
}
