package de.breuer.bateen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@Builder
public class DeviceConfigModel {
    private boolean wifiApOn;
    private boolean aklsOn;
    private boolean dsrcOn;
}