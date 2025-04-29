package de.breuer.bateen.ui.animation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class DefaultAnimation implements AnimationBuilder {
    @Override
    public void build(HorizontalLayout scene, Component parentView) {
        Div info = new Div();
        info.setText("Ungültiger Modus: Keine Animation verfügbar");
        info.getStyle()
                .set("color", "white")
                .set("position", "absolute")
                .set("top", "140px")
                .set("left", "40%")
                .set("font-size", "20px")
                .set("font-weight", "bold");
        scene.add(info);
    }
}
