package com.mycompany.myapp.domain.user;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ImageUrl {

    private String imageUrl;

    public ImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    //JPA
    public ImageUrl() {
        this.imageUrl = "";
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageUrl imageUrl1 = (ImageUrl) o;
        return Objects.equals(imageUrl, imageUrl1.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(imageUrl);
    }

    @Override
    public String toString() {
        return imageUrl;
    }
}
