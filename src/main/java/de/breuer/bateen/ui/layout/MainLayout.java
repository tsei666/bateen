package de.breuer.bateen.ui.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.ui.AutomaticTestView;
import de.breuer.bateen.ui.ChangeVmView;
import de.breuer.bateen.ui.ConfigureTestView;
import de.breuer.bateen.ui.MainView;
import de.breuer.bateen.ui.login.LoginViewController;
import de.breuer.bateen.ui.login.LoginViewModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

//@CssImport("./styles/views/main/main-view.css")
//@JsModule("./styles/shared-styles.js")
public class MainLayout extends AppLayout {
    LoginViewController loginViewController;
    LoginViewModel loginModel;
    private final Tabs menu;
    private H1 viewTitle;

    public MainLayout(@Autowired LoginViewController loginViewController, @Autowired LoginViewModel loginViewModel) {
        this.loginViewController = loginViewController;
        this.loginModel = loginViewModel;
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();

        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        layout.getStyle().set("padding", "10px");
        layout.add(new DrawerToggle());

        StreamResource streamResource = new StreamResource("bateen.png", () -> getClass().getResourceAsStream("/bateen.png"));
        Image logo = new Image(streamResource, "BaTeEn");
        logo.setWidth("4%");
        logo.getStyle().set("border-radius", "var(--lumo-border-radius-l)");
        layout.add(logo);
        viewTitle = new H1();
        layout.add(viewTitle);

        Span spacer = new Span();

        layout.add(spacer);

        layout.add(createLoginCard("kfz0001", 1234, "Müller"));
        layout.add(spacer);
        layout.add(createLoginCard("kfz0002", 1234, "Doe"));

        layout.add(spacer);
        H3 title = new H3(String.format("Connected VM: %s", ConfigController.getUrl()));
        layout.add(title);

        return layout;
    }

    private Div createLoginCard(String officerId, int pin, String label) {
        Div card = new Div();
        card.setText(label);
        card.getStyle()
                .set("width", "120px")
                .set("height", "60px")
                .set("background", loginModel.isOfficerLoggedIn(officerId) ? "#a5d6a7" : "#e0e0e0")
                .set("border-radius", "8px")
                .set("box-shadow", "1px 1px 4px rgba(0,0,0,0.1)")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("transition", "transform 0.3s ease")
                .set("cursor", "pointer")
                .set("font-size", "12px")
                .set("color", "#233348");

        card.addClickListener(click -> {
            var result = loginViewController.toggleOfficerLogin(officerId, pin);
            switch (result) {
                case LOGIN_SUCCESS -> {
                    card.getStyle().set("background", "#a5d6a7").set("transform", "translateY(5px) scale(0.95)");
                    Notification.show("Login success", 2000, Notification.Position.TOP_CENTER);
                }
                case LOGIN_FAILED -> {
                    card.getStyle().set("background", "#ef9a9a").set("transform", "scale(1)");
                    Notification.show("Login failed", 2000, Notification.Position.TOP_CENTER);
                }
                case LOGOUT -> {
                    card.getStyle().set("background", "#e0e0e0").set("transform", "scale(1)");
                    Notification.show("Logout success", 2000, Notification.Position.TOP_CENTER);
                }
            }
        });

        return card;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();

        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new H1("BaTeEn"));

        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        return new Tab[] {
                createTab("Home", MainView.class),
                createTab("Configure Test", ConfigureTestView.class),
                createTab("Automatic", AutomaticTestView.class),
                createTab("Change VM", ChangeVmView.class)
        };//createTab("Hello World", StartView.class),
    }

    private static Tab createTab(String text,
                                 Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren()
                .filter(tab -> ComponentUtil.getData(tab, Class.class)
                        .equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }
}
