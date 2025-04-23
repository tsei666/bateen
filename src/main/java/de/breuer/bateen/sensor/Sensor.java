package de.breuer.bateen.sensor;

public interface Sensor<T> {

    String getSensorRecordId();
    void setSensorRecordId(String sensorRecordId);

    T getData();
    void setData(T data);

    String getTimestamp();
}
