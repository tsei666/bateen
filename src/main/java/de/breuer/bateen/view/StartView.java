package de.breuer.bateen.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainView.class)
@PageTitle("BaTeEn")
public class StartView extends VerticalLayout {
    public StartView() {
        add(createContent());
    }

    public Component createContent(){
        TextField username = new TextField("Username");
        return username;
    }
}
