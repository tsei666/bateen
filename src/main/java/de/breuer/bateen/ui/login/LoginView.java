package de.breuer.bateen.ui.login;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.controller.LoginController;
import de.breuer.bateen.ui.layout.StartLayout;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "/login", layout = StartLayout.class)
@PageTitle("BaTeEn")
@PermitAll
public class LoginView extends VerticalLayout {

    private final LoginViewController controller;
    private final LoginViewModel model = new LoginViewModel();

    @Autowired
    public LoginView(LoginController loginController) {
        this.controller = new LoginViewController(loginController, model);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(createLoginCards(), createContinueButton());
    }

    private Component createLoginCards() {
        FlexLayout cardContainer = new FlexLayout();
        cardContainer.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        cardContainer.setJustifyContentMode(JustifyContentMode.CENTER);
        cardContainer.setAlignItems(FlexLayout.Alignment.CENTER);
        cardContainer.getStyle().set("gap", "2em").set("margin", "2em");

        cardContainer.add(createCard("kfz0001", 1234, "Officer Horst Müller"));
        cardContainer.add(createCard("kfz0002", 1234, "Officer John Doe"));

        return cardContainer;
    }

    private Div createCard(String officerId, int pin, String label) {
        Div card = new Div();
        card.setText(label);
        card.getStyle()
                .set("width", "150px")
                .set("height", "100px")
                .set("background", "#e0e0e0")
                .set("border-radius", "12px")
                .set("box-shadow", "2px 2px 6px rgba(0,0,0,0.2)")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("transition", "transform 0.4s ease")
                .set("cursor", "pointer");

        card.addClickListener(click -> {
            LoginViewController.LoginResult result = controller.toggleOfficerLogin(officerId, pin);

            switch (result) {
                case LOGIN_SUCCESS -> {
                    card.getStyle().set("background", "#a5d6a7").set("transform", "translateY(10px) scale(0.95)");
                    Notification.show("Login success", 3000, Notification.Position.TOP_CENTER);
                }
                case LOGIN_FAILED -> {
                    card.getStyle().set("background", "#ef9a9a").set("transform", "scale(1)");
                    Notification.show("Login failed", 3000, Notification.Position.TOP_CENTER);
                }
                case LOGOUT -> {
                    card.getStyle().set("background", "#e0e0e0").set("transform", "scale(1)");
                    Notification.show("Logout success", 3000, Notification.Position.TOP_CENTER);
                }
            }
        });

        return card;
    }

    private Component createContinueButton() {
        Button weiterButton = new Button("Next", click -> {
            Notification.show("Weiter zur nächsten Ansicht...", 2000, Notification.Position.BOTTOM_CENTER);
            // Navigation hier einfügen
        });
        weiterButton.getStyle()
                .set("margin-top", "40px")
                .set("font-size", "18px")
                .set("padding", "10px 20px");
        return weiterButton;
    }
}

