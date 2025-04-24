package de.breuer.bateen.ui.display;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

    @Autowired
    public DisplayView(DisplayViewController displayViewController) {
        this.displayViewController = displayViewController;

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);
        setSizeFull();

        try {
            Display display = displayViewController.getDisplay();
            if (display == null) {
                showNoVMConnectedMessage();
            } else {
                showDisplayContent(display);
            }
        } catch (IllegalStateException | NullPointerException ex) {
            showNoVMConnectedMessage();
        }
    }

    private void showNoVMConnectedMessage() {
        Span message = new Span("There is no VM connected, go to ");
        Anchor changeVmLink = new Anchor("/change-vm", "Change-VM");
        message.getStyle().set("font-size", "1.1em");

        Div messageContainer = new Div();
        messageContainer.add(message, changeVmLink);
        messageContainer.getStyle().set("text-align", "center");

        add(messageContainer);
    }

    private void showDisplayContent(Display display) {
        add(
                new Span("Control Mode: " + display.getControlMode()),
                coloredSpan("WiFi AP: ", display.getDeviceStatus().isWifiApOn()),
                coloredSpan("AKLS: ", display.getDeviceStatus().isAklsOn()),
                coloredSpan("DSRC: ", display.getDeviceStatus().isDsrcOn())
        );

        add(createButtons(display));
    }

    private Span coloredSpan(String label, boolean isOn) {
        Span span = new Span(label + (isOn ? "ON" : "OFF"));
        span.getStyle().set("color", isOn ? "green" : "red");
        span.getStyle().set("font-weight", "bold");
        return span;
    }

    private VerticalLayout createButtons(Display display) {
        Button controlModeButton = new Button("Toggle Control Mode", e -> {
            displayViewController.toggleControlMode(display);
            reloadPage();
        });

        Button wifiButton = new Button("Toggle WiFi", e -> {
            displayViewController.toggleWifi(display);
            reloadPage();
        });

        Button aklsButton = new Button("Toggle AKLS", e -> {
            displayViewController.toggleAkls(display);
            reloadPage();
        });

        Button dsrcButton = new Button("Toggle DSRC", e -> {
            displayViewController.toggleDsrc(display);
            reloadPage();
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(controlModeButton, wifiButton, aklsButton, dsrcButton);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setSpacing(true);

        VerticalLayout wrapper = new VerticalLayout(buttonLayout);
        wrapper.setAlignItems(Alignment.CENTER);
        return wrapper;
    }

    private void reloadPage() {
        getUI().ifPresent(ui -> ui.getPage().reload());
    }
}
