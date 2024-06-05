package com.mycompany.myapp.domain.user;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Password {

    private String password;

    public Password(String password) {
        this.password = isPasswordValid(password);
    }


    //JPA
    public Password() {
        this.password = "";
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(password);
    }

    @Override
    public String toString() {
        return password;
    }

    public String isPasswordValid(String password){
        if(password.length() < 12){
            throw new RuntimeException("Password needs to be at least 12 characters long");
        } else if(password.length() > 128){
            throw new RuntimeException("Password has more than 128 characters");
        } else {
            return password;
        }
    }
}
