package de.breuer.bateen.ui.animation;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.Component;

public interface AnimationBuilder {
    void build(HorizontalLayout scene, Component parentView);
}
