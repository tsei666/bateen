package de.breuer.bateen.model.akls;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorDataModel {

    private String sensorRecordId;
    private Integer sensorRecordState;
    private boolean isComplete;
    private List<Integer> incompleteReason = new ArrayList<>();
    private String recordTimeStamp;
    private String anprNumberPlate;
    private int anprNumberPlateConfidence;
    private String anprCountry;
    private int anprCountryConfidence;
    private String anprPlatePicture;
    private String anprOverviewPicture;
    private String anprSideviewPicture;
    private int vehicleWeightClass;
    private int vehicleType; //VehicleType von Sharedlib
    private int vehicleHeight;
    private int vehicleWidth;
    private int vehicleLength;
    private int vehicleLengthConfidence;
    private int vehicleAxleCount;
    private int vehicleSpeed;
    private String details;
    private String unNumber;
    private Boolean hasWasteSign;
    private Boolean isExtraWideVehicle;
    private List<String> sensorRecordIdRef = new ArrayList<>();
}
