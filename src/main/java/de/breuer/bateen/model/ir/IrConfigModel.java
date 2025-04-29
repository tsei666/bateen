package de.breuer.bateen.model.ir;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class IrConfigModel {
    @Builder.Default
    private boolean sensorsOn = false;
    @Builder.Default
    private boolean demoOn = false;
    @Builder.Default
    private String caseId = UUID.randomUUID().toString();
    @Builder.Default
    private int sensorRecordState = 0;
}