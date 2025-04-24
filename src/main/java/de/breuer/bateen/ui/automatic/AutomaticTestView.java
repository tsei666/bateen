package de.breuer.bateen.ui.automatic;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.ui.components.SensorCardFactory;
import de.breuer.bateen.ui.layout.MainLayout;

@Route(value = "automatic", layout = MainLayout.class)
@PageTitle("Automatic Test Case")
public class AutomaticTestView extends VerticalLayout {

    public AutomaticTestView(AutomaticTestViewController controller) {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H1("Automatic Test Case"));

        // Test Cards
        FlexLayout cardsRow = new FlexLayout();
        cardsRow.setWidthFull();
        cardsRow.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        cardsRow.setJustifyContentMode(JustifyContentMode.BETWEEN);
        cardsRow.setAlignItems(Alignment.START);

        cardsRow.add(
                createTestCard("Test Case A", controller),
                createTestCard("Test Case B", controller),
                createTestCard("Test Case C", controller),
                createTestCard("Test Case D", controller)
        );

        add(cardsRow);

        // Sensor Cards
        FlexLayout sensorContainer = new FlexLayout();
        sensorContainer.setWidthFull();
        sensorContainer.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        sensorContainer.setJustifyContentMode(JustifyContentMode.BETWEEN);
        sensorContainer.setAlignItems(Alignment.START);

        controller.getSensorData().forEach(sensor ->
                sensorContainer.add(SensorCardFactory.create(sensor)));

        add(sensorContainer);
    }

    private VerticalLayout createTestCard(String name, AutomaticTestViewController controller) {
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

        H3 title = new H3(name);
        String caseId = name.substring(name.length() - 1); // A, B, C, D

        Button generateButton = new Button("Generate Test Case");
        generateButton.addClickListener(e -> {
            controller.generateTestCase(caseId);
            generateButton.setText("Generated!");
        });

        generateButton.getStyle()
                .set("background-color", "#007bff")
                .set("color", "#fff");

        card.add(title, generateButton);
        return card;
    }
}
