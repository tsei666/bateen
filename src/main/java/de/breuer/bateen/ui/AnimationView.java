package de.breuer.bateen.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.ui.layout.MainLayout;

@Route(value = "animation", layout = MainLayout.class)
@PageTitle("Animation")
@StyleSheet("styles.css")
public class AnimationView extends VerticalLayout {

    public AnimationView() {
        setWidthFull();
        setHeightFull();
        setAlignItems(Alignment.CENTER);

        // Erstellen der Straße
        HorizontalLayout scene = new HorizontalLayout();
        scene.setWidth("100%");
        scene.setHeight("300px");
        scene.getStyle().set("position", "relative");
        scene.getStyle().set("background-color", "#7e7e7e");
        scene.getStyle().set("border", "5px solid black");
        scene.getStyle().set("border-radius", "10px");
        scene.getStyle().set("overflow", "hidden");

        // Kontrollfahrzeug erstellen
        Button kontrollfahrzeug = new Button();
        kontrollfahrzeug.getStyle().set("width", "60px");
        kontrollfahrzeug.getStyle().set("height", "40px");
        kontrollfahrzeug.getStyle().set("background-color", "#0f62fe");
        kontrollfahrzeug.getStyle().set("border-radius", "5px");
        kontrollfahrzeug.getStyle().set("border", "1px solid black");
        kontrollfahrzeug.getStyle().set("position", "absolute");
        kontrollfahrzeug.getStyle().set("top", "120px");
        kontrollfahrzeug.getStyle().set("left", "45%");

        // LKW erstellen
        Button lkw = new Button();
        lkw.getStyle().set("width", "120px");
        lkw.getStyle().set("height", "60px");
        lkw.getStyle().set("background-color", "#ff6f00");
        lkw.getStyle().set("border-radius", "5px");
        lkw.getStyle().set("border", "1px solid black");
        lkw.getStyle().set("position", "absolute");
        lkw.getStyle().set("top", "110px");

        // Keyframes CSS als String erstellen
        String keyframes = "@keyframes driveLKW { 0% { left: -150px; } 100% { left: 100%; } }";

        // Keyframes in den globalen Style einfügen
        getElement().executeJs("let style = document.createElement('style'); style.innerHTML = $0; document.head.appendChild(style);", keyframes);

        // LKW Animation hinzufügen
        lkw.getStyle().set("animation", "driveLKW 10s linear infinite");

        // Komponenten zur Szene hinzufügen
        scene.add(kontrollfahrzeug, lkw);
        add(scene);
    }
}
