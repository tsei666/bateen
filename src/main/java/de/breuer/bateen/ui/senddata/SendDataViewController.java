package de.breuer.bateen.ui.senddata;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.model.ir.IrConfigModel;
import de.breuer.bateen.model.ir.IrDataModel;
import de.breuer.bateen.sensor.AklsSensor;
import de.breuer.bateen.sensor.DsrcSensor;
import de.breuer.bateen.sensor.IrCameraSensor;
import de.breuer.bateen.sensor.VehicleSensor;
import de.breuer.bateen.service.DsrcSensorService;
import de.breuer.bateen.service.IrSensorService;
import de.breuer.bateen.service.SensorService;
import org.springframework.stereotype.Component;

@Component
public class SendDataViewController {

    private final DsrcSensorService dsrcSensorService;
    private final IrSensorService irSensorService;
    private SensorService sensorService;

    public SendDataViewController(DsrcSensorService dsrcSensorService, IrSensorService irSensorService, SensorService sensorService) {
        this.dsrcSensorService = dsrcSensorService;
        this.irSensorService = irSensorService;
        this.sensorService = sensorService;
    }

    public void sendSensorData() {
        DsrcSensor sensor = ConfigController.getDsrcSensor();
        AklsSensor aklsSensor = ConfigController.getAklsSensor();
        VehicleSensor vehicleSensor = ConfigController.getVehicleSensor();
        if (sensor != null && aklsSensor != null && vehicleSensor != null ) {
            sensorService.postSensorData();
        } else {
            throw new IllegalStateException("No Sensor available");
        }
    }

    public void sendDsrcSensorData() {
        DsrcSensor sensor = ConfigController.getDsrcSensor();
        if (sensor != null) {
            dsrcSensorService.sendDsrcSensor();
        } else {
            throw new IllegalStateException("No Sensor available");
        }
    }

    public void sendIrSensorData() {
        IrConfigModel irConfigModel = irSensorService.fetchIrConfig();
        IrCameraSensor sensor = ConfigController.getIrCameraSensor();
        IrDataModel irDataModel = new IrDataModel();
        irDataModel.setIrImages(sensor.getIrImages());
        irDataModel.setIrMaxTempValues(sensor.getIrMaxTempValues());
        irDataModel.setIrMeanTempValues(sensor.getIrMeanTempValues());
        try {
            irSensorService.postIrData(irConfigModel.getCaseId(), irDataModel);
        } catch (Exception e) {
            throw new IllegalStateException("Could not send IR data", e);
        }
    }
}
