package de.breuer.bateen.controller;

import de.breuer.bateen.model.ir.IrConfigModel;
import de.breuer.bateen.model.ir.IrStatusModel;
import de.breuer.bateen.service.IrSensorService;
import org.springframework.stereotype.Controller;

@Controller
public class IrCameraController {

    IrSensorService irSensorService;

    public IrCameraController(IrSensorService irSensorService) {
        this.irSensorService = irSensorService;
    }

    public IrConfigModel getIrConfig() {
        IrConfigModel irConfigModel;
        try {
            irConfigModel = irSensorService.fetchIrConfig();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch IR config", e);
        }
        ConfigController.setIrConfigModel(irConfigModel);
        return irConfigModel;
    }

    public void updateIr(IrStatusModel irStatus) {
        try {
            irSensorService.postIrStatus(irStatus);
        } catch (Exception e) {
            throw new RuntimeException("Failed to post IR status", e);
        }
    }
}
