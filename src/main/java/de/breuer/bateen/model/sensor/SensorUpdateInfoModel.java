package de.breuer.bateen.model.sensor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorUpdateInfoModel {
    @Builder.Default
    private String newVersion = "";
    @Builder.Default
    private String link = "";
}