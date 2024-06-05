package com.mycompany.myapp.domain.user;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class LastName {

    private String lastName;

    public LastName(String firstName) {
        this.lastName = firstName;
    }


    //JPA
    public LastName() {
        this.lastName = "";
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastName lastName1 = (LastName) o;
        return Objects.equals(lastName, lastName1.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lastName);
    }

    @Override
    public String toString() {
        return lastName;
    }
}
