package com.mycompany.myapp.domain.user;

import jakarta.persistence.Embeddable;

import java.time.Instant;
import java.util.Objects;

@Embeddable
public class ResetDate {

    private Instant resetDate;

    public ResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }


    //JPA
    public ResetDate() {
        this.resetDate = null;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResetDate resetDate1 = (ResetDate) o;
        return Objects.equals(resetDate, resetDate1.resetDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(resetDate);
    }

    @Override
    public String toString() {
        return resetDate.toString();
    }
}
