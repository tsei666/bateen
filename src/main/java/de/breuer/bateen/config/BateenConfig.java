package de.breuer.bateen.config;

import de.breuer.bateen.model.Display;
import de.breuer.bateen.model.Officer;
import de.breuer.bateen.model.ir.IrConfigModel;
import de.breuer.bateen.sensor.AklsSensor;
import de.breuer.bateen.sensor.DsrcSensor;
import de.breuer.bateen.sensor.IrCameraSensor;
import de.breuer.bateen.sensor.VehicleSensor;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Data
public class BateenConfig {
    private String url;
    private String graphqlUrl;
    private List<Officer> officers;
    private Display display;
    private AklsSensor aklsSensor;
    private IrCameraSensor irCameraSensor;
    private VehicleSensor vehicleSensor;
    private DsrcSensor dsrcSensor;
    private IrConfigModel irConfigModel;
}
