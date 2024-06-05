package com.mycompany.myapp.domain.user;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ActivationKey {

    private String activationKey;

    public ActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    //JPA
    public ActivationKey() {
        this.activationKey = "";
    }

    public String getActivationKey() {
        return activationKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivationKey activationKey1 = (ActivationKey) o;
        return Objects.equals(activationKey, activationKey1.activationKey);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(activationKey);
    }

    @Override
    public String toString() {
        return activationKey;
    }
}
