package com.mycompany.myapp.domain.serviceRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.ZonedDateTime;

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
        return date.equals(date1.date);
    }
}
