package com.mycompany.myapp.domain.location;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import javax.naming.Name;
import java.util.Objects;

@Embeddable
public class GeographicalCoordinates {

    @Column(name = "coord_x")
    private Float coordX;
    @Column(name = "coord_y")
    private Float coordY;
    @Column(name = "coord_z")
    private Float coordZ;

    public GeographicalCoordinates(Float coordX, Float coordY, Float coordZ) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = coordZ;
    }

    public GeographicalCoordinates() {
        this.coordX = 0F;
        this.coordY = 0F;
        this.coordZ = 0F;
    }

    public Float xValue() {
        return coordX;
    }

    public Float yValue() {
        return coordY;
    }

    public Float zValue() {
        return coordZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicalCoordinates that = (GeographicalCoordinates) o;
        return Objects.equals(coordX, that.coordX) && Objects.equals(coordY, that.coordY) && Objects.equals(coordZ, that.coordZ);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordX, coordY, coordZ);
    }
}
