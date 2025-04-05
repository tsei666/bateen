package de.breuer.bateen.ui.display;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.model.Display;
import de.breuer.bateen.ui.layout.MainLayout;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "display", layout = MainLayout.class)
@PageTitle("Display")
@PermitAll
public class DisplayView extends VerticalLayout {

    private final DisplayViewController displayViewController;
    private final Display display;

    private final Span controlModeSpan;
    private final Span wifiSpan;
    private final Span aklsSpan;
    private final Span dsrcSpan;

    @Autowired
    public DisplayView(DisplayViewController displayViewController) {
        this.displayViewController = displayViewController;
        this.display = displayViewController.getDisplay();

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);

        controlModeSpan = new Span();
        wifiSpan = new Span();
        aklsSpan = new Span();
        dsrcSpan = new Span();

        add(controlModeSpan, wifiSpan, aklsSpan, dsrcSpan);

        updateDisplay();
        createButtons();
    }

    private void updateDisplay() {
        controlModeSpan.setText("Control Mode: " + display.getControlMode());
        wifiSpan.setText("WiFi AP: " + (display.getDeviceStatus().isWifiApOn() ? "ON" : "OFF"));
        aklsSpan.setText("AKLS: " + (display.getDeviceStatus().isAklsOn() ? "ON" : "OFF"));
        dsrcSpan.setText("DSRC: " + (display.getDeviceStatus().isDsrcOn() ? "ON" : "OFF"));
    }

    private void createButtons() {
        Button controlModeButton = new Button("Toggle Control Mode", e -> {
            displayViewController.toggleControlMode(display);
            updateDisplay();
        });

        Button wifiButton = new Button("Toggle WiFi", e -> {
            displayViewController.toggleWifi(display);
            updateDisplay();
        });

        Button aklsButton = new Button("Toggle AKLS", e -> {
            displayViewController.toggleAkls(display);
            updateDisplay();
        });

        Button dsrcButton = new Button("Toggle DSRC", e -> {
            displayViewController.toggleDsrc(display);
            updateDisplay();
        });

        add(controlModeButton, wifiButton, aklsButton, dsrcButton);
    }
}
