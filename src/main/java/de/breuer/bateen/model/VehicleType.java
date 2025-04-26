package de.breuer.bateen.model;

import lombok.Getter;

@Getter
public enum VehicleType {
    NONE(0),
    CAR(1),
    LKW(2),
    VAN(3),
    BUS(4),
    CAR_TRUCK(5),
    TANK_TRUCK(6),
    TRUCK(7);

    private final int id;

    private VehicleType(int i) {
        this.id = i;
    }
    @Override
    public String toString() {
        return this.name() + " (" + id + ")";
    }
}