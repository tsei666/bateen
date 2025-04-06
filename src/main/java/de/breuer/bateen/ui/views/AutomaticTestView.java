package de.breuer.bateen.ui.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.ui.layout.MainLayout;

@Route(value = "automatic", layout = MainLayout.class)
@PageTitle("Automatic Test Case")
public class AutomaticTestView extends VerticalLayout {

    public AutomaticTestView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Automatic Test Case");
        add(title);
    }
}
