package de.breuer.bateen.ui.start;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.controller.ConnectionController;
import de.breuer.bateen.ui.layout.StartLayout;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Route(value = "", layout = StartLayout.class)
@PageTitle("BaTeEn")
@CssImport("./styles/styles.css")
public class StartView extends VerticalLayout {

    private final StartViewModel model = new StartViewModel();
    private final StartViewController controller;
    private Button testConnectionButton;
    private Icon spinnerIcon;
    private Span loadingText;

    public StartView(ConnectionController connectionController) {
        this.controller = new StartViewController(connectionController, model);

        setSizeFull();
        setSpacing(true);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setPadding(true);

        this.testConnectionButton = createTestConnectionButton();

        add(createContent());
        add(new Span());
        add(testConnectionButton);
        add(new Span());
    }

    private Component createContent() {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setAlignItems(Alignment.CENTER);

        H2 header = new H2("Select the test object");
        HorizontalLayout buttonLayout = new HorizontalLayout();

        buttonLayout.add(createTestObjectButton("Local", "localhost:50000"));
        buttonLayout.add(createTestObjectButton("Remote", "192.168.1.100:50000"));
        buttonLayout.add(createOtherButton());

        contentLayout.add(header, buttonLayout);
        return contentLayout;
    }

    private Component createTestObjectButton(String title, String url) {
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.setSpacing(false);
        buttonLayout.setPadding(false);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Span titleLabel = new Span(title);
        titleLabel.getStyle().set("font-weight", "bold");

        Span subtitleLabel = new Span(url);
        subtitleLabel.getStyle().set("font-size", "var(--lumo-font-size-s)");
        subtitleLabel.getStyle().set("color", "var(--lumo-secondary-text-color)");

        buttonLayout.add(titleLabel, subtitleLabel);

        Button button = new Button(buttonLayout);
        button.setId(title);
        button.setWidth("200px");
        button.setHeight("80px");

        button.addClickListener(event -> {
            controller.handleButtonSelection(button, url);
            updateTestButtonState();
            Notification.show("Ausgewählt: " + title + " (" + url + ")");
        });

        return button;
    }

    private Component createOtherButton() {
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.setSpacing(false);
        buttonLayout.setPadding(false);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Span titleLabel = new Span("Other");
        titleLabel.getStyle().set("font-weight", "bold");

        TextField urlField = new TextField();
        urlField.setPlaceholder("Specify URL");
        urlField.setWidthFull();
        urlField.getStyle().set("border", "none");
        urlField.getStyle().set("background-color", "transparent");
        urlField.getStyle().set("text-align", "center");
        urlField.getStyle().set("font-size", "var(--lumo-font-size-s)");
        urlField.getStyle().set("color", "var(--lumo-secondary-text-color)");
        urlField.getStyle().set("padding", "0");

        buttonLayout.add(titleLabel, urlField);

        Button button = new Button(buttonLayout);
        button.setId("Other");
        button.setWidth("200px");
        button.setHeight("80px");

        button.addClickListener(event -> {
            controller.handleButtonSelection(button, urlField.getValue());
            updateTestButtonState();
            Notification.show("Ausgewählt: Other (" + (urlField.getValue().isEmpty() ? "Keine URL eingegeben" : urlField.getValue()) + ")");
        });

        return button;
    }

    private Button createTestConnectionButton() {
        Button button = new Button("Test Connection");
        button.setId("TestConnection");
        button.setWidth("500px");
        button.setHeight("80px");

        spinnerIcon = VaadinIcon.SPINNER.create();
        spinnerIcon.addClassName("spinner");
        spinnerIcon.setVisible(false);

        loadingText = new Span("Testing Connection");
        loadingText.setVisible(false);

        HorizontalLayout buttonLayout = new HorizontalLayout(button, loadingText);
        buttonLayout.setAlignItems(Alignment.CENTER);

        button.setEnabled(false);

        button.addClickListener(event -> {
            if (controller.isContinueEnabled()) {
                getUI().ifPresent(ui -> ui.navigate("login"));
                return;
            }

            if (model.getUrl() == null || model.getUrl().isEmpty()) {
                Notification.show("Select a test object or enter a URL", 3000, Notification.Position.MIDDLE);
                return;
            }

            button.setEnabled(false);
            spinnerIcon.setVisible(true);
            loadingText.setVisible(true);

            boolean success = controller.testConnection();

            getUI().ifPresent(ui -> ui.access(() -> {
                spinnerIcon.setVisible(false);
                loadingText.setVisible(false);

                if (success) {
                    controller.enableContinue();
                    button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                    button.setText("Connected successfully! Click here to continue");
                } else {
                    button.addThemeVariants(ButtonVariant.LUMO_ERROR);
                    button.setText("Unable to connect");
                }

                button.setEnabled(true);
            }));
        });

        return button;
    }

    private void updateTestButtonState() {
        testConnectionButton.removeThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_ERROR);
        testConnectionButton.setText("Test Connection");
        testConnectionButton.setEnabled(true);
    }
}
