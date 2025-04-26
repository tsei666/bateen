package de.breuer.bateen.util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;
import de.breuer.bateen.model.VehicleType;
import de.breuer.bateen.model.ir.IrImageModel;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class FormFieldHelper {

    private FormFieldHelper() {
    }

    public static void addLiveTextField(FormLayout layout, String label, Supplier<String> getter, Consumer<String> setter, String helperText) {
        TextField tf = new TextField(label);
        tf.setWidthFull();
        tf.setHelperText(helperText);
        tf.setValue(Optional.ofNullable(getter.get()).orElse(""));
        tf.addValueChangeListener(e -> setter.accept(e.getValue()));
        layout.add(tf);
    }

    public static void addLiveIntegerField(FormLayout layout, String label, Supplier<Integer> getter, Consumer<Integer> setter, String helperText) {
        IntegerField intField = new IntegerField(label);
        intField.setWidthFull();
        intField.setHelperText(helperText);
        intField.setValue(Optional.ofNullable(getter.get()).orElse(0));
        intField.addValueChangeListener(e -> setter.accept(e.getValue()));
        layout.add(intField);
    }

    public static void addLiveLongField(FormLayout layout, String label, Supplier<Long> getter, Consumer<Long> setter, String helperText) {
        IntegerField longField = new IntegerField(label);
        longField.setWidthFull();
        longField.setHelperText(helperText);
        longField.setValue(Optional.ofNullable(getter.get()).map(Long::intValue).orElse(0));
        longField.addValueChangeListener(e -> setter.accept(longField.getValue().longValue()));
        layout.add(longField);
    }

    public static void addLiveCheckbox(FormLayout layout, String label, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        Checkbox cb = new Checkbox(label);
        cb.setValue(Optional.ofNullable(getter.get()).orElse(false));
        cb.addValueChangeListener(e -> setter.accept(cb.getValue()));
        layout.add(cb);
    }

    public static void addLiveFloatListField(FormLayout layout, String label, List<Float> values, Consumer<List<Float>> setter) {
        TextField listField = new TextField(label);
        listField.setWidthFull();
        listField.setValue(values != null ? values.toString().replaceAll("[\\[\\]]", "") : "");
        listField.setHelperText("Separate by comma");
        listField.addValueChangeListener(e -> {
            try {
                String[] tokens = listField.getValue().split(",");
                List<Float> floatList = new ArrayList<>();
                for (String token : tokens) {
                    floatList.add(Float.parseFloat(token.trim()));
                }
                setter.accept(floatList);
            } catch (NumberFormatException ex) {
                Notification.show("Ungültiges Format für " + label);
            }
        });
        layout.add(listField);
    }

    public static void addImageSelector(String fieldName, Supplier<String> getter, Consumer<String> setter, FormLayout formLayout) {
        TextField base64Preview = new TextField(fieldName + " (Base64)");
        base64Preview.setReadOnly(true);
        base64Preview.setWidthFull();
        base64Preview.setValue(Optional.ofNullable(getter.get()).orElse(""));

        Button selectImageButton = new Button("Select image", e -> openImageDialog(fieldName, base64 -> {
            setter.accept(base64);
            base64Preview.setValue(base64);
        }));

        formLayout.add(base64Preview, selectImageButton);
    }

    public static void addImageSelectorList(String fieldName, List<String> current, Consumer<List<String>> setter, FormLayout formLayout) {
        TextField base64Preview = new TextField(fieldName + " (Base64)");
        base64Preview.setReadOnly(true);
        base64Preview.setWidthFull();
        base64Preview.setValue("List of images: " + (current != null ? current.size() : 0) + " entries");

        Button selectImageButton = new Button("Select image", e -> openImageDialog(fieldName, base64 -> {
            List<String> updated = current != null ? new ArrayList<>(current) : new ArrayList<>();
            updated.add(base64);
            setter.accept(updated);
            base64Preview.setValue("List of images: " + updated.size() + " entries");
        }));

        formLayout.add(base64Preview, selectImageButton);
    }

    public static void addIrImageSelectorList(String fieldName, List<IrImageModel> current, Consumer<List<IrImageModel>> setter, FormLayout formLayout) {
        TextField previewField = new TextField(fieldName + " (Images)");
        previewField.setReadOnly(true);
        previewField.setWidthFull();
        previewField.setValue("Images: " + (current != null ? current.size() : 0) + " entries");

        Button selectImageButton = new Button("Select image", e -> openImageDialog(fieldName, base64 -> {
            List<IrImageModel> updated = current != null ? new ArrayList<>(current) : new ArrayList<>();
            IrImageModel newImage = new IrImageModel();
            newImage.setRawData(base64);
            newImage.setType("UNKNOWN"); // Oder ein sinnvoller Standardwert
            newImage.setTimestamp((int) System.currentTimeMillis());
            updated.add(newImage);
            setter.accept(updated);
            previewField.setValue("Images: " + updated.size() + " entries");
        }));

        formLayout.add(previewField, selectImageButton);
    }


    private static void openImageDialog(String fieldName, Consumer<String> onImageSelected) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Select image for: " + fieldName);

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
                        String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(img.toPath()));
                        onImageSelected.accept(base64);
                        Notification.show("Selected image: " + img.getName());
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
    }

    public static void addVehicleTypeComboBox(FormLayout layout, String label, Supplier<VehicleType> getter, Consumer<VehicleType> setter) {
        ComboBox<VehicleType> comboBox = new ComboBox<>(label);
        comboBox.setHelperText("Choose the vehicle type");
        comboBox.setWidthFull();
        comboBox.setItems(VehicleType.values());
        comboBox.setItemLabelGenerator(VehicleType::toString);
        comboBox.setValue(Optional.ofNullable(getter.get()).orElse(VehicleType.NONE));
        comboBox.addValueChangeListener(event -> setter.accept(event.getValue()));
        layout.add(comboBox);
    }

    public static void addLiveIntegerListField(FormLayout form, String label, List<Integer> list, Consumer<List<Integer>> setter) {
        TextArea field = new TextArea(label);
        field.setValue(list != null ? list.toString() : "[]");
        field.addValueChangeListener(event -> {
            try {
                String value = event.getValue().replaceAll("[\\[\\]]", "");
                List<Integer> newList = Arrays.stream(value.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Integer::parseInt)
                        .toList();
                setter.accept(newList);
            } catch (NumberFormatException e) {
                // ignore invalid input for now
            }
        });
        form.add(field);
    }

}
