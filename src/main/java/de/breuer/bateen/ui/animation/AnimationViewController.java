package de.breuer.bateen.ui.animation;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.model.SensorRecordState;
import de.breuer.bateen.model.akls.SensorDataModel;
import de.breuer.bateen.model.ir.IrDataModel;
import de.breuer.bateen.sensor.DsrcSensor;
import de.breuer.bateen.sensor.IrCameraSensor;
import de.breuer.bateen.service.DsrcSensorService;
import de.breuer.bateen.service.IrSensorService;
import de.breuer.bateen.service.SensorService;
import de.breuer.bateen.service.impl.SensorServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AnimationViewController {

    private final SensorService sensorService;
    private final DsrcSensorService dsrcSensorService;
    private final IrSensorService irSensorService;

    @Getter
    private final List<String> logMessages = new ArrayList<>();

    public void sendSensorDataForStop(int stopNumber) {

        if (stopNumber == 1) {
            SensorDataModel dataModel = getNewSensorDataModel();
            sendSensorData(dataModel, SensorRecordState.START, stopNumber);

        } else if (stopNumber == 2) {
            sendDsrcSensorData();
            sendIrSensorData();

        } else if (stopNumber == 3) {
            SensorDataModel dataModel = getNewSensorDataModel();
            sendSensorData(dataModel, SensorRecordState.END, stopNumber);

        } else {
            logMessages.add("ℹ️ No SensorRecordState sent for stop " + stopNumber + ".");
            log.info("No SensorRecordState sent for stop {}", stopNumber);
        }
    }

    public void sendCompleted() {
        SensorDataModel dataModel = getNewSensorDataModel();
        sendSensorData(dataModel, SensorRecordState.COMPLETED, -1);
    }

    private SensorDataModel getNewSensorDataModel() {
        if (!(sensorService instanceof SensorServiceImpl serviceImpl)) {
            throw new IllegalStateException("SensorService is not an instance of SensorServiceImpl");
        }
        return serviceImpl.setSensorDataModel();
    }

    private void sendSensorData(SensorDataModel dataModel, SensorRecordState state, int stopNumber) {
        try {
            logMessages.add("Sending SensorData with state '" + state + "': " + dataModel);
            log.info("Sending SensorData with state '{}': {}", state, dataModel);

            sensorService.postSensorData(dataModel, state);

            String message = "✅ SensorData with state '" + state + "' sent successfully" +
                             (stopNumber > 0 ? " (Stop " + stopNumber + ")" : "");
            logMessages.add(message);
            log.info(message);

        } catch (Exception e) {
            String message = "❌ Failed to send SensorData with state '" + state + "': " + e.getMessage();
            logMessages.add(message);
            log.error(message, e);
        }
    }

    private void sendDsrcSensorData() {
        DsrcSensor sensor = ConfigController.getDsrcSensor();

        try {
            logMessages.add("Sending DSRC data with model: " + sensor);
            log.info("Sending DSRC data with model: {}", sensor);

            dsrcSensorService.sendDsrcSensor();

            String message = "✅ DSRC data sent successfully.";
            logMessages.add(message);
            log.info(message);

        } catch (Exception e) {
            String message = "❌ Failed to send DSRC data: " + e.getMessage();
            logMessages.add(message);
            log.error(message, e);
        }
    }

    private void sendIrSensorData() {
        IrCameraSensor irSensor = ConfigController.getIrCameraSensor();
        IrDataModel irData = new IrDataModel();
        irData.setIrImages(irSensor.getIrImages());
        irData.setIrMaxTempValues(irSensor.getIrMaxTempValues());
        irData.setIrMeanTempValues(irSensor.getIrMeanTempValues());

        String caseId = ConfigController.getAklsSensor().getSensorRecordId();

        try {
            logMessages.add("Sending IR data with model: " + irData);
            log.info("Sending IR data with model: {}", irData);

            irSensorService.postIrData(caseId, irData);

            String message = "✅ IR data sent successfully.";
            logMessages.add(message);
            log.info(message);

        } catch (Exception e) {
            String message = "❌ Failed to send IR data: " + e.getMessage();
            logMessages.add(message);
            log.error(message, e);
        }
    }
}
