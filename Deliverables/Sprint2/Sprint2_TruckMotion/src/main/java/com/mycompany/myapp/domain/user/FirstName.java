package com.mycompany.myapp.domain.user;

import java.util.Objects;

public class FirstName {

    private String firstName;

    public FirstName(String firstName) {
        this.firstName = firstName;
    }


    //JPA
    public FirstName() {
        this.firstName = "";
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstName firstName1 = (FirstName) o;
        return Objects.equals(firstName, firstName1.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstName);
    }

    @Override
    public String toString() {
        return firstName;
    }
}
