package de.breuer.bateen.ui.automatic;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.breuer.bateen.controller.SensorDataController;
import de.breuer.bateen.factory.SensorFactory;
import de.breuer.bateen.sensor.Sensor;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AutomaticTestViewController.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class AutomaticTestViewControllerDiffblueTest {
    @Autowired
    private AutomaticTestViewController automaticTestViewController;

    @MockitoBean
    private SensorDataController sensorDataController;

    @MockitoBean
    private SensorFactory sensorFactory;

    /**
     * Test {@link AutomaticTestViewController#generateTestCase(String)}.
     * <p>
     * Method under test: {@link AutomaticTestViewController#generateTestCase(String)}
     */
    @Test
    @DisplayName("Test generateTestCase(String)")
    @Tag("MaintainedByDiffblue")
    void testGenerateTestCase() {
        // Arrange
        doNothing().when(sensorFactory).generateTestCase(Mockito.<String>any());

        // Act
        automaticTestViewController.generateTestCase("42");

        // Assert
        verify(sensorFactory).generateTestCase(eq("42"));
    }

    /**
     * Test {@link AutomaticTestViewController#getSensorData()}.
     * <p>
     * Method under test: {@link AutomaticTestViewController#getSensorData()}
     */
    @Test
    @DisplayName("Test getSensorData()")
    @Tag("MaintainedByDiffblue")
    void testGetSensorData() {
        // Arrange
        Mockito.<List<Sensor<?>>>when(sensorDataController.getSensorData()).thenReturn(null);

        // Act
        List<Sensor<?>> actualSensorData = automaticTestViewController.getSensorData();

        // Assert
        verify(sensorDataController).getSensorData();
        assertNull(actualSensorData);
    }
}
