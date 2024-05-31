package com.mycompany.myapp.domain.truck;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Make {

    @Column(name = "make")
    private String make;

    public Make() {}

    public Make(String make) {
        this.make = make;
    }

    public String value() {
        return make;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Make make1 = (Make) o;
        return Objects.equals(make, make1.make);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(make);
    }

    @Override
    public String toString() {
        return make;
    }
}
