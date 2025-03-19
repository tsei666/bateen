package de.breuer.bateen.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import de.breuer.bateen.view.layout.StartLayout;

import java.util.concurrent.CompletableFuture;

@Route(value = "", layout = StartLayout.class)
@PageTitle("BaTeEn")
@CssImport("./styles/styles.css")
public class StartView extends VerticalLayout {
    private Button selectedButton;
    private Button testConnectionButton;
    private String url;
    private Boolean continueBool = false;


    public StartView() {
        testConnectionButton = createTestConnectionButton();

        this.setSizeFull();
        this.setSpacing(true);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(Alignment.CENTER);
        this.setPadding(true);
        add(createContent());
        add(new Span());
        add(testConnectionButton);
        add(new Span());
    }

    private Component createTitleContent() {
        VerticalLayout titleContent = new VerticalLayout();
        titleContent.setWidthFull();
        titleContent.setAlignItems(Alignment.CENTER);

        H1 title = new H1("BaTeEn Start");
        title.addClassName("custom-title");
        titleContent.add(title);

        StreamResource streamResource = new StreamResource("bateen.png", () -> getClass().getResourceAsStream("/bateen.png"));
        Image logo = new Image(streamResource, "BaTeEn");
        logo.setWidth("28%");
        logo.getStyle().set("border-radius", "var(--lumo-border-radius-l)");
        titleContent.addComponentAsFirst(logo);
        return titleContent;
    }

    public Component createContent() {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout buttonLayout = new HorizontalLayout();

        H2 header = new H2("Select the test object");
        contentLayout.add(header);

        buttonLayout.add(createTestObjectButton("Local", "localhost:50000"));
        buttonLayout.add(createTestObjectButton("Remote", "192.168.1.100:50000"));
        buttonLayout.add(createOtherButton());
        contentLayout.add(buttonLayout);

        return contentLayout;
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
            if (selectedButton != null) {
                selectedButton.getStyle().remove("border");
                selectedButton.getStyle().remove("box-shadow");
            }

            selectedButton = button;
            button.getStyle().set("border", "2px solid var(--lumo-primary-color)");
            button.getStyle().set("box-shadow", "0 0 5px var(--lumo-primary-color)");

            url = urlField.getValue();
            resetTestConnectionButton();
            getUI().ifPresent(ui -> ui.access(() -> testConnectionButton.setEnabled(true)));
            Notification.show("Ausgewählt: Other (" + (url.isEmpty() ? "Keine URL eingegeben" : url) + ")");
        });

        return button;
    }

    private Component createTestObjectButton(String title, String subtitle) {
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.setSpacing(false);
        buttonLayout.setPadding(false);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Span titleLabel = new Span(title);
        titleLabel.getStyle().set("font-weight", "bold");

        Span subtitleLabel = new Span(subtitle);
        subtitleLabel.getStyle().set("font-size", "var(--lumo-font-size-s)");
        subtitleLabel.getStyle().set("color", "var(--lumo-secondary-text-color)");

        buttonLayout.add(titleLabel, subtitleLabel);

        Button button = new Button(buttonLayout);
        button.setId(title);
        button.setWidth("200px");
        button.setHeight("80px");

        button.addClickListener(event -> {
            if (selectedButton != null) {
                selectedButton.getStyle().remove("border");
                selectedButton.getStyle().remove("box-shadow");
            }

            selectedButton = button;
            button.getStyle().set("border", "2px solid var(--lumo-primary-color)");
            button.getStyle().set("box-shadow", "0 0 5px var(--lumo-primary-color)");

            url = subtitle;
            resetTestConnectionButton();
            getUI().ifPresent(ui -> ui.access(() -> testConnectionButton.setEnabled(true)));
            Notification.show("Ausgewählt: " + title + " (" + url + ")");
        });
        return button;
    }

    private Button createTestConnectionButton() {
        Button button = new Button("Test Connection");
        button.setId("TestConnection");
        button.setWidth("500px");
        button.setHeight("80px");

        // Spinner-Icon erstellen und zum Button hinzufügen
        Icon spinnerIcon = VaadinIcon.SPINNER.create();
        spinnerIcon.addClassName("spinner");
        spinnerIcon.setVisible(false); // Initial unsichtbar
        button.setIcon(spinnerIcon);

        // Text für den Ladeindikator erstellen
        Span loadingText = new Span("Testing Connection");
        loadingText.setVisible(false); // Initial unsichtbar

        // Layout für Button und Ladeindikator
        HorizontalLayout buttonLayout = new HorizontalLayout(button, loadingText);
        buttonLayout.setAlignItems(Alignment.CENTER);

        // Button initial deaktivieren
        button.setEnabled(false);

        button.addClickListener(event -> {
            if (continueBool) {
                // Falls der Test bereits erfolgreich war und erneut geklickt wird, navigiere weiter
                getUI().ifPresent(ui -> ui.navigate("login"));
                return;
            }
            resetTestConnectionButton();
            if (url == null || url.isEmpty()) {
                Notification.show("Select a test object or enter a URL", 3000, Notification.Position.MIDDLE);
                return;
            }

            // Button während des Tests deaktivieren und Spinner sowie Text anzeigen
            button.setEnabled(false);
            spinnerIcon.setVisible(true);
            loadingText.setVisible(true);

            // Verbindungstest im Hintergrund ausführen
            CompletableFuture.supplyAsync(() -> testConnection(url))
                    .thenAccept(success -> {
                        // UI-Änderungen im UI-Thread vornehmen
                        getUI().ifPresent(ui -> ui.access(() -> {
                            spinnerIcon.setVisible(false);
                            loadingText.setVisible(false);
                            if (success) {
                                continueBool = true;
                                button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
                                button.setText("Connected successfully! Click here to continue");
                            } else {
                                button.addThemeVariants(ButtonVariant.LUMO_ERROR);
                                button.setText("Unable to connect");
                            }
                            button.setEnabled(true);
                        }));
                    });
        });

        return button;
    }


    private boolean testConnection(String url) {
        try {
            Thread.sleep(2000);
            return Math.random() < 0.8;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private void resetTestConnectionButton() {
        testConnectionButton.removeThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_ERROR);
        testConnectionButton.setText("Test Connection");
        testConnectionButton.setEnabled(false);
        continueBool = false;
    }


}
