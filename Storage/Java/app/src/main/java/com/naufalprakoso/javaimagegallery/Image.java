package com.naufalprakoso.javaimagegallery;

public class Image {
    private String name, image;

    Image(){

    }

    public Image(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

}
