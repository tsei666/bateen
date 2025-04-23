package de.breuer.bateen.ui.manually;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import de.breuer.bateen.sensor.Sensor;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.*;

@Route(value = "manual", layout = de.breuer.bateen.ui.layout.MainLayout.class)
@PageTitle("Manuelle Tests")
public class ManuallyTestView extends VerticalLayout {

    public ManuallyTestView(ManuallyTestViewController controller) {
        setPadding(true);
        setSpacing(true);

        add(new H2("Manueller Sensor-Test"));

        controller.getSensorData().forEach(sensor -> add(createSensorForm(sensor, controller)));
    }

    private VerticalLayout createSensorForm(Sensor<?> sensor, ManuallyTestViewController controller) {
        Object data = sensor.getData();
        Class<?> clazz = data.getClass();

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        Map<String, Object> fieldComponents = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();

            try {
                if (List.of("anprPlatePicture", "anprOverviewPicture", "anprSideviewPicture", "irImages").contains(name)) {
                    addImageSelector(name, data, formLayout, name.equals("irImages"));
                } else {
                    addComponentForField(data, formLayout, fieldComponents, field);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Button save = new Button("Speichern", e -> applyFormChanges(data, clazz, fieldComponents));

        VerticalLayout layout = new VerticalLayout();
        layout.add(new H2(clazz.getSimpleName()), formLayout, save);
        return layout;
    }

    private void addComponentForField(Object data, FormLayout formLayout, Map<String, Object> fieldComponents, Field field) throws IllegalAccessException {
        String name = field.getName();
        if (field.getType().equals(String.class)) {
            TextField tf = new TextField(name);
            tf.setValue((String) field.get(data));
            fieldComponents.put(name, tf);
            formLayout.add(tf);
        } else if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
            IntegerField intField = new IntegerField(name);
            intField.setValue((Integer) field.get(data));
            fieldComponents.put(name, intField);
            formLayout.add(intField);
        } else if (field.getType().equals(boolean.class)) {
            Checkbox cb = new Checkbox(name);
            cb.setValue((Boolean) field.get(data));
            fieldComponents.put(name, cb);
            formLayout.add(cb);
        }
    }

    private void applyFormChanges(Object data, Class<?> clazz, Map<String, Object> fieldComponents) {
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object input = fieldComponents.get(field.getName());
                if (input instanceof TextField tf) {
                    field.set(data, tf.getValue());
                } else if (input instanceof IntegerField intField) {
                    field.set(data, intField.getValue());
                } else if (input instanceof Checkbox cb) {
                    field.set(data, cb.getValue());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addImageSelector(String fieldName, Object data, FormLayout formLayout, boolean isListField) {
        TextField base64Preview = new TextField(fieldName + " (Base64)");
        base64Preview.setReadOnly(true);
        base64Preview.setWidthFull();

        Button selectImageButton = new Button("Bild auswählen");

        selectImageButton.addClickListener(e -> {
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Wähle ein Bild für: " + fieldName);

            VerticalLayout imageList = new VerticalLayout();
            File[] images = new File("src/main/resources/" + fieldName).listFiles();

            if (images != null) {
                for (File img : images) {
                    StreamResource res = new StreamResource(img.getName(), () -> {
                        try {
                            return new FileInputStream(img);
                        } catch (Exception ex) {
                            return null;
                        }
                    });

                    Image image = new Image(res, img.getName());
                    image.setWidth("150px");
                    image.getStyle().set("cursor", "pointer");

                    image.addClickListener(event -> {
                        try {
                            byte[] bytes = Files.readAllBytes(img.toPath());
                            String base64 = Base64.getEncoder().encodeToString(bytes);
                            Field field = data.getClass().getDeclaredField(fieldName);
                            field.setAccessible(true);
                            if (isListField) {
                                List<String> list = (List<String>) field.get(data);
                                if (list == null) list = new ArrayList<>();
                                list.add(base64);
                                field.set(data, list);
                                base64Preview.setValue("Liste: " + list.size() + " Einträge");
                            } else {
                                field.set(data, base64);
                                base64Preview.setValue(base64);
                            }
                            Notification.show("Bild ausgewählt: " + img.getName());
                            dialog.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                    imageList.add(image);
                }
            }

            dialog.add(imageList);
            dialog.setWidth("600px");
            dialog.setHeight("400px");
            dialog.open();
        });

        formLayout.add(base64Preview, selectImageButton);
    }
}