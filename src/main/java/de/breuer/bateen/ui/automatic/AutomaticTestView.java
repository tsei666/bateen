package de.breuer.bateen.ui.automatic;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.ui.components.SensorCardFactory;
import de.breuer.bateen.ui.layout.MainLayout;

import java.util.HashMap;
import java.util.Map;

@Route(value = "automatic", layout = MainLayout.class)
@PageTitle("Automatic Test Case")
public class AutomaticTestView extends VerticalLayout {

    private final Map<String, Button> buttonMap = new HashMap<>();
    private final FlexLayout sensorContainer = new FlexLayout();

    public AutomaticTestView(AutomaticTestViewController controller) {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H1("Automatic Test Case"));

        FlexLayout cardsRow = new FlexLayout();
        cardsRow.setWidthFull();
        cardsRow.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        cardsRow.setJustifyContentMode(JustifyContentMode.BETWEEN);
        cardsRow.setAlignItems(Alignment.START);

        cardsRow.add(
                createTestCard("Test Case All Green", "This test case uses example data for a good/ clean vehicle.", "Green", controller),
                createTestCard("Test Case B", "", "B", controller),
                createTestCard("Test Case Random", "This is a test with random values. The vehicle type is also random but without NONE and CAR.", "Random", controller),
                createTestCard("Test Case D", "", "D", controller)
        );

        add(cardsRow);

        sensorContainer.setWidthFull();
        sensorContainer.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        sensorContainer.setJustifyContentMode(JustifyContentMode.BETWEEN);
        sensorContainer.setAlignItems(Alignment.START);
        updateSensorCards(controller);
        add(sensorContainer);
    }

    private void updateSensorCards(AutomaticTestViewController controller) {
        sensorContainer.removeAll();
        controller.getSensorData().forEach(sensor ->
                sensorContainer.add(SensorCardFactory.create(sensor)));
    }

    private VerticalLayout createTestCard(String titleText, String description, String caseId, AutomaticTestViewController controller) {
        VerticalLayout card = new VerticalLayout();
        card.setWidth("250px");
        card.setSpacing(true);
        card.setPadding(true);

        card.getStyle()
                .set("border", "1px solid #ddd")
                .set("border-radius", "8px")
                .set("padding", "1em")
                .set("box-shadow", "0 2px 4px rgba(0,0,0,0.05)")
                .set("background-color", "#fafafa");

        H3 title = new H3(titleText);
        Span text = new Span(String.format("Description: %s", description));
        text.getStyle().set("font-style", "italic");
        Button actionButton = new Button("Generate Test Case");

        actionButton.getStyle()
                .set("background-color", "#007bff")
                .set("color", "#fff");

        actionButton.getElement().setProperty("state", "generate");

        actionButton.addClickListener(e -> {
            String state = actionButton.getElement().getProperty("state");

            if ("generate".equals(state)) {
                buttonMap.forEach((key, btn) -> {
                    btn.setText("Generate Test Case");
                    btn.getStyle().set("background-color", "#007bff");
                    btn.getElement().setProperty("state", "generate");
                });

                actionButton.setText("Continue");
                actionButton.getStyle().set("background-color", "#4CAF50");
                actionButton.getElement().setProperty("state", "continue");

                controller.generateTestCase(caseId);
                updateSensorCards(controller);

            } else if ("continue".equals(state)) {
                getUI().ifPresent(ui -> ui.navigate("sensor-summary"));
            }
        });

        buttonMap.put(caseId, actionButton);
        card.add(title, text, actionButton);
        return card;
    }
}