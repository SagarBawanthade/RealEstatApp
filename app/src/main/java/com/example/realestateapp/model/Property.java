package com.example.realestateapp.model;

public class Property {
    private String imageResId;
    private String title;

    private String category;

    public Property(String img, String title, String category) {
        this.imageResId = img;
        this.title  = title;
        this.category = category;
    }

    public String getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }
}
