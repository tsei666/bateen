package de.breuer.bateen.ui.ir;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.model.ir.IrConfigModel;
import de.breuer.bateen.model.ir.IrStatusModel;
import de.breuer.bateen.ui.layout.MainLayout;
import jakarta.annotation.security.PermitAll;

@Route(value = "ir-camera", layout = MainLayout.class)
@PageTitle("IR Camera View")
@PermitAll
public class IrCameraView extends VerticalLayout {

    private final IrCameraViewController controller;
    private IrStatusModel irStatusModel;
    private final VerticalLayout statusLayout = new VerticalLayout();

    public IrCameraView(IrCameraViewController controller) {
        this.controller = controller;

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        setSpacing(true);
        setSizeFull();

        if (controller.isVmConnected()) {
            irStatusModel = new IrStatusModel();
            showContent(controller.getIrConfig());
        } else {
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

    private void showContent(IrConfigModel irConfig) {
        irStatusModel.setDemoIsOn(irConfig.isDemoOn());
        irStatusModel.setSensorsAreOn(irConfig.isSensorsOn());
        irStatusModel.setHasError(false);
        irStatusModel.setErrorMessage("");

        H2 title = new H2("IR Camera Configuration");
        title.getStyle().set("text-align", "center");

        Button toggleSensorsOn = new Button("Toggle Sensors On", e -> {
            irStatusModel.setSensorsAreOn(!Boolean.TRUE.equals(irStatusModel.getSensorsAreOn()));
            updateStatusLayout();
        });

        Button toggleDemoOn = new Button("Toggle Demo On", e -> {
            irStatusModel.setDemoIsOn(!Boolean.TRUE.equals(irStatusModel.getDemoIsOn()));
            updateStatusLayout();
        });

        Button sendIrStatus = new Button("Send IR Status", e -> {
            controller.sendIrStatus(irStatusModel);
        });

        toggleSensorsOn.getStyle().set("margin", "10px");
        toggleDemoOn.getStyle().set("margin", "10px");
        sendIrStatus.getStyle()
                .set("background-color", "#4CAF50")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "16px")
                .set("border-radius", "6px")
                .set("margin", "10px 0");

        add(
                title,
                coloredSpan("Initial SensorsOn: ", irConfig.isSensorsOn()),
                coloredSpan("Initial DemoOn: ", irConfig.isDemoOn()),
                toggleSensorsOn,
                toggleDemoOn,
                statusLayout,
                sendIrStatus
        );

        updateStatusLayout(); // Direkt Status anzeigen
    }

    private void updateStatusLayout() {
        statusLayout.removeAll();

        Span currentStatus = new Span(
                "Current IR Status -> " +
                "SensorsOn: " + (Boolean.TRUE.equals(irStatusModel.getSensorsAreOn()) ? "ON" : "OFF") +
                ", DemoOn: " + (Boolean.TRUE.equals(irStatusModel.getDemoIsOn()) ? "ON" : "OFF") +
                ", HasError: " + (Boolean.TRUE.equals(irStatusModel.getHasError()) ? "YES" : "NO") +
                ", ErrorMessage: " + (irStatusModel.getErrorMessage() != null ? irStatusModel.getErrorMessage() : "None")
        );
        currentStatus.getStyle()
                .set("font-size", "1.1em")
                .set("margin", "20px 0")
                .set("text-align", "center");

        statusLayout.add(currentStatus);
    }

    private Span coloredSpan(String label, boolean isOn) {
        Span span = new Span(label + (isOn ? "ON" : "OFF"));
        span.getStyle()
                .set("color", isOn ? "green" : "red")
                .set("font-weight", "bold");
        return span;
    }
}
