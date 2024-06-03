package com.mycompany.myapp.domain.driver.dto;



public class AvailableDriverDTO {
    private String id;
    private String name;

    public AvailableDriverDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter and setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

