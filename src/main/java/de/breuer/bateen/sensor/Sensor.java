package de.breuer.bateen.sensor;

public interface Sensor<T> {

    String getSensorRecordId();
    void setSensorRecordId(String sensorRecordId);

    T getData();  // z. B. für Wrapper oder UI
    void setData(T data); // z. B. aus einem Formular heraus

    String getTimestamp(); // ggf. zentral über default
}
