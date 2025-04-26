package de.breuer.bateen.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.controller.SensorDataController;
import de.breuer.bateen.sensor.Sensor;
import de.breuer.bateen.ui.components.SensorCardFactory;
import de.breuer.bateen.ui.layout.MainLayout;

import java.util.List;

@Route(value = "sensor-summary", layout = MainLayout.class)
@PageTitle("Sensor Summary")
public class SensorSummaryView extends VerticalLayout {

    public SensorSummaryView(SensorDataController sensorDataController) {
        setPadding(true);
        setSpacing(true);
        setSizeFull();

        H1 title = new H1("Review All Sensor Data");
        title.getStyle().set("text-align", "center");
        add(title);

        Button startButton = new Button("Start", event -> showPopup());
        startButton.getStyle()
                .set("margin", "0 auto")
                .set("display", "block")
                .set("background-color", "#4CAF50")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "18px")
                .set("border-radius", "6px");

        add(startButton);

        FlexLayout sensorContainer = new FlexLayout();
        sensorContainer.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        sensorContainer.setJustifyContentMode(JustifyContentMode.BETWEEN);
        sensorContainer.setAlignItems(Alignment.START);
        sensorContainer.setWidthFull();

        List<Sensor<?>> sensors = sensorDataController.getSensorData();
        sensors.forEach(sensor -> sensorContainer.add(SensorCardFactory.create(sensor)));

        add(sensorContainer);
    }

    private void showPopup() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Want visualisation?");

        Button yesButton = new Button("Yes", event -> {
            dialog.close();
            getUI().ifPresent(ui -> ui.navigate("animation"));
        });

        Button noButton = new Button("No", event -> {
            dialog.close();
            getUI().ifPresent(ui -> ui.navigate("send-data"));
        });

        yesButton.getStyle().set("background-color", "#4CAF50").set("color", "white");
        noButton.getStyle().set("background-color", "#f44336").set("color", "white");

        dialog.add(new Paragraph("Do you want to visualize the data before sending?"));
        dialog.getFooter().add(yesButton, noButton);

        dialog.open();
    }
}
