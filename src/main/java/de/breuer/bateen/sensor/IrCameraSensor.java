package de.breuer.bateen.sensor;

import lombok.Data;

import java.util.List;

@Data
public class IrCameraSensor extends AbstractSensor<IrCameraSensor> {
    private List<String> irImages;
    private List<Float> irMaxTempValues;
    private List<Float> irMeanTempValues;

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
