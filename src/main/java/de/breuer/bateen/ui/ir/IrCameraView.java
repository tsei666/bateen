package de.breuer.bateen.ui.ir;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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

import java.util.Arrays;
import java.util.List;

@Route(value = "ir-camera", layout = MainLayout.class)
@PageTitle("IR Camera View")
@PermitAll
public class IrCameraView extends VerticalLayout {

    private final IrCameraViewController controller;
    private IrStatusModel irStatusModel;
    private final VerticalLayout statusLayout = new VerticalLayout();
    private final Span feedbackMessage = new Span();

    private static final List<String> ERROR_MESSAGES = Arrays.asList(
            "on", "off", "error", "connected", "disconnected", "none", "update", "warning"
    );

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
        irStatusModel.setErrorMessage("none");

        H2 title = new H2("IR Camera Status");
        title.getStyle().set("text-align", "center");

        // Toggle-Buttons
        Button toggleSensorsOn = new Button("Toggle Sensors", e -> {
            irStatusModel.setSensorsAreOn(!Boolean.TRUE.equals(irStatusModel.getSensorsAreOn()));
            updateStatusLayout();
        });

        Button toggleDemoOn = new Button("Toggle Demo", e -> {
            irStatusModel.setDemoIsOn(!Boolean.TRUE.equals(irStatusModel.getDemoIsOn()));
            updateStatusLayout();
        });

        ComboBox<String> errorMessageSelect = new ComboBox<>("Select Error Message");
        errorMessageSelect.setItems(ERROR_MESSAGES);
        errorMessageSelect.setValue(irStatusModel.getErrorMessage());
        errorMessageSelect.addValueChangeListener(event -> {
            String value = event.getValue();
            irStatusModel.setErrorMessage(value);
            irStatusModel.setHasError(!"none".equals(value));
            updateStatusLayout();
        });

        // Senden-Button
        Button sendIrStatus = new Button("Send IR Status", e -> {
            boolean success = controller.sendIrStatus(irStatusModel);
            showFeedback(success);
        });

        toggleSensorsOn.getStyle().set("margin", "10px");
        toggleDemoOn.getStyle().set("margin", "10px");
        errorMessageSelect.getStyle().set("margin", "10px");
        sendIrStatus.getStyle()
                .set("background-color", "#4CAF50")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "16px")
                .set("border-radius", "6px")
                .set("margin", "10px 0");

        feedbackMessage.getStyle()
                .set("font-size", "1.1em")
                .set("margin-top", "10px");

        add(
                title,
                toggleSensorsOn,
                toggleDemoOn,
                errorMessageSelect,
                statusLayout,
                sendIrStatus,
                feedbackMessage
        );

        updateStatusLayout();
    }

    private void updateStatusLayout() {
        statusLayout.removeAll();
        statusLayout.setSpacing(true);
        statusLayout.setPadding(true);
        statusLayout.setAlignItems(Alignment.CENTER);

        Span sensorsStatus = new Span("Sensors: " + (Boolean.TRUE.equals(irStatusModel.getSensorsAreOn()) ? "ON" : "OFF"));
        sensorsStatus.getStyle().set("color", Boolean.TRUE.equals(irStatusModel.getSensorsAreOn()) ? "green" : "red");

        Span demoStatus = new Span("Demo Mode: " + (Boolean.TRUE.equals(irStatusModel.getDemoIsOn()) ? "ON" : "OFF"));
        demoStatus.getStyle().set("color", Boolean.TRUE.equals(irStatusModel.getDemoIsOn()) ? "green" : "red");

        Span errorStatus = new Span("Has Error: " + (Boolean.TRUE.equals(irStatusModel.getHasError()) ? "YES" : "NO"));
        errorStatus.getStyle().set("color", Boolean.TRUE.equals(irStatusModel.getHasError()) ? "red" : "green");

        Span errorMessageStatus = new Span("Error Message: " + (irStatusModel.getErrorMessage() != null ? irStatusModel.getErrorMessage() : "None"));
        errorMessageStatus.getStyle().set("font-weight", "bold");

        statusLayout.add(sensorsStatus, demoStatus, errorStatus, errorMessageStatus);
    }

    private void showFeedback(boolean success) {
        if (success) {
            feedbackMessage.setText("Status erfolgreich gesendet.");
            feedbackMessage.getStyle().set("color", "green");
        } else {
            feedbackMessage.setText("Fehler beim Senden des Status.");
            feedbackMessage.getStyle().set("color", "red");
        }
    }
}
