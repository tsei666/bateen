package de.breuer.bateen.ui.animation;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.ui.layout.MainLayout;

@Route(value = "animation", layout = MainLayout.class)
@PageTitle("Animation")
public class AnimationView extends VerticalLayout {

    private boolean animationRunning = true;
    private Button animationControlButton;
    private Div animatedDiv;

    public AnimationView() {
        setWidthFull();
        setHeightFull();
        setAlignItems(Alignment.CENTER);

        HorizontalLayout scene = new HorizontalLayout();
        scene.setWidth("100%");
        scene.setHeight("300px");
        scene.getStyle()
                .set("position", "relative")
                .set("background-color", "#7e7e7e")
                .set("border", "5px solid black")
                .set("border-radius", "10px")
                .set("overflow", "hidden");

        AnimationBuilder builder = AnimationModeResolver.resolve();
        builder.build(scene, this);

        if (builder instanceof StationaryAnimation stationary) {
            this.animatedDiv = stationary.getTruck();
        } else if (builder instanceof MobileAnimation mobile) {
            this.animatedDiv = mobile.getControlVehicle();
        } else {
            this.animatedDiv = null;
        }

        add(scene);
        setupControls();
    }

    private void setupControls() {
        animationControlButton = new Button("Pause Animation", event -> toggleAnimation());
        animationControlButton.getStyle()
                .set("margin-top", "20px")
                .set("background-color", "#dc3545")
                .set("color", "white")
                .set("font-weight", "bold");
        add(animationControlButton);
    }

    private void startAnimation() {
        if (animatedDiv != null) {
            animatedDiv.getStyle().set("animation-play-state", "running");
        }
    }

    private void pauseAnimation() {
        if (animatedDiv != null) {
            animatedDiv.getStyle().set("animation-play-state", "paused");
        }
    }

    private void toggleAnimation() {
        if (animatedDiv == null) return;

        if (animationRunning) {
            pauseAnimation();
            animationControlButton.setText("Start Animation");
            animationControlButton.getStyle().set("background-color", "#28a745");
        } else {
            startAnimation();
            animationControlButton.setText("Pause Animation");
            animationControlButton.getStyle().set("background-color", "#dc3545");
        }
        animationRunning = !animationRunning;
    }
}
