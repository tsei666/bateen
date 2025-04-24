package de.breuer.bateen.ui.components;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.breuer.bateen.sensor.AklsSensor;

public class AklsSensorCard extends VerticalLayout {

    public AklsSensorCard(AklsSensor akls) {
        setWidth("350px");
        setPadding(false);
        setSpacing(false);
        setStyle();

        add(
                new Paragraph("License Plate: " + akls.getAnprNumberPlate()),
                new Paragraph("License Plate Confidence: " + akls.getAnprNumberPlateConfidence()),
                new Paragraph("Country: " + akls.getAnprCountry()),
                new Paragraph("Country Confidence: " + akls.getAnprCountryConfidence()),
                new Paragraph("Plate Picture: " + previewBase64(akls.getAnprPlatePicture())),
                new Paragraph("Overview Picture: " + previewBase64(akls.getAnprOverviewPicture())),
                new Paragraph("Sideview Picture: " + previewBase64(akls.getAnprSideviewPicture()))
        );
    }

    private void setStyle() {
        getStyle()
                .set("border", "1px solid #ddd")
                .set("border-radius", "8px")
                .set("padding", "1em")
                .set("box-shadow", "0 2px 4px rgba(0,0,0,0.05)")
                .set("background-color", "#fafafa");
    }

    private String previewBase64(String base64) {
        return base64 != null && base64.length() > 30 ? base64.substring(0, 30) + "..." : base64;
    }
}
