package com.mycompany.myapp.domain.serviceRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ServiceRequestName {
    @Column(name = "service_name")
    private String name;

    public ServiceRequestName() {
        this.name = "";
    }

    public ServiceRequestName(String name) {
        this.name = name;
    }

    public String value(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequestName name1 = (ServiceRequestName) o;
        return name.equals(name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
