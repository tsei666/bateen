package de.breuer.bateen.ui.forms;

import com.vaadin.flow.component.Component;
import de.breuer.bateen.sensor.Sensor;
import de.breuer.bateen.ui.manually.ManuallyTestViewController;

public interface SensorFormRenderer {
    Component render(Sensor<?> sensor, ManuallyTestViewController controller);
}
