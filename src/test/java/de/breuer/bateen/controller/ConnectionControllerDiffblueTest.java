package de.breuer.bateen.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.breuer.bateen.service.ConnectionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ConnectionController.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ConnectionControllerDiffblueTest {
    @Autowired
    private ConnectionController connectionController;

    @MockitoBean
    private ConnectionService connectionService;

    /**
     * Test {@link ConnectionController#isAlive()}.
     * <ul>
     *   <li>Given {@link ConnectionService} {@link ConnectionService#getAlive()} return {@code false}.</li>
     *   <li>Then return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test: {@link ConnectionController#isAlive()}
     */
    @Test
    @DisplayName("Test isAlive(); given ConnectionService getAlive() return 'false'; then return 'false'")
    @Tag("MaintainedByDiffblue")
    void testIsAlive_givenConnectionServiceGetAliveReturnFalse_thenReturnFalse() throws JsonProcessingException {
        // Arrange
        when(connectionService.getAlive()).thenReturn(false);

        // Act
        boolean actualIsAliveResult = connectionController.isAlive();

        // Assert
        verify(connectionService).getAlive();
        assertFalse(actualIsAliveResult);
    }

    /**
     * Test {@link ConnectionController#isAlive()}.
     * <ul>
     *   <li>Given {@link ConnectionService} {@link ConnectionService#getAlive()} return {@code true}.</li>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test: {@link ConnectionController#isAlive()}
     */
    @Test
    @DisplayName("Test isAlive(); given ConnectionService getAlive() return 'true'; then return 'true'")
    @Tag("MaintainedByDiffblue")
    void testIsAlive_givenConnectionServiceGetAliveReturnTrue_thenReturnTrue() throws JsonProcessingException {
        // Arrange
        when(connectionService.getAlive()).thenReturn(true);

        // Act
        boolean actualIsAliveResult = connectionController.isAlive();

        // Assert
        verify(connectionService).getAlive();
        assertTrue(actualIsAliveResult);
    }
}
