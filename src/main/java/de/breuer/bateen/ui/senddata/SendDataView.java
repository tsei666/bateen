package de.breuer.bateen.ui.senddata;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.ui.layout.MainLayout;
import jakarta.annotation.security.PermitAll;

@Route(value = "send-data", layout = MainLayout.class)
@PageTitle("SendData")
@PermitAll
public class SendDataView extends VerticalLayout {

    private final TextArea logArea = new TextArea("Logs");


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

        Button sendButtonWithDsrc = new Button("Send with DSRC Data", event -> {
            logArea.clear();
            try {
                controller.sendSensorData();
                controller.sendDsrcSensorData();
                logArea.setValue("Data with DSRC sent successfully");
            } catch (Exception e) {
                logArea.setValue("Error sending data with DSRC: " + e.getMessage());
            }
        });

        Button sendButtonWithoutDSRC = new Button("Send without DSRC Data", event -> {
            try {
                controller.sendSensorData();
            } catch (Exception e) {
                logArea.setValue("Error sending data without DSRC: " + e.getMessage());
            }
        });

        Button sendButtonDSRC = new Button("Send only DSRC Data", event -> {
            try {
                controller.sendDsrcSensorData();
                logArea.setValue("DSRC data sent successfully");
            } catch (Exception e) {
                logArea.setValue("Error sending DSRC data: " + e.getMessage());
            }
        });


        setButtonStyle(sendButtonWithDsrc);
        setButtonStyle(sendButtonWithoutDSRC);
        setButtonStyle(sendButtonDSRC);
        buttonLayout.add(sendButtonWithDsrc, sendButtonWithoutDSRC, sendButtonDSRC);
        logArea.setWidth("80%");
        logArea.setHeight("300px");
        logArea.setReadOnly(true);

        add(title, buttonLayout, logArea);
    }

    private void setButtonStyle(Button button) {
        button.getStyle()
                .set("background-color", "#4CAF50")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "30px")
                .set("border-radius", "6px");
    }
}
