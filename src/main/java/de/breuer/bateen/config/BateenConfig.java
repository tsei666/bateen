package de.breuer.bateen.config;

import de.breuer.bateen.model.Display;
import de.breuer.bateen.model.Officer;
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
    private AklsSensor aklsSensor = new AklsSensor();
    private IrCameraSensor irCameraSensor = new IrCameraSensor();
    private VehicleSensor vehicleSensor = new VehicleSensor();
    private DsrcSensor dsrcSensor = new DsrcSensor();
}
