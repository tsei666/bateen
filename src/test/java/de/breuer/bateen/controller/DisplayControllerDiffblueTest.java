package de.breuer.bateen.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.breuer.bateen.model.ControlModeModel;
import de.breuer.bateen.model.DeviceStatusModel;
import de.breuer.bateen.service.DisplayService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DisplayController.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class DisplayControllerDiffblueTest {
    @Autowired
    private DisplayController displayController;

    @MockitoBean
    private DisplayService displayService;

    /**
     * Test {@link DisplayController#getControlMode()}.
     * <p>
     * Method under test: {@link DisplayController#getControlMode()}
     */
    @Test
    @DisplayName("Test getControlMode()")
    @Tag("MaintainedByDiffblue")
    void testGetControlMode() {
        // Arrange
        when(displayService.getControlMode()).thenReturn(ControlModeModel.CONTROL_MODE_STATIONARY);

        // Act
        ControlModeModel actualControlMode = displayController.getControlMode();

        // Assert
        verify(displayService).getControlMode();
        assertEquals(ControlModeModel.CONTROL_MODE_STATIONARY, actualControlMode);
    }

    /**
     * Test {@link DisplayController#getDeviceStatus()}.
     * <p>
     * Method under test: {@link DisplayController#getDeviceStatus()}
     */
    @Test
    @DisplayName("Test getDeviceStatus()")
    @Tag("MaintainedByDiffblue")
    void testGetDeviceStatus() {
        // Arrange
        DeviceStatusModel buildResult = DeviceStatusModel.builder()
                .aklsOn(true)
                .aklsUpdateInProgress(true)
                .dsrcOn(true)
                .wifiApOn(true)
                .build();
        when(displayService.getDeviceStatus()).thenReturn(buildResult);

        // Act
        DeviceStatusModel actualDeviceStatus = displayController.getDeviceStatus();

        // Assert
        verify(displayService).getDeviceStatus();
        assertTrue(actualDeviceStatus.isAklsOn());
        assertTrue(actualDeviceStatus.isAklsUpdateInProgress());
        assertTrue(actualDeviceStatus.isDsrcOn());
        assertTrue(actualDeviceStatus.isWifiApOn());
    }
}
