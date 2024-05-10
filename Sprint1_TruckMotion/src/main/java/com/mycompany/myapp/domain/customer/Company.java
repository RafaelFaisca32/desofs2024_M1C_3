package com.mycompany.myapp.domain.customer;

import jakarta.persistence.Embeddable;

@Embeddable
public class Company {

    private String company;
    public Company(String company) {
        this.company = company;
    }

    public String value(){
        return company;
    }

    //Needed for JPA
    protected Company() {
    }
}
