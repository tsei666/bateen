package de.breuer.bateen.ui.components;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.breuer.bateen.sensor.IrCameraSensor;

import java.util.List;

public class IrCameraSensorCard extends VerticalLayout {

    public IrCameraSensorCard(IrCameraSensor ir) {
        setWidth("350px");
        setPadding(false);
        setSpacing(false);
        setStyle();

        add(
            new Paragraph("IR Images: " + previewList(ir.getIrImages())),
            new Paragraph("IR Max Temperature: " + ir.getIrMaxTempValues()),
            new Paragraph("IR Mean Temperature: " + ir.getIrMeanTempValues())
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

    private String previewList(List<String> list) {
        if (list == null || list.isEmpty()) return "[]";
        return "[" + list.size() + " entries]";
    }
}
