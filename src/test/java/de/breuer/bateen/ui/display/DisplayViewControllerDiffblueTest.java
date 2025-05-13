package de.breuer.bateen.ui.display;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.breuer.bateen.controller.DisplayController;
import de.breuer.bateen.model.ControlModeModel;
import de.breuer.bateen.model.DeviceStatusModel;
import de.breuer.bateen.model.Display;
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

@ContextConfiguration(classes = {DisplayViewController.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class DisplayViewControllerDiffblueTest {
    @MockitoBean
    private DisplayController displayController;

    @Autowired
    private DisplayViewController displayViewController;

    /**
     * Test {@link DisplayViewController#getDisplay()}.
     * <p>
     * Method under test: {@link DisplayViewController#getDisplay()}
     */
    @Test
    @DisplayName("Test getDisplay()")
    @Tag("MaintainedByDiffblue")
    void testGetDisplay() {
        // Arrange
        Display display = new Display();
        display.setControlMode(1);
        DeviceStatusModel deviceStatus = DeviceStatusModel.builder()
                .aklsOn(true)
                .aklsUpdateInProgress(true)
                .dsrcOn(true)
                .wifiApOn(true)
                .build();
        display.setDeviceStatus(deviceStatus);
        when(displayController.getDisplay()).thenReturn(display);

        // Act
        Display actualDisplay = displayViewController.getDisplay();

        // Assert
        verify(displayController).getDisplay();
        assertSame(display, actualDisplay);
    }

    /**
     * Test {@link DisplayViewController#toggleControlMode(Display)}.
     * <ul>
     *   <li>Given {@code CONTROL_MODE_MOBIL}.</li>
     * </ul>
     * <p>
     * Method under test: {@link DisplayViewController#toggleControlMode(Display)}
     */
    @Test
    @DisplayName("Test toggleControlMode(Display); given 'CONTROL_MODE_MOBIL'")
    @Tag("MaintainedByDiffblue")
    void testToggleControlMode_givenControlModeMobil() {
        // Arrange
        doNothing().when(displayController).updateControlMode(Mockito.<ControlModeModel>any());
        Display display = mock(Display.class);
        when(display.getControlMode()).thenReturn(ControlModeModel.CONTROL_MODE_MOBIL);
        doNothing().when(display).setControlMode(anyInt());
        doNothing().when(display).setDeviceStatus(Mockito.<DeviceStatusModel>any());
        display.setControlMode(1);
        display.setDeviceStatus(new DeviceStatusModel());

        // Act
        displayViewController.toggleControlMode(display);

        // Assert
        verify(displayController).updateControlMode(eq(ControlModeModel.CONTROL_MODE_MOBIL));
        verify(display, atLeast(1)).getControlMode();
        verify(display, atLeast(1)).setControlMode(eq(1));
        verify(display).setDeviceStatus(isA(DeviceStatusModel.class));
    }

    /**
     * Test {@link DisplayViewController#toggleControlMode(Display)}.
     * <ul>
     *   <li>Given {@code CONTROL_MODE_STATIONARY}.</li>
     * </ul>
     * <p>
     * Method under test: {@link DisplayViewController#toggleControlMode(Display)}
     */
    @Test
    @DisplayName("Test toggleControlMode(Display); given 'CONTROL_MODE_STATIONARY'")
    @Tag("MaintainedByDiffblue")
    void testToggleControlMode_givenControlModeStationary() {
        // Arrange
        doNothing().when(displayController).updateControlMode(Mockito.<ControlModeModel>any());
        Display display = mock(Display.class);
        when(display.getControlMode()).thenReturn(ControlModeModel.CONTROL_MODE_STATIONARY);
        doNothing().when(display).setControlMode(anyInt());
        doNothing().when(display).setDeviceStatus(Mockito.<DeviceStatusModel>any());
        display.setControlMode(1);
        display.setDeviceStatus(new DeviceStatusModel());

        // Act
        displayViewController.toggleControlMode(display);

        // Assert
        verify(displayController).updateControlMode(eq(ControlModeModel.CONTROL_MODE_STATIONARY));
        verify(display, atLeast(1)).getControlMode();
        verify(display, atLeast(1)).setControlMode(anyInt());
        verify(display).setDeviceStatus(isA(DeviceStatusModel.class));
    }

    /**
     * Test {@link DisplayViewController#toggleControlMode(Display)}.
     * <ul>
     *   <li>Given minus one.</li>
     *   <li>Then {@link Display} (default constructor) ControlModeValue is one.</li>
     * </ul>
     * <p>
     * Method under test: {@link DisplayViewController#toggleControlMode(Display)}
     */
    @Test
    @DisplayName("Test toggleControlMode(Display); given minus one; then Display (default constructor) ControlModeValue is one")
    @Tag("MaintainedByDiffblue")
    void testToggleControlMode_givenMinusOne_thenDisplayControlModeValueIsOne() {
        // Arrange
        doNothing().when(displayController).updateControlMode(Mockito.<ControlModeModel>any());

        Display display = new Display();
        display.setControlMode(-1);
        DeviceStatusModel deviceStatus = DeviceStatusModel.builder()
                .aklsOn(true)
                .aklsUpdateInProgress(true)
                .dsrcOn(true)
                .wifiApOn(true)
                .build();
        display.setDeviceStatus(deviceStatus);

        // Act
        displayViewController.toggleControlMode(display);

        // Assert
        verify(displayController).updateControlMode(eq(ControlModeModel.CONTROL_MODE_STATIONARY));
        assertEquals(1, display.getControlModeValue());
        assertEquals(ControlModeModel.CONTROL_MODE_STATIONARY, display.getControlMode());
    }

    /**
     * Test {@link DisplayViewController#toggleControlMode(Display)}.
     * <ul>
     *   <li>Then {@link Display} (default constructor) ControlModeValue is two.</li>
     * </ul>
     * <p>
     * Method under test: {@link DisplayViewController#toggleControlMode(Display)}
     */
    @Test
    @DisplayName("Test toggleControlMode(Display); then Display (default constructor) ControlModeValue is two")
    @Tag("MaintainedByDiffblue")
    void testToggleControlMode_thenDisplayControlModeValueIsTwo() {
        // Arrange
        doNothing().when(displayController).updateControlMode(Mockito.<ControlModeModel>any());

        Display display = new Display();
        display.setControlMode(1);
        display.setDeviceStatus(new DeviceStatusModel());

        // Act
        displayViewController.toggleControlMode(display);

        // Assert
        verify(displayController).updateControlMode(eq(ControlModeModel.CONTROL_MODE_MOBIL));
        assertEquals(2, display.getControlModeValue());
        assertEquals(ControlModeModel.CONTROL_MODE_MOBIL, display.getControlMode());
    }

    /**
     * Test {@link DisplayViewController#toggleWifi(Display)}.
     * <ul>
     *   <li>Then calls {@link Display#getDeviceStatus()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link DisplayViewController#toggleWifi(Display)}
     */
    @Test
    @DisplayName("Test toggleWifi(Display); then calls getDeviceStatus()")
    @Tag("MaintainedByDiffblue")
    void testToggleWifi_thenCallsGetDeviceStatus() {
        // Arrange
        doNothing().when(displayController).updateDeviceStatus(Mockito.<DeviceStatusModel>any());
        Display display = mock(Display.class);
        DeviceStatusModel buildResult = DeviceStatusModel.builder()
                .aklsOn(true)
                .aklsUpdateInProgress(true)
                .dsrcOn(true)
                .wifiApOn(true)
                .build();
        when(display.getDeviceStatus()).thenReturn(buildResult);
        doNothing().when(display).setControlMode(anyInt());
        doNothing().when(display).setDeviceStatus(Mockito.<DeviceStatusModel>any());
        display.setControlMode(1);
        display.setDeviceStatus(new DeviceStatusModel());

        // Act
        displayViewController.toggleWifi(display);

        // Assert
        verify(displayController).updateDeviceStatus(isA(DeviceStatusModel.class));
        verify(display, atLeast(1)).getDeviceStatus();
        verify(display).setControlMode(eq(1));
        verify(display, atLeast(1)).setDeviceStatus(Mockito.<DeviceStatusModel>any());
    }

    /**
     * Test {@link DisplayViewController#toggleWifi(Display)}.
     * <ul>
     *   <li>When {@link Display} (default constructor) ControlMode is one.</li>
     *   <li>Then {@link Display} (default constructor) DeviceStatus WifiApOn.</li>
     * </ul>
     * <p>
     * Method under test: {@link DisplayViewController#toggleWifi(Display)}
     */
    @Test
    @DisplayName("Test toggleWifi(Display); when Display (default constructor) ControlMode is one; then Display (default constructor) DeviceStatus WifiApOn")
    @Tag("MaintainedByDiffblue")
    void testToggleWifi_whenDisplayControlModeIsOne_thenDisplayDeviceStatusWifiApOn() {
        // Arrange
        doNothing().when(displayController).updateDeviceStatus(Mockito.<DeviceStatusModel>any());

        Display display = new Display();
        display.setControlMode(1);
        display.setDeviceStatus(new DeviceStatusModel());

        // Act
        displayViewController.toggleWifi(display);

        // Assert
        verify(displayController).updateDeviceStatus(isA(DeviceStatusModel.class));
        assertTrue(display.getDeviceStatus().isWifiApOn());
    }

    /**
     * Test {@link DisplayViewController#toggleAkls(Display)}.
     * <ul>
     *   <li>Then calls {@link Display#getDeviceStatus()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link DisplayViewController#toggleAkls(Display)}
     */
    @Test
    @DisplayName("Test toggleAkls(Display); then calls getDeviceStatus()")
    @Tag("MaintainedByDiffblue")
    void testToggleAkls_thenCallsGetDeviceStatus() {
        // Arrange
        doNothing().when(displayController).updateDeviceStatus(Mockito.<DeviceStatusModel>any());
        Display display = mock(Display.class);
        DeviceStatusModel buildResult = DeviceStatusModel.builder()
                .aklsOn(true)
                .aklsUpdateInProgress(true)
                .dsrcOn(true)
                .wifiApOn(true)
                .build();
        when(display.getDeviceStatus()).thenReturn(buildResult);
        doNothing().when(display).setControlMode(anyInt());
        doNothing().when(display).setDeviceStatus(Mockito.<DeviceStatusModel>any());
        display.setControlMode(1);
        display.setDeviceStatus(new DeviceStatusModel());

        // Act
        displayViewController.toggleAkls(display);

        // Assert
        verify(displayController).updateDeviceStatus(isA(DeviceStatusModel.class));
        verify(display, atLeast(1)).getDeviceStatus();
        verify(display).setControlMode(eq(1));
        verify(display, atLeast(1)).setDeviceStatus(Mockito.<DeviceStatusModel>any());
    }

    /**
     * Test {@link DisplayViewController#toggleAkls(Display)}.
     * <ul>
     *   <li>When {@link Display} (default constructor) ControlMode is one.</li>
     *   <li>Then {@link Display} (default constructor) DeviceStatus AklsOn.</li>
     * </ul>
     * <p>
     * Method under test: {@link DisplayViewController#toggleAkls(Display)}
     */
    @Test
    @DisplayName("Test toggleAkls(Display); when Display (default constructor) ControlMode is one; then Display (default constructor) DeviceStatus AklsOn")
    @Tag("MaintainedByDiffblue")
    void testToggleAkls_whenDisplayControlModeIsOne_thenDisplayDeviceStatusAklsOn() {
        // Arrange
        doNothing().when(displayController).updateDeviceStatus(Mockito.<DeviceStatusModel>any());

        Display display = new Display();
        display.setControlMode(1);
        display.setDeviceStatus(new DeviceStatusModel());

        // Act
        displayViewController.toggleAkls(display);

        // Assert
        verify(displayController).updateDeviceStatus(isA(DeviceStatusModel.class));
        assertTrue(display.getDeviceStatus().isAklsOn());
    }

    /**
     * Test {@link DisplayViewController#toggleDsrc(Display)}.
     * <ul>
     *   <li>Then calls {@link Display#getDeviceStatus()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link DisplayViewController#toggleDsrc(Display)}
     */
    @Test
    @DisplayName("Test toggleDsrc(Display); then calls getDeviceStatus()")
    @Tag("MaintainedByDiffblue")
    void testToggleDsrc_thenCallsGetDeviceStatus() {
        // Arrange
        doNothing().when(displayController).updateDeviceStatus(Mockito.<DeviceStatusModel>any());
        Display display = mock(Display.class);
        DeviceStatusModel buildResult = DeviceStatusModel.builder()
                .aklsOn(true)
                .aklsUpdateInProgress(true)
                .dsrcOn(true)
                .wifiApOn(true)
                .build();
        when(display.getDeviceStatus()).thenReturn(buildResult);
        doNothing().when(display).setControlMode(anyInt());
        doNothing().when(display).setDeviceStatus(Mockito.<DeviceStatusModel>any());
        display.setControlMode(1);
        display.setDeviceStatus(new DeviceStatusModel());

        // Act
        displayViewController.toggleDsrc(display);

        // Assert
        verify(displayController).updateDeviceStatus(isA(DeviceStatusModel.class));
        verify(display, atLeast(1)).getDeviceStatus();
        verify(display).setControlMode(eq(1));
        verify(display, atLeast(1)).setDeviceStatus(Mockito.<DeviceStatusModel>any());
    }

    /**
     * Test {@link DisplayViewController#toggleDsrc(Display)}.
     * <ul>
     *   <li>When {@link Display} (default constructor) ControlMode is one.</li>
     *   <li>Then {@link Display} (default constructor) DeviceStatus DsrcOn.</li>
     * </ul>
     * <p>
     * Method under test: {@link DisplayViewController#toggleDsrc(Display)}
     */
    @Test
    @DisplayName("Test toggleDsrc(Display); when Display (default constructor) ControlMode is one; then Display (default constructor) DeviceStatus DsrcOn")
    @Tag("MaintainedByDiffblue")
    void testToggleDsrc_whenDisplayControlModeIsOne_thenDisplayDeviceStatusDsrcOn() {
        // Arrange
        doNothing().when(displayController).updateDeviceStatus(Mockito.<DeviceStatusModel>any());

        Display display = new Display();
        display.setControlMode(1);
        display.setDeviceStatus(new DeviceStatusModel());

        // Act
        displayViewController.toggleDsrc(display);

        // Assert
        verify(displayController).updateDeviceStatus(isA(DeviceStatusModel.class));
        assertTrue(display.getDeviceStatus().isDsrcOn());
    }
}
