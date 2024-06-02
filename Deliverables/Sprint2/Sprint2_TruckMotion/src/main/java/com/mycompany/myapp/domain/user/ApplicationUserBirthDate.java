package com.mycompany.myapp.domain.user;

import com.mycompany.myapp.domain.transport.TransportEndTime;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Embeddable
public class ApplicationUserBirthDate {
    private LocalDate birthDate;

    public ApplicationUserBirthDate() {
        this.birthDate = LocalDate.MIN;
    }

    public ApplicationUserBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate value(){
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationUserBirthDate birthDate = (ApplicationUserBirthDate) o;
        return this.birthDate.isEqual(birthDate.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(birthDate);
    }

    @Override
    public String toString() {
        return this.birthDate != null ? this.birthDate.toString() : "null";
    }
}
