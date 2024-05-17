package com.mycompany.myapp.domain.serviceRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ServiceStatusObservations {
    @Column(name = "observations")
    private String observations;

    public ServiceStatusObservations() {
        observations = "";
    }

    public ServiceStatusObservations(String observations) {
        this.observations = observations;
    }

    public String value(){
        return observations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceStatusObservations observations1 = (ServiceStatusObservations) o;
        return observations.equals(observations1.observations);
    }

    @Override
    public String toString() {
        return observations;
    }
}
