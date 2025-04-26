package de.breuer.bateen.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SensorRecordState {
    NA(0), // Nicht verfügbar
    START(1), // Überholvorgang beginnt/ Heck des LKW erkannt (nur
    // sensorRecordId)
    ABORTED(2), // Überholvorgang abgebrochen (nur sensorRecordId)

    END(3), // Überholvorgang beendet/ Front des LKW erkannt (sensorRecordId,
    // ggf.mit Klassifizierung und Abmessungen)
    // (ANPR-Vorab-Teil-Ereignis)
    COMPLETED(5); // Gesamtfahrzeugereignis beendet
    private final int id;

    SensorRecordState(int id) {
        this.id = id;
    }

    public static SensorRecordState getById(int id) {
        return Arrays.stream(SensorRecordState.values()).filter(s -> s.getId() == id).findFirst().orElse(NA);
    }

    public boolean isStart() {
        return this == START;
    }

    public boolean isCompleted() {
        return this == COMPLETED;
    }
}