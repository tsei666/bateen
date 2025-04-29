package de.breuer.bateen.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.VmRepository;
import de.breuer.bateen.config.VmConfig;
import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.ui.layout.MainLayout;

@Route(value = "change-vm", layout = MainLayout.class)
@PageTitle("Change VM")
public class ChangeVmView extends VerticalLayout {

    private String selectedUrl;
    private Button selectedButton;
    private final Button saveButton;

    public ChangeVmView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);
        setPadding(true);

        H2 title = new H2("Change Virtual Machine Connection");

        HorizontalLayout buttonLayout = new HorizontalLayout();

        for (VmConfig vm : VmRepository.getAvailableVms()) {
            buttonLayout.add(createUrlButton(vm.getName(), vm.getUrl()));
        }
        buttonLayout.add(createCustomUrlButton());

        saveButton = new Button("Apply Configuration", e -> saveUrl());
        saveButton.setEnabled(false);
        saveButton.setWidth("300px");

        add(title, buttonLayout, new Span(), saveButton);
    }

    private Button createUrlButton(String label, String url) {
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.setSpacing(false);
        buttonLayout.setPadding(false);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        Span titleLabel = new Span(label);
        titleLabel.getStyle().set("font-weight", "bold");

        Span subtitleLabel = new Span(url);
        subtitleLabel.getStyle().set("font-size", "var(--lumo-font-size-s)");
        subtitleLabel.getStyle().set("color", "var(--lumo-secondary-text-color)");

        buttonLayout.add(titleLabel, subtitleLabel);

        Button button = new Button(buttonLayout);
        button.setId(label);
        button.setWidth("200px");
        button.setHeight("60px");

        button.addClickListener(e -> {
            selectButton(button);
            selectedUrl = url;
            saveButton.setEnabled(true);
            Notification.show("Selected: " + label + " (" + url + ")");
        });

        return button;
    }

    private Component createCustomUrlButton() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(Alignment.CENTER);

        Span label = new Span("Other:");
        TextField urlField = new TextField();
        urlField.setPlaceholder("Enter custom URL");
        urlField.setWidth("180px");

        Button button = new Button(layout);
        button.setWidth("200px");
        button.setHeight("60px");

        layout.add(label, urlField);

        button.addClickListener(e -> {
            selectButton(button);
            selectedUrl = urlField.getValue();
            saveButton.setEnabled(true);
            Notification.show("Selected: Other (" + (selectedUrl.isEmpty() ? "Empty" : selectedUrl) + ")");
        });

        return button;
    }

    private void selectButton(Button button) {
        if (selectedButton != null) {
            selectedButton.getStyle().remove("border");
            selectedButton.getStyle().remove("box-shadow");
        }

        selectedButton = button;
        selectedButton.getStyle().set("border", "2px solid var(--lumo-primary-color)");
        selectedButton.getStyle().set("box-shadow", "0 0 5px var(--lumo-primary-color)");
    }

    private void saveUrl() {
        if (selectedUrl == null || selectedUrl.isEmpty()) {
            Notification.show("No URL selected", 3000, Notification.Position.MIDDLE);
            return;
        }

        ConfigController.setUrl(selectedUrl);
        Notification.show("Configuration updated: " + selectedUrl, 3000, Notification.Position.TOP_CENTER);
        saveButton.setEnabled(false);
        UI.getCurrent().getPage().reload();
    }
}
