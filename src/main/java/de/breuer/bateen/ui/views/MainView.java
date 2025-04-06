package de.breuer.bateen.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.breuer.bateen.ui.layout.MainLayout;

@Route(value = "/main", layout = MainLayout.class)
@PageTitle("BaTeEn")
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout {

    public MainView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);
        setPadding(true);

        // Buttons
        Button configureButton = createNavigationButton("Configure Test Case", "configure");
        Button automaticButton = createNavigationButton("Automatic Test Case", "automatic");
        Button vmButton = createNavigationButton("Change VM", "change-vm");

        add(configureButton, automaticButton, vmButton);
    }

    private Button createNavigationButton(String label, String route) {
        Button button = new Button(label);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        button.getStyle()
                .set("width", "300px")
                .set("height", "60px")
                .set("font-size", "18px")
                .set("box-shadow", "0 4px 10px rgba(0,0,0,0.15)")
                .set("border-radius", "12px")
                .set("background-color", "#233348");

        button.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(route)));
        return button;
    }
}
