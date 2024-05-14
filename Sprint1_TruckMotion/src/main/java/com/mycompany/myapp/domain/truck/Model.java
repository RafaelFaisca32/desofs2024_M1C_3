package com.mycompany.myapp.domain.truck;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Model {

    @Column(name = "model")
    private String model;

    public Model() {}

    public Model(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model1 = (Model) o;
        return Objects.equals(model, model1.model);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(model);
    }
}
