package de.breuer.bateen.ui.ir;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.controller.IrCameraController;
import de.breuer.bateen.model.ir.IrConfigModel;
import de.breuer.bateen.model.ir.IrStatusModel;
import org.springframework.stereotype.Component;

@Component
public class IrCameraViewController {

    private IrCameraController irCameraController;

    public IrCameraViewController(IrCameraController irCameraController) {
        this.irCameraController = irCameraController;
    }

    public boolean isVmConnected() {
        return !ConfigController.getUrl().isBlank() || !ConfigController.getUrl().isEmpty();
    }

    public IrConfigModel getIrConfig() {
        return irCameraController.getIrConfig();
    }

    public void sendIrStatus(IrStatusModel irStatus) {
        irCameraController.updateIr(irStatus);
    }
}
