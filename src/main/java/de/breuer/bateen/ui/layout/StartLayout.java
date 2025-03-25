package de.breuer.bateen.ui.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.StreamResource;

@CssImport("./styles/styles.css")
public class StartLayout extends VerticalLayout implements RouterLayout {

    public StartLayout() {
        setSizeFull();
        getStyle().set("background-color", "white");
        getStyle().set("padding", "30px");
        setSpacing(true);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setPadding(true);

        add(createTitleContent());
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
        logo.setWidth("23%");
        logo.getStyle().set("border-radius", "var(--lumo-border-radius-l)");
        titleContent.addComponentAsFirst(logo);

        return titleContent;
    }
}
