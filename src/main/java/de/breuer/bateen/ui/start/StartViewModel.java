package de.breuer.bateen.ui.start;

import com.vaadin.flow.component.button.Button;
import lombok.Data;

@Data
public class StartViewModel {
    private Button selectedButton;
    private String url;
    private boolean continueEnabled;
}
