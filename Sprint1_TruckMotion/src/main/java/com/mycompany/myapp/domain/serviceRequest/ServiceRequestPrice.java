package com.mycompany.myapp.domain.serviceRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ServiceRequestPrice {
    @Column(name = "price")
    private Float price;

    public ServiceRequestPrice() {
        this.price = 0F;
    }

    public ServiceRequestPrice(Float price) {
        if (price < 0){
            price = 0F;
        }
        this.price = price;
    }

    public Float value(){
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequestPrice price1 = (ServiceRequestPrice) o;
        return price.equals(price1.price);
    }
}
