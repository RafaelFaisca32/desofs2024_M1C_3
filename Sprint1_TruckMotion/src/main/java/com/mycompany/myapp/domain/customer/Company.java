package com.mycompany.myapp.domain.customer;

import jakarta.persistence.Embeddable;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company1 = (Company) o;
        return Objects.equals(company, company1.company);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(company);
    }

    @Override
    public String toString() {
        return company;
    }
}
