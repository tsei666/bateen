package de.breuer.bateen.ui.manually;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.ui.forms.SensorFormFactory;
import de.breuer.bateen.ui.layout.MainLayout;

@Route(value = "manual", layout = MainLayout.class)
@PageTitle("Manual Test")
public class ManuallyTestView extends VerticalLayout {

    private final VerticalLayout formContent = new VerticalLayout();

    public ManuallyTestView(ManuallyTestViewController controller) {
        setPadding(true);
        setSpacing(true);

        add(new H2("Manual Sensor Testing"));

        Button exampleDataButton = createExampleDataButton(controller);
        add(exampleDataButton);

        Button continueButton = createContinueButton();
        add(continueButton);

        formContent.setWidthFull();
        controller.getSensorData().forEach(sensor -> formContent.add(SensorFormFactory.createForm(sensor, controller)));
        add(formContent);
    }

    private Button createExampleDataButton(ManuallyTestViewController controller) {
        Button button = new Button("Use example data", event -> {
            controller.createExampleData();
            formContent.removeAll();
            controller.getSensorData().forEach(sensor -> formContent.add(SensorFormFactory.createForm(sensor, controller)));
        });

        button.getStyle()
                .set("margin-bottom", "20px")
                .set("font-size", "20px")
                .set("font-weight", "bold")
                .set("border-radius", "5px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        return button;
    }

    private Button createContinueButton() {
        Button button = new Button("Continue", event -> {
            // getUI().ifPresent(ui -> ui.navigate("next-step"));
        });

        button.getStyle()
                .set("position", "fixed")
                .set("top", "140px")
                .set("right", "50px")
                .set("font-size", "20px")
                .set("background-color", "#4CAF50")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("border-radius", "5px")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.2)");

        return button;
    }
}
