package com.mycompany.myapp.domain.user;

import java.util.Objects;

public class Activated {

    private Boolean activated;

    public Activated(Boolean activated) {
        this.activated = activated;
    }


    //JPA
    public Activated() {
        this.activated = false;
    }

    public Boolean getActivated() {
        return activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activated activated1 = (Activated) o;
        return Objects.equals(activated, activated1.activated);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(activated);
    }

    @Override
    public String toString() {
        return activated.toString();
    }
}
