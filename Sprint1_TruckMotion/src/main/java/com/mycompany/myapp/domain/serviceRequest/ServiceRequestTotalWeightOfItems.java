package com.mycompany.myapp.domain.serviceRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ServiceRequestTotalWeightOfItems {
    @Column(name = "total_weight_of_items")
    private Float totalWeightOfItems;

    public ServiceRequestTotalWeightOfItems() {
        this.totalWeightOfItems = 0F;
    }

    public ServiceRequestTotalWeightOfItems(Float totalWeightOfItems) {
        if (totalWeightOfItems < 0){
            totalWeightOfItems = 0F;
        }
        this.totalWeightOfItems = totalWeightOfItems;
    }

    public Float value(){
        return totalWeightOfItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequestTotalWeightOfItems totalWeightOfItems1 = (ServiceRequestTotalWeightOfItems) o;
        return totalWeightOfItems.equals(totalWeightOfItems1.totalWeightOfItems);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(totalWeightOfItems);
    }
}
