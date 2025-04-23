package de.breuer.bateen.sensor;

import lombok.Data;

@Data
public class AklsSensor implements Sensor<AklsSensor> {
    private String sensorRecordId;

    private String anprNumberPlate;
    private int anprNumberPlateConfidence;
    private String anprCountry;
    private int anprCountryConfidence;
    private String anprPlatePicture;
    private String anprOverviewPicture;
    private String anprSideviewPicture;

    @Override
    public AklsSensor getData() {
        return this;
    }

    @Override
    public void setData(AklsSensor data) {
        this.anprNumberPlate = data.anprNumberPlate;
        this.anprNumberPlateConfidence = data.anprNumberPlateConfidence;
        this.anprCountry = data.anprCountry;
        this.anprCountryConfidence = data.anprCountryConfidence;
        this.anprPlatePicture = data.anprPlatePicture;
        this.anprOverviewPicture = data.anprOverviewPicture;
        this.anprSideviewPicture = data.anprSideviewPicture;
    }

    @Override
    public String getTimestamp() {
        return null;
    }
}
