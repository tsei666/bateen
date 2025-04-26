package de.breuer.bateen.sensor;

import de.breuer.bateen.model.ir.IrImageModel;
import lombok.Data;

import java.util.List;

@Data
public class IrCameraSensor extends AbstractSensor<IrCameraSensor> {
    private List<IrImageModel> irImages;
    private List<Integer> irMaxTempValues;
    private List<Integer> irMeanTempValues;

    @Override
    public IrCameraSensor getData() {
        return this;
    }

    @Override
    public void setData(IrCameraSensor data) {
        this.irImages = data.irImages;
        this.irMaxTempValues = data.irMaxTempValues;
        this.irMeanTempValues = data.irMeanTempValues;
    }
}
