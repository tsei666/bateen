package de.breuer.bateen.ui.ir;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.controller.IrCameraController;
import de.breuer.bateen.model.ir.IrConfigModel;
import de.breuer.bateen.model.ir.IrStatusModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IrCameraViewController {

    private IrCameraController irCameraController;

    public IrCameraViewController(IrCameraController irCameraController) {
        this.irCameraController = irCameraController;
    }

    public boolean isVmConnected() {
        try {
            return !ConfigController.getUrl().isBlank() || !ConfigController.getUrl().isEmpty();
        } catch (Exception e) {
            log.error("Failed to check VM connection", e);
        }
        return false;
    }

    public IrConfigModel getIrConfig() {
        return irCameraController.getIrConfig();
    }

    public boolean sendIrStatus(IrStatusModel irStatus) {
        try {
            irCameraController.updateIr(irStatus);
            return true;
        } catch (Exception e) {
            log.error("Failed to send IR status", e);
        }
        return false;
    }
}
