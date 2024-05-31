package com.mycompany.myapp.domain.transport;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Embeddable
public class TransportStartTime {
    @Column(name = "start_time")
    private ZonedDateTime startTime;

    public TransportStartTime() {
        this.startTime = ZonedDateTime.now();
    }

    public TransportStartTime(ZonedDateTime date) {
        this.startTime = date;
    }

    public ZonedDateTime value(){
        return startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportStartTime startTime1 = (TransportStartTime) o;
        return startTime.withZoneSameInstant(ZoneOffset.UTC).compareTo(startTime1.startTime.withZoneSameInstant(ZoneOffset.UTC)) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(startTime);
    }

    @Override
    public String toString() {
        return this.startTime != null ? this.startTime.toString() : "null";
    }
}
