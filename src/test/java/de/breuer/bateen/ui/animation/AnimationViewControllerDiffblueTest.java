package de.breuer.bateen.ui.animation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.breuer.bateen.model.SensorRecordState;
import de.breuer.bateen.model.akls.SensorDataModel;
import de.breuer.bateen.service.DsrcSensorService;
import de.breuer.bateen.service.IrSensorService;
import de.breuer.bateen.service.impl.SensorServiceImpl;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@ExtendWith(MockitoExtension.class)
class AnimationViewControllerDiffblueTest {
    @InjectMocks
    private AnimationViewController animationViewController;

    /**
     * Test {@link AnimationViewController#sendSensorDataForStop(int)}.
     * <p>
     * Method under test: {@link AnimationViewController#sendSensorDataForStop(int)}
     */
    @Test
    @DisplayName("Test sendSensorDataForStop(int)")
    @Tag("MaintainedByDiffblue")
    void testSendSensorDataForStop() {
        // Arrange
        SensorServiceImpl sensorService = mock(SensorServiceImpl.class);
        SensorDataModel buildResult = SensorDataModel.builder()
                .anprCountry("GB")
                .anprCountryConfidence(1)
                .anprNumberPlate("42")
                .anprNumberPlateConfidence(1)
                .anprOverviewPicture("Anpr Overview Picture")
                .anprPlatePicture("Anpr Plate Picture")
                .anprSideviewPicture("Anpr Sideview Picture")
                .details("Details")
                .incompleteReason(null)
                .recordTimeStamp("Record Time Stamp")
                .sensorRecordId("42")
                .sensorRecordIdRef(null)
                .sensorRecordState(1)
                .unNumber("42")
                .vehicleAxleCount(3)
                .vehicleHeight(1)
                .vehicleLength(3)
                .vehicleLengthConfidence(1)
                .vehicleSpeed(1)
                .vehicleType(1)
                .vehicleWeightClass(3)
                .vehicleWidth(1)
                .build();
        when(sensorService.setSensorDataModel()).thenReturn(buildResult);
        doNothing().when(sensorService).postSensorData(Mockito.<SensorDataModel>any(), Mockito.<SensorRecordState>any());
        DsrcSensorService dsrcSensorService = new DsrcSensorService(mock(Builder.class));
        AnimationViewController animationViewController = new AnimationViewController(sensorService, dsrcSensorService,
                new IrSensorService(mock(Builder.class)));

        // Act
        animationViewController.sendSensorDataForStop(1);

        // Assert
        verify(sensorService).postSensorData(isA(SensorDataModel.class), eq(SensorRecordState.START));
        verify(sensorService).setSensorDataModel();
        List<String> logMessages = animationViewController.getLogMessages();
        assertEquals(2, logMessages.size());
        assertEquals("Sending SensorData with state 'START': SensorDataModel(sensorRecordId=42, sensorRecordState=1,"
                     + " isComplete=false, incompleteReason=null, recordTimeStamp=Record Time Stamp, anprNumberPlate=42,"
                     + " anprNumberPlateConfidence=1, anprCountry=GB, anprCountryConfidence=1, anprPlatePicture=Anpr Plate"
                     + " Picture, anprOverviewPicture=Anpr Overview Picture, anprSideviewPicture=Anpr Sideview Picture,"
                     + " vehicleWeightClass=3, vehicleType=1, vehicleHeight=1, vehicleWidth=1, vehicleLength=3, vehicleLengt"
                     + "hConfidence=1, vehicleAxleCount=3, vehicleSpeed=1, details=Details, unNumber=42, hasWasteSign=null,"
                     + " isExtraWideVehicle=null, sensorRecordIdRef=null)", logMessages.get(0));
        assertEquals("✅ SensorData with state 'START' sent successfully (Stop 1)", logMessages.get(1));
    }

    /**
     * Test {@link AnimationViewController#sendSensorDataForStop(int)}.
     * <ul>
     *   <li>Then {@link AnimationViewController} LogMessages size is one.</li>
     * </ul>
     * <p>
     * Method under test: {@link AnimationViewController#sendSensorDataForStop(int)}
     */
    @Test
    @DisplayName("Test sendSensorDataForStop(int); then AnimationViewController LogMessages size is one")
    @Tag("MaintainedByDiffblue")
    void testSendSensorDataForStop_thenAnimationViewControllerLogMessagesSizeIsOne() {
        // Arrange and Act
        animationViewController.sendSensorDataForStop(10);

        // Assert
        List<String> logMessages = animationViewController.getLogMessages();
        assertEquals(1, logMessages.size());
        assertEquals("ℹ️ No SensorRecordState sent for stop 10.", logMessages.get(0));
    }

    /**
     * Test {@link AnimationViewController#sendCompleted()}.
     * <p>
     * Method under test: {@link AnimationViewController#sendCompleted()}
     */
    @Test
    @DisplayName("Test sendCompleted()")
    @Tag("MaintainedByDiffblue")
    void testSendCompleted() {
        // Arrange
        SensorServiceImpl sensorService = mock(SensorServiceImpl.class);
        SensorDataModel buildResult = SensorDataModel.builder()
                .anprCountry("GB")
                .anprCountryConfidence(1)
                .anprNumberPlate("42")
                .anprNumberPlateConfidence(1)
                .anprOverviewPicture("Anpr Overview Picture")
                .anprPlatePicture("Anpr Plate Picture")
                .anprSideviewPicture("Anpr Sideview Picture")
                .details("Details")
                .incompleteReason(null)
                .recordTimeStamp("Record Time Stamp")
                .sensorRecordId("42")
                .sensorRecordIdRef(null)
                .sensorRecordState(1)
                .unNumber("42")
                .vehicleAxleCount(3)
                .vehicleHeight(1)
                .vehicleLength(3)
                .vehicleLengthConfidence(1)
                .vehicleSpeed(1)
                .vehicleType(1)
                .vehicleWeightClass(3)
                .vehicleWidth(1)
                .build();
        when(sensorService.setSensorDataModel()).thenReturn(buildResult);
        doNothing().when(sensorService).postSensorData(Mockito.<SensorDataModel>any(), Mockito.<SensorRecordState>any());
        DsrcSensorService dsrcSensorService = new DsrcSensorService(mock(Builder.class));
        AnimationViewController animationViewController = new AnimationViewController(sensorService, dsrcSensorService,
                new IrSensorService(mock(Builder.class)));

        // Act
        animationViewController.sendCompleted();

        // Assert
        verify(sensorService).postSensorData(isA(SensorDataModel.class), eq(SensorRecordState.COMPLETED));
        verify(sensorService).setSensorDataModel();
        List<String> logMessages = animationViewController.getLogMessages();
        assertEquals(2, logMessages.size());
        assertEquals("Sending SensorData with state 'COMPLETED': SensorDataModel(sensorRecordId=42, sensorRecordState=1,"
                     + " isComplete=false, incompleteReason=null, recordTimeStamp=Record Time Stamp, anprNumberPlate=42,"
                     + " anprNumberPlateConfidence=1, anprCountry=GB, anprCountryConfidence=1, anprPlatePicture=Anpr Plate"
                     + " Picture, anprOverviewPicture=Anpr Overview Picture, anprSideviewPicture=Anpr Sideview Picture,"
                     + " vehicleWeightClass=3, vehicleType=1, vehicleHeight=1, vehicleWidth=1, vehicleLength=3, vehicleLengt"
                     + "hConfidence=1, vehicleAxleCount=3, vehicleSpeed=1, details=Details, unNumber=42, hasWasteSign=null,"
                     + " isExtraWideVehicle=null, sensorRecordIdRef=null)", logMessages.get(0));
        assertEquals("✅ SensorData with state 'COMPLETED' sent successfully", logMessages.get(1));
    }
}
