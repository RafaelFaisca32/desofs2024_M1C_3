package com.mycompany.myapp.domain.user;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ResetKey {

    private String resetKey;

    public ResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    //JPA
    public ResetKey() {
        this.resetKey = "";
    }

    public String getResetKey() {
        return resetKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResetKey resetKey1 = (ResetKey) o;
        return Objects.equals(resetKey, resetKey1.resetKey);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(resetKey);
    }

    @Override
    public String toString() {
        return resetKey;
    }
}
