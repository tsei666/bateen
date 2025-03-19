package de.breuer.bateen.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.view.layout.StartLayout;

@Route(value = "/login", layout = StartLayout.class)
@PageTitle("BaTeEn")
public class LoginView extends VerticalLayout {
    public LoginView() {
        add(createContent());
    }

    public Component createContent(){
        TextField username = new TextField("Username");
        return username;
    }
}
