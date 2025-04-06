package de.breuer.bateen.ui.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.ui.layout.MainLayout;

@Route(value = "configure", layout = MainLayout.class)
@PageTitle("Configure Test Case")
public class ConfigureTestView extends VerticalLayout {

    public ConfigureTestView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Configure Test Case");
        add(title);
    }
}
