package de.breuer.bateen.model.ir;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IrStatusModel {

    private Boolean sensorsAreOn;
    private Boolean demoIsOn;
    private Boolean hasError;
    private String errorMessage;
}