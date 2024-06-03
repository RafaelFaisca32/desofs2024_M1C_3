package com.mycompany.myapp.domain.user;

import java.util.Objects;

public class Login {

    private String login;

    public Login(String login) {
        this.login = login;
    }


    //JPA
    public Login() {
        this.login = "";
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login1 = (Login) o;
        return Objects.equals(login, login1.login);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(login);
    }

    @Override
    public String toString() {
        return login;
    }
}
