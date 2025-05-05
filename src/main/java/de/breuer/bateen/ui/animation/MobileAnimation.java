package de.breuer.bateen.ui.animation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@CssImport("./styles/animationStyles.css")
public class MobileAnimation implements AnimationBuilder {

    private Div controlVehicle;
    private Div truck;

    @Override
    public void build(HorizontalLayout scene, Component parentView) {
        getCentralStrips().forEach(scene::add);

        truck = new Div();
        truck.addClassName("static-truck");
        scene.add(truck);

        controlVehicle = new Div();
        controlVehicle.addClassName("moving-vehicle");
        scene.add(controlVehicle);

        Element parentElement = parentView.getElement();
        String animationCss = "@keyframes driveVan { 0% { left: -150px; } 100% { left: 100%; } }";
        parentElement.executeJs(
                "let style = document.createElement('style'); style.innerHTML = $0; document.head.appendChild(style);",
                animationCss
        );

        controlVehicle.getStyle().set("animation", "driveVan 10s linear infinite");
        controlVehicle.getStyle().set("animation-play-state", "paused");
    }

    private List<Div> getCentralStrips() {
        List<Div> strips = new ArrayList<>();
        int startLeft = 0;
        int gap = 250;
        int numberOfStrips = 8;

        for (int i = 0; i < numberOfStrips; i++) {
            Div strip = new Div();
            strip.getStyle()
                    .set("height", "10px")
                    .set("background-color", "white")
                    .set("position", "absolute")
                    .set("top", "145px")
                    .set("width", "150px")
                    .set("left", startLeft + (i * gap) + "px");
            strips.add(strip);
        }
        return strips;
    }
}
