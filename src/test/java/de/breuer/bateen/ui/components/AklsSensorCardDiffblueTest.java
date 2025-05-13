package de.breuer.bateen.ui.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.dom.Element;
import de.breuer.bateen.sensor.AklsSensor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AklsSensorCardDiffblueTest {
    /**
     * Test {@link AklsSensorCard#AklsSensorCard(AklsSensor)}.
     * <ul>
     *   <li>Given {@code Anpr Overview Picture}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AklsSensorCard#AklsSensorCard(AklsSensor)}
     */
    @Test
    @DisplayName("Test new AklsSensorCard(AklsSensor); given 'Anpr Overview Picture'")
    @Tag("MaintainedByDiffblue")
    void testNewAklsSensorCard_givenAnprOverviewPicture() {
        // Arrange
        AklsSensor akls = new AklsSensor();
        akls.setAnprCountry("GB");
        akls.setAnprCountryConfidence(1);
        akls.setAnprNumberPlate("42");
        akls.setAnprNumberPlateConfidence(1);
        akls.setAnprOverviewPicture("Anpr Overview Picture");
        akls.setAnprPlatePicture("Anpr Plate Picture");
        akls.setAnprSideviewPicture("Anpr Sideview Picture");
        akls.setSensorRecordId("42");

        // Act
        AklsSensorCard actualAklsSensorCard = new AklsSensorCard(akls);

        // Assert
        Stream<Component> children = actualAklsSensorCard.getChildren();
        List<Component> collectResult = children.limit(5).collect(Collectors.toList());
        assertEquals(5, collectResult.size());
        assertTrue(collectResult.get(0) instanceof H3);
        assertTrue(collectResult.get(1) instanceof Paragraph);
        assertTrue(collectResult.get(2) instanceof Paragraph);
        assertTrue(collectResult.get(3) instanceof Paragraph);
        assertTrue(collectResult.get(4) instanceof Paragraph);
        Element element = actualAklsSensorCard.getElement();
        assertEquals("<vaadin-vertical-layout style=\"width:350px;border:1px solid #ddd;border-radius:8px;background-color:"
                     + "#fafafa;padding:1em;box-shadow:0 2px 4px rgba(0,0,0,0.05)\">\n" + " <h3>AKLS Sensor</h3>\n"
                     + " <p>License Plate: 42</p>\n" + " <p>License Plate Confidence: 1</p>\n" + " <p>Country: GB</p>\n"
                     + " <p>Country Confidence: 1</p>\n" + " <p>Plate Picture: Anpr Plate Picture</p>\n"
                     + " <p>Overview Picture: Anpr Overview Picture</p>\n" + " <p>Sideview Picture: Anpr Sideview Picture</p>\n"
                     + "</vaadin-vertical-layout>", element.getOuterHTML());
        assertEquals(
                "AKLS SensorLicense Plate: 42License Plate Confidence: 1Country: GBCountry Confidence: 1Plate Picture:"
                + " Anpr Plate PictureOverview Picture: Anpr Overview PictureSideview Picture: Anpr Sideview Picture",
                element.getTextRecursively());
        Stream<Element> children2 = element.getChildren();
        assertEquals(5, children2.limit(5).collect(Collectors.toList()).size());
    }

    /**
     * Test {@link AklsSensorCard#AklsSensorCard(AklsSensor)}.
     * <ul>
     *   <li>Given {@code AklsSensor}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AklsSensorCard#AklsSensorCard(AklsSensor)}
     */
    @Test
    @DisplayName("Test new AklsSensorCard(AklsSensor); given 'de.breuer.bateen.sensor.AklsSensor'")
    @Tag("MaintainedByDiffblue")
    void testNewAklsSensorCard_givenDeBreuerBateenSensorAklsSensor() {
        // Arrange
        AklsSensor akls = new AklsSensor();
        akls.setAnprCountry("GB");
        akls.setAnprCountryConfidence(1);
        akls.setAnprNumberPlate("42");
        akls.setAnprNumberPlateConfidence(1);
        akls.setAnprOverviewPicture("de.breuer.bateen.sensor.AklsSensor");
        akls.setAnprPlatePicture("Anpr Plate Picture");
        akls.setAnprSideviewPicture("Anpr Sideview Picture");
        akls.setSensorRecordId("42");

        // Act
        AklsSensorCard actualAklsSensorCard = new AklsSensorCard(akls);

        // Assert
        Stream<Component> children = actualAklsSensorCard.getChildren();
        List<Component> collectResult = children.limit(5).collect(Collectors.toList());
        assertEquals(5, collectResult.size());
        assertTrue(collectResult.get(0) instanceof H3);
        assertTrue(collectResult.get(1) instanceof Paragraph);
        assertTrue(collectResult.get(2) instanceof Paragraph);
        assertTrue(collectResult.get(3) instanceof Paragraph);
        assertTrue(collectResult.get(4) instanceof Paragraph);
        Element element = actualAklsSensorCard.getElement();
        assertEquals(
                "<vaadin-vertical-layout style=\"width:350px;border:1px solid #ddd;border-radius:8px;background-color:"
                + "#fafafa;padding:1em;box-shadow:0 2px 4px rgba(0,0,0,0.05)\">\n" + " <h3>AKLS Sensor</h3>\n"
                + " <p>License Plate: 42</p>\n" + " <p>License Plate Confidence: 1</p>\n" + " <p>Country: GB</p>\n"
                + " <p>Country Confidence: 1</p>\n" + " <p>Plate Picture: Anpr Plate Picture</p>\n"
                + " <p>Overview Picture: de.breuer.bateen.sensor.AklsSe...</p>\n"
                + " <p>Sideview Picture: Anpr Sideview Picture</p>\n" + "</vaadin-vertical-layout>",
                element.getOuterHTML());
        assertEquals("AKLS SensorLicense Plate: 42License Plate Confidence: 1Country: GBCountry Confidence: 1Plate Picture:"
                     + " Anpr Plate PictureOverview Picture: de.breuer.bateen.sensor.AklsSe...Sideview Picture: Anpr Sideview"
                     + " Picture", element.getTextRecursively());
        Stream<Element> children2 = element.getChildren();
        assertEquals(5, children2.limit(5).collect(Collectors.toList()).size());
    }

    /**
     * Test {@link AklsSensorCard#AklsSensorCard(AklsSensor)}.
     * <ul>
     *   <li>Given {@code null}.</li>
     *   <li>When {@link AklsSensor} (default constructor) AnprPlatePicture is {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AklsSensorCard#AklsSensorCard(AklsSensor)}
     */
    @Test
    @DisplayName("Test new AklsSensorCard(AklsSensor); given 'null'; when AklsSensor (default constructor) AnprPlatePicture is 'null'")
    @Tag("MaintainedByDiffblue")
    void testNewAklsSensorCard_givenNull_whenAklsSensorAnprPlatePictureIsNull() {
        // Arrange
        AklsSensor akls = new AklsSensor();
        akls.setAnprCountry("GB");
        akls.setAnprCountryConfidence(1);
        akls.setAnprNumberPlate("42");
        akls.setAnprNumberPlateConfidence(1);
        akls.setSensorRecordId("42");
        akls.setAnprPlatePicture(null);
        akls.setAnprSideviewPicture(null);
        akls.setAnprOverviewPicture(null);

        // Act
        AklsSensorCard actualAklsSensorCard = new AklsSensorCard(akls);

        // Assert
        Stream<Component> children = actualAklsSensorCard.getChildren();
        List<Component> collectResult = children.limit(5).collect(Collectors.toList());
        assertEquals(5, collectResult.size());
        assertTrue(collectResult.get(0) instanceof H3);
        assertTrue(collectResult.get(1) instanceof Paragraph);
        assertTrue(collectResult.get(2) instanceof Paragraph);
        assertTrue(collectResult.get(3) instanceof Paragraph);
        assertTrue(collectResult.get(4) instanceof Paragraph);
        Element element = actualAklsSensorCard.getElement();
        assertEquals("<vaadin-vertical-layout style=\"width:350px;border:1px solid #ddd;border-radius:8px;background-color:"
                     + "#fafafa;padding:1em;box-shadow:0 2px 4px rgba(0,0,0,0.05)\">\n" + " <h3>AKLS Sensor</h3>\n"
                     + " <p>License Plate: 42</p>\n" + " <p>License Plate Confidence: 1</p>\n" + " <p>Country: GB</p>\n"
                     + " <p>Country Confidence: 1</p>\n" + " <p>Plate Picture: null</p>\n" + " <p>Overview Picture: null</p>\n"
                     + " <p>Sideview Picture: null</p>\n" + "</vaadin-vertical-layout>", element.getOuterHTML());
        assertEquals("AKLS SensorLicense Plate: 42License Plate Confidence: 1Country: GBCountry Confidence: 1Plate Picture:"
                     + " nullOverview Picture: nullSideview Picture: null", element.getTextRecursively());
        Stream<Element> children2 = element.getChildren();
        assertEquals(5, children2.limit(5).collect(Collectors.toList()).size());
    }
}
