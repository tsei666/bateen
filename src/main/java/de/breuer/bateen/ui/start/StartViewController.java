package de.breuer.bateen.ui.start;

import com.vaadin.flow.component.button.Button;
import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.controller.ConnectionController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StartViewController {

    private final ConnectionController connectionController;
    private final StartViewModel model;

    public void handleButtonSelection(Button selected, String url) {
        if (model.getSelectedButton() != null) {
            model.getSelectedButton().getStyle().remove("border");
            model.getSelectedButton().getStyle().remove("box-shadow");
        }

        model.setSelectedButton(selected);
        selected.getStyle().set("border", "2px solid var(--lumo-primary-color)");
        selected.getStyle().set("box-shadow", "0 0 5px var(--lumo-primary-color)");
        model.setUrl(url);
    }

    public boolean testConnection() {
        ConfigController.setUrl(model.getUrl());
        return connectionController.isAlive();
    }

    public void reset() {
        model.setContinueEnabled(false);
    }

    public void enableContinue() {
        model.setContinueEnabled(true);
    }

    public boolean isContinueEnabled() {
        return model.isContinueEnabled();
    }
}
