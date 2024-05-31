package com.mycompany.myapp.domain.serviceRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ServiceRequestItems {
    @Column(name = "items")
    private String items;

    public ServiceRequestItems() {
        this.items = "";
    }

    public ServiceRequestItems(String items) {
        this.items = items;
    }

    public String value(){
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequestItems items1 = (ServiceRequestItems) o;
        return items.equals(items1.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(items);
    }

    @Override
    public String toString() {
        return items;
    }
}
