package de.breuer.bateen.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigControllerDiffblueTest {
    /**
     * Test {@link ConfigController#getUrl()}.
     * <p>
     * Method under test: {@link ConfigController#getUrl()}
     */
    @Test
    @DisplayName("Test getUrl()")
    @Tag("MaintainedByDiffblue")
    void testGetUrl() {
        // Arrange, Act and Assert
        assertEquals(null, ConfigController.getUrl());
    }

    /**
     * Test {@link ConfigController#getGraphqlUrl()}.
     * <p>
     * Method under test: {@link ConfigController#getGraphqlUrl()}
     */
    @Test
    @DisplayName("Test getGraphqlUrl()")
    @Tag("MaintainedByDiffblue")
    void testGetGraphqlUrl() {
        // Arrange, Act and Assert
        assertEquals(null, ConfigController.getGraphqlUrl());
    }
}
