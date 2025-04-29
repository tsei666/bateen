package de.breuer.bateen.ui.animation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class StationaryAnimation implements AnimationBuilder {

    private Div truck;

    public Div getTruck() {
        return truck;
    }

    @Override
    public void build(HorizontalLayout scene, Component parentView) {
        getCentralStrips().forEach(scene::add);

        Div controlVehicle = new Div();
        controlVehicle.getStyle()
                .set("background-image", "url('/images/crafter.png')")
                .set("background-size", "contain")
                .set("background-repeat", "no-repeat")
                .set("background-position", "center")
                .set("transform", "rotate(-90deg)")
                .set("width", "290px")
                .set("height", "220px")
                .set("position", "absolute")
                .set("top", "110px")
                .set("left", "40%");
        scene.add(controlVehicle);

        truck = new Div();
        truck.getStyle()
                .set("background-image", "url('/images/Truck.png')")
                .set("background-size", "contain")
                .set("background-repeat", "no-repeat")
                .set("background-position", "center")
                .set("transform", "rotate(-90deg)")
                .set("width", "400px")
                .set("height", "300px")
                .set("position", "absolute")
                .set("top", "-90px")
                .set("left", "-150px");
        scene.add(truck);

        Element parentElement = parentView.getElement();
        String animationCss = "@keyframes driveLKW { 0% { left: -150px; } 100% { left: 100%; } }";
        parentElement.executeJs(
                "let style = document.createElement('style'); style.innerHTML = $0; document.head.appendChild(style);",
                animationCss
        );
        truck.getStyle().set("animation", "driveLKW 10s linear infinite");
        truck.getStyle().set("animation-play-state", "running");
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
