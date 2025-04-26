package de.breuer.bateen.model.ir;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IrImageModel {
    @Builder.Default
    private String type = "";
    @Builder.Default
    private String description = "";
    @Builder.Default
    private int timestamp = 0;
    @Builder.Default
    @ToString.Exclude
    private String labeledData = "";
    @ToString.Exclude
    @Builder.Default
    private String rawData = "";
    @Builder.Default
    private boolean hasDamage = false;
    @Builder.Default
    private String caseId = UUID.randomUUID().toString();
}