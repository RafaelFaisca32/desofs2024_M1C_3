package com.mycompany.myapp.domain.transport;

import com.mycompany.myapp.domain.serviceRequest.ServiceRequestDate;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Embeddable
public class TransportEndTime {
    @Column(name = "end_time")
    private ZonedDateTime endTime;

    public TransportEndTime() {
        this.endTime = ZonedDateTime.now();
    }

    public TransportEndTime(ZonedDateTime date) {
        this.endTime = date;
    }

    public ZonedDateTime value(){
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportEndTime endTime1 = (TransportEndTime) o;
        return endTime.withZoneSameInstant(ZoneOffset.UTC).compareTo(endTime1.endTime.withZoneSameInstant(ZoneOffset.UTC)) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(endTime);
    }

    @Override
    public String toString() {
        return this.endTime != null ? this.endTime.toString() : "null";
    }
}
