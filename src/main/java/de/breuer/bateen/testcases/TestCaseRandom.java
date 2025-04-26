package de.breuer.bateen.testcases;

import de.breuer.bateen.controller.ConfigController;
import de.breuer.bateen.model.ir.IrImageModel;
import de.breuer.bateen.model.VehicleType;
import de.breuer.bateen.sensor.AklsSensor;
import de.breuer.bateen.sensor.DsrcSensor;
import de.breuer.bateen.sensor.IrCameraSensor;
import de.breuer.bateen.sensor.VehicleSensor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TestCaseRandom implements TestCaseGenerator {

    private static final String LETTERS_NUMBERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ";
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String BASE64_EXAMPLE = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8Xw8AAgMBgR0kd7kAAAAASUVORK5CYII=";

    private static final Random random = new Random();

    @Override
    public void generateTestCase() {
        String sensorRecordId = String.valueOf(UUID.randomUUID());
        String recordTimeStamp = String.valueOf(Timestamp.from(Instant.now()));
        String numberPlate = randomNumberPlate();
        String countryCode = randomCountryCode();
        String dsrcVehicleRegistrationPlate = String.format("%s.%s", countryCode, numberPlate);

        AklsSensor akls = new AklsSensor();
        akls.setSensorRecordId(sensorRecordId);
        akls.setAnprNumberPlate(numberPlate);
        akls.setAnprNumberPlateConfidence(randomConfidence());
        akls.setAnprCountry(countryCode);
        akls.setAnprCountryConfidence(randomConfidence());
        akls.setAnprPlatePicture(randomBase64Image());
        akls.setAnprOverviewPicture(randomBase64Image());
        akls.setAnprSideviewPicture(randomBase64Image());

        VehicleSensor vehicle = new VehicleSensor();
        vehicle.setSensorRecordId(sensorRecordId);
        vehicle.setVehicleWeightClass(random.nextInt(1001));
        VehicleType[] types = {VehicleType.LKW, VehicleType.VAN, VehicleType.BUS, VehicleType.CAR_TRUCK, VehicleType.TANK_TRUCK, VehicleType.TRUCK};
        vehicle.setVehicleType(types[random.nextInt(types.length)]);
        vehicle.setVehicleHeight(random.nextInt(401) + 100);
        vehicle.setVehicleWidth(random.nextInt(201) + 150);
        vehicle.setVehicleLength(random.nextInt(601) + 400);
        vehicle.setVehicleLengthConfidence(random.nextInt(101));
        vehicle.setVehicleAxleCount(random.nextInt(11));
        vehicle.setVehicleSpeed(random.nextInt(301));
        vehicle.setDetails("NoDetails");
        vehicle.setUnNumber(String.valueOf(1000 + random.nextInt(9000)));
        vehicle.setHasWasteSign(random.nextBoolean());
        vehicle.setExtraWideVehicle(random.nextBoolean());

        DsrcSensor dsrc = new DsrcSensor();
        dsrc.setSensorRecordId(sensorRecordId);
        dsrc.setRecordTimeStamp(recordTimeStamp);
        dsrc.setVehicleRegistrationPlate(dsrcVehicleRegistrationPlate);
        dsrc.setSpeedingEvent(random.nextBoolean());
        dsrc.setDrivingWithoutValidCard(random.nextBoolean());
        dsrc.setDriverCard(random.nextBoolean());
        dsrc.setCardInsertion(random.nextBoolean());
        dsrc.setMotionDataError(random.nextBoolean());
        dsrc.setVehicleMotionConflict(random.nextBoolean());
        dsrc.setSecondDriverCard(random.nextBoolean());
        dsrc.setCurrentActivityDriving(random.nextBoolean());
        dsrc.setLastSessionClosed(random.nextBoolean());
        dsrc.setPowerSupplyInterruption(random.nextBoolean());
        dsrc.setSensorFault(random.nextInt(256));
        dsrc.setTimeAdjustment(random.nextInt(1_000_001));
        dsrc.setLatestBreachAttempt(random.nextInt(1_000_001));
        dsrc.setLastCalibrationData(1_600_000_000 + random.nextInt(1_000_000));
        dsrc.setPrevCalibrationData(1_500_000_000 + random.nextInt(1_000_000));
        dsrc.setDateTachoConnected(1_500_000_000 + random.nextInt(1_000_000));
        dsrc.setCurrentSpeed(random.nextInt(131));
        dsrc.setTimeStamp((int) (Instant.now().getEpochSecond()));
        dsrc.setLatestAuthenticatedPosition(random.nextBoolean() ? random.nextLong(100_000_000L) : -1L);
        dsrc.setContinuousDrivingTime(random.nextInt(500));
        dsrc.setDailyDrivingTimeShift(random.nextInt(100));
        dsrc.setDailyDrivingTimeWeek(random.nextInt(100));
        dsrc.setWeeklyDrivingTime(random.nextInt(100));
        dsrc.setFortnightlyDrivingTime(random.nextInt(100));

        IrCameraSensor ir = new IrCameraSensor();
        ir.setSensorRecordId(sensorRecordId);

// Erzeuge 1–5 zufällige IR-Bilder
        List<IrImageModel> randomIrImages = new ArrayList<>();
        int imageCount = random.nextInt(5) + 1; // mindestens 1, maximal 5 Bilder

        for (int i = 0; i < imageCount; i++) {
            IrImageModel irImage = new IrImageModel();
            irImage.setType(random.nextBoolean() ? "thermal" : "infrared");
            irImage.setDescription("Random IR Image " + (i + 1));
            irImage.setTimestamp((int) (System.currentTimeMillis() / 1000L)); // aktueller Zeitstempel (Sekunden)
            irImage.setCaseId(UUID.randomUUID().toString());
            irImage.setLabeledData(TestCaseRandom.randomBase64Image());
            irImage.setRawData(TestCaseRandom.randomBase64Image());
            randomIrImages.add(irImage);
        }
        ir.setIrImages(randomIrImages);

// Max-Temperatur-Werte: 1–5 Werte zwischen 20°C und 100°C
        List<Integer> maxTemps = new ArrayList<>();
        for (int i = 0; i < imageCount; i++) {
            maxTemps.add(20 + random.nextInt(81)); // 20–100
        }
        ir.setIrMaxTempValues(maxTemps);

// Mittel-Temperatur-Werte: 1–5 Werte zwischen 10°C und 80°C
        List<Integer> meanTemps = new ArrayList<>();
        for (int i = 0; i < imageCount; i++) {
            meanTemps.add(10 + random.nextInt(71)); // 10–80
        }
        ir.setIrMeanTempValues(meanTemps);

        ConfigController.setAklsSensor(akls);
        ConfigController.setVehicleSensor(vehicle);
        ConfigController.setDsrcSensor(dsrc);
        ConfigController.setIrCameraSensor(ir);
    }

    public static String randomNumberPlate() {
        int length = random.nextInt(7) + 4; // 4 bis 10 Zeichen
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(LETTERS_NUMBERS.charAt(random.nextInt(LETTERS_NUMBERS.length())));
        }
        return sb.toString().trim(); // Falls Leerzeichen am Ende entstehen
    }

    public static int randomConfidence() {
        return random.nextInt(101); // 0-100 inklusive
    }

    public static String randomCountryCode() {
        StringBuilder sb = new StringBuilder(2);
        for (int i = 0; i < 2; i++) {
            sb.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        }
        return sb.toString();
    }

    public static String randomBase64Image() {
        // Du kannst hier beliebig viele Beispiel-Base64 Strings hinterlegen
        return BASE64_EXAMPLE;
    }
}
