package de.breuer.bateen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStatusModel {
    private boolean wifiApOn;
    private boolean aklsOn;
    private boolean dsrcOn;
    private boolean aklsUpdateInProgress;
}