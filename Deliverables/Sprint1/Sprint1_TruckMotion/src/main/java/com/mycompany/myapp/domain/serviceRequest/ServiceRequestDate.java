package com.mycompany.myapp.domain.serviceRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Embeddable
public class ServiceRequestDate {
    @Column(name = "date")
    private ZonedDateTime date;

    public ServiceRequestDate() {
        this.date = ZonedDateTime.now();
    }

    public ServiceRequestDate(ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime value(){
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequestDate date1 = (ServiceRequestDate) o;
        return date.withZoneSameInstant(ZoneOffset.UTC).compareTo(date1.date.withZoneSameInstant(ZoneOffset.UTC)) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }

    @Override
    public String toString() {
        return this.date != null ? date.toString() : "null";
    }
}
