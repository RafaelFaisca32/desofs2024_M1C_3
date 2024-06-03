package com.mycompany.myapp.domain.user;

import java.util.Objects;

public class LangKey {

    private String langKey;

    public LangKey(String langKey) {
        this.langKey = langKey;
    }

    //JPA
    public LangKey() {
        this.langKey = "";
    }

    public String getLangKey() {
        return langKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LangKey langKey1 = (LangKey) o;
        return Objects.equals(langKey, langKey1.langKey);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(langKey);
    }

    @Override
    public String toString() {
        return langKey;
    }
}
