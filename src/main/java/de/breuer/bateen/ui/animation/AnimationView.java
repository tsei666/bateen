package de.breuer.bateen.ui.animation;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import de.breuer.bateen.ui.display.DisplayView;
import de.breuer.bateen.ui.layout.MainLayout;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "animation", layout = MainLayout.class)
@PageTitle("Animation")
@PermitAll
public class AnimationView extends VerticalLayout {

    private boolean animationRunning = false;
    private Div animatedDiv;
    private Div staticDiv;

    private boolean wasAutomaticallyStopped = false;

    private Button controlButton;
    private Button resetButton;
    private int stopCount = 0;

    private final VerticalLayout logBox = new VerticalLayout();

    @Autowired
    private AnimationViewController animationViewController;

    public AnimationView() {
        setWidthFull();
        setHeightFull();
        setAlignItems(Alignment.CENTER);

        add(createInfoBox());

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
            this.staticDiv = stationary.getControlVehicle();
        } else if (builder instanceof MobileAnimation mobile) {
            this.animatedDiv = mobile.getControlVehicle();
            this.staticDiv = mobile.getTruck();
        }

        add(scene);
        setupControlButtons();
        setupLogBox();

        if (animatedDiv != null) {
            animatedDiv.getElement().executeJs(
                    """
                    this.style.left = '-150px';
                    this.style.animationPlayState = 'paused';
                    """
            );
        }
    }

    private HorizontalLayout createInfoBox() {
        HorizontalLayout infoBox = new HorizontalLayout();
        infoBox.setAlignItems(Alignment.CENTER);
        infoBox.getStyle()
                .set("background-color", "#f8f9fa")
                .set("padding", "10px")
                .set("border-radius", "5px")
                .set("margin-bottom", "20px");

        String controlMode = AnimationModeResolver.getCurrentControlModeName();
        Span modeInfo = new Span("Control Mode: " + controlMode);
        modeInfo.getStyle().set("margin-right", "10px");

        RouterLink changeVmLink = new RouterLink("7\"-Display", DisplayView.class);
        changeVmLink.getStyle()
                .set("color", "#007bff")
                .set("text-decoration", "none");

        Span infoText = new Span(" Switch Control Mode here:");

        infoBox.add(modeInfo, infoText, changeVmLink);
        return infoBox;
    }

    private void setupControlButtons() {
        controlButton = new Button("Start Animation", e -> controlButtonAction());
        resetButton = new Button("Reset", e -> resetAnimation());

        controlButton.getStyle()
                .set("background-color", "#007bff")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("min-width", "180px")
                .set("min-height", "40px");

        resetButton.getStyle()
                .set("background-color", "#dc3545")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("min-width", "180px")
                .set("min-height", "40px");

        HorizontalLayout buttonLayout = new HorizontalLayout(controlButton, resetButton);
        buttonLayout.setSpacing(true);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.getStyle().set("margin-top", "20px").set("gap", "15px");

        add(buttonLayout);
    }

    private void setupLogBox() {
        logBox.setWidth("80%");
        logBox.setMaxHeight("200px"); // Maximalhöhe definieren
        logBox.getStyle()
                .set("background-color", "#f1f1f1")
                .set("padding", "10px")
                .set("border-radius", "5px")
                .set("overflow-y", "auto")  // Vertikales Scrollen
                .set("white-space", "pre-wrap") // Zeilenumbruch für lange Texte
                .set("word-break", "break-word"); // Lange Worte umbrechen
        add(logBox);
    }

    private void updateLogBox() {
        logBox.removeAll();
        for (String message : animationViewController.getLogMessages()) {
            logBox.add(new Span(message));
        }
    }

    private void controlButtonAction() {
        if (!animationRunning && !wasAutomaticallyStopped && stopCount == 0) {
            startAnimation();
        } else if (!animationRunning && wasAutomaticallyStopped) {
            resumeAnimation();
        }
    }

    private void startAnimation() {
        if (animatedDiv != null) {
            controlButton.setEnabled(false);
            animatedDiv.getElement().executeJs(
                    """
                    this.style.left = '-150px';
                    this.style.animation = 'none';
                    this.offsetHeight;
                    if (this.classList.contains('moving-truck')) {
                        this.style.animation = 'driveLKW 10s linear forwards';
                    } else {
                        this.style.animation = 'driveVan 10s linear forwards';
                    }
                    this.style.animationPlayState = 'running';
                    """
            );
            stopCount = 0;
            animationRunning = true;
            wasAutomaticallyStopped = false;
            monitorStop0();
        }
    }

    private void resumeAnimation() {
        if (animatedDiv != null) {
            controlButton.setEnabled(false);
            animatedDiv.getElement().executeJs("if (this._monitoringFrame) { cancelAnimationFrame(this._monitoringFrame); this._monitoringFrame = null; }");

            animatedDiv.getStyle().set("animation-play-state", "running");
            animationRunning = true;
            wasAutomaticallyStopped = false;

            if (stopCount == 0) monitorStop0();
            else if (stopCount == 1) monitorStop1();
            else if (stopCount == 2) monitorStop2();
            else if (stopCount == 3) {
                stopCount = 4;
                animationViewController.sendCompleted();
                updateLogBox();
                controlButton.setEnabled(false);
            }
        }
    }

    private void resetAnimation() {
        if (animatedDiv != null) {
            animatedDiv.getElement().executeJs(
                    """
                    this.style.left = '-150px';
                    this.style.animation = 'none';
                    this.offsetHeight;
                    if (this.classList.contains('moving-truck')) {
                        this.style.animation = 'driveLKW 10s linear forwards';
                    } else {
                        this.style.animation = 'driveVan 10s linear forwards';
                    }
                    this.style.animationPlayState = 'paused';
                    """
            );
            stopCount = 0;
            animationRunning = false;
            wasAutomaticallyStopped = false;
            controlButton.setText("Start Animation");
            controlButton.setEnabled(true);
            logBox.removeAll();
        }
    }

    @ClientCallable
    public void handleAutoStop(int reachedStopCount) {
        pauseAnimation();
        this.animationRunning = false;
        this.wasAutomaticallyStopped = true;
        this.stopCount = reachedStopCount;

        // Sensor-Daten oder andere Aktionen ausführen
        animationViewController.sendSensorDataForStop(reachedStopCount);
        updateLogBox();

        if (reachedStopCount < 3) {
            controlButton.setText("Resume Animation");
            controlButton.setEnabled(true);
        } else if (reachedStopCount == 3) {
            controlButton.setText("Resume Final");
            controlButton.setEnabled(true);
        }
    }

    private void pauseAnimation() {
        if (animatedDiv != null) {
            animatedDiv.getStyle().set("animation-play-state", "paused");
            animationRunning = false;
        }
    }

    private void monitorStop0() {
        getElement().executeJs(
                """
                const moving = $0;
                const fixed = $1;
                const view = $2;

                if (moving._monitoringFrame) {
                    cancelAnimationFrame(moving._monitoringFrame);
                }

                function monitor() {
                    const movingRect = moving.getBoundingClientRect();
                    const fixedRect = fixed.getBoundingClientRect();

                    const behindDistance = fixedRect.left - (movingRect.left + movingRect.width);

                    if (behindDistance <= 10) {
                        moving.style.animationPlayState = 'paused';
                        view.$server.handleAutoStop(1);
                        return;
                    }

                    moving._monitoringFrame = requestAnimationFrame(monitor);
                }

                moving._monitoringFrame = requestAnimationFrame(monitor);
                """,
                animatedDiv.getElement(), staticDiv.getElement(), this.getElement()
        );
    }

    private void monitorStop1() {
        getElement().executeJs(
                """
                const moving = $0;
                const fixed = $1;
                const view = $2;

                if (moving._monitoringFrame) {
                    cancelAnimationFrame(moving._monitoringFrame);
                }

                function monitor() {
                    const movingRect = moving.getBoundingClientRect();
                    const fixedRect = fixed.getBoundingClientRect();

                    const behindDistance = fixedRect.left - movingRect.left;

                    if (behindDistance <= 0) {
                        moving.style.animationPlayState = 'paused';
                        view.$server.handleAutoStop(2);
                        return;
                    }

                    moving._monitoringFrame = requestAnimationFrame(monitor);
                }

                moving._monitoringFrame = requestAnimationFrame(monitor);
                """,
                animatedDiv.getElement(), staticDiv.getElement(), this.getElement()
        );
    }

    private void monitorStop2() {
        getElement().executeJs(
                """
                const moving = $0;
                const fixed = $1;
                const view = $2;

                if (moving._monitoringFrame) {
                    cancelAnimationFrame(moving._monitoringFrame);
                }

                function monitor() {
                    const movingRect = moving.getBoundingClientRect();
                    const fixedRect = fixed.getBoundingClientRect();

                    const aheadDistance = movingRect.left - (fixedRect.left + fixedRect.width);

                    if (aheadDistance >= 10) {
                        moving.style.animationPlayState = 'paused';
                        view.$server.handleAutoStop(3);
                        return;
                    }

                    moving._monitoringFrame = requestAnimationFrame(monitor);
                }

                moving._monitoringFrame = requestAnimationFrame(monitor);
                """,
                animatedDiv.getElement(), staticDiv.getElement(), this.getElement()
        );
    }
}
