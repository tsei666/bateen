package de.breuer.bateen.model.ir;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class IrDataModel {
    private List<IrImageModel> irImages = new ArrayList<>();
    private List<Integer> irMaxTempValues = new ArrayList<>();
    private List<Integer> irMeanTempValues = new ArrayList<>();
}