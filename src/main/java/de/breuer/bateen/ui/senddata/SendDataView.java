package de.breuer.bateen.ui.senddata;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.ui.layout.MainLayout;
import de.breuer.bateen.util.VaadinLogAppender;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "send-data", layout = MainLayout.class)
@PageTitle("SendData")
@PermitAll
@Slf4j
public class SendDataView extends VerticalLayout {

    private final TextArea logArea = new TextArea("Logs");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private boolean pollingActive = false;

    public SendDataView(SendDataViewController controller) {
        setWidthFull();
        setHeightFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        setSpacing(true);

        H2 title = new H2("Send Data");
        title.getStyle().set("text-align", "center");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setSpacing(true);

        clearLogArea();

        Button sendButtonWithDsrc = createSendButton("Send with DSRC Data", () -> {
            controller.sendSensorData();
            controller.sendDsrcSensorData();
        });

        Button sendButtonWithoutDSRC = createSendButton("Send without DSRC Data", controller::sendSensorData);

        Button sendButtonDSRC = createSendButton("Send only DSRC Data", controller::sendDsrcSensorData);

        Button sendIrData = createSendButton("Send IR Data", controller::sendIrSensorData);

        buttonLayout.add(sendButtonWithDsrc, sendButtonWithoutDSRC, sendButtonDSRC, sendIrData);

        logArea.setWidth("80%");
        logArea.setHeight("300px");
        logArea.setReadOnly(true);

        Button clearLogsButton = new Button("Clear Logs", event -> {
            VaadinLogAppender.clearLogs();
            clearLogArea();
        });
        setButtonStyle(clearLogsButton);

        add(title, buttonLayout, logArea, clearLogsButton);
    }

    private Button createSendButton(String text, Runnable action) {
        Button button = new Button(text, event -> {
            try {
                if (!pollingActive) {
                    startPolling();
                }
                action.run();
            } catch (Exception e) {
                log.error("Error in action '{}': {}", text, e.getMessage(), e);
            }
        });
        setButtonStyle(button);
        return button;
    }

    private void startPolling() {
        UI ui = UI.getCurrent();
        if (ui != null) {
            ui.setPollInterval(1000);
            ui.addPollListener(event -> updateLogArea());
            pollingActive = true;
        }
    }

    private void updateLogArea() {
        List<VaadinLogAppender.LogEntry> entries = VaadinLogAppender.getLogs();
        if (!entries.isEmpty()) {
            // Nur die neuen Logs holen
            StringBuilder current = new StringBuilder(logArea.getValue() != null ? logArea.getValue() : "");

            entries.forEach(entry -> {
                String logLine = "[" + LocalTime.ofInstant(entry.getInstant(), java.time.ZoneId.systemDefault()).format(timeFormatter) + "] " + entry.getMessage();
                current.append(logLine).append("\n");
            });

            logArea.setValue(current.toString());

            // Automatisch ganz nach unten scrollen
            logArea.getElement().executeJs(
                    "const textarea = this.shadowRoot.querySelector('textarea'); textarea.scrollTop = textarea.scrollHeight;"
            );

            // Nach dem Anzeigen: Logs in der Appender-Speicherung löschen, damit sie nicht wiederholt werden
            VaadinLogAppender.clearLogs();
        }
    }

    private void clearLogArea() {
        logArea.clear();
    }

    private void setButtonStyle(Button button) {
        button.getStyle()
                .set("font-size", "20px")
                .set("border-radius", "6px")
                .set("margin", "5px");
    }
}