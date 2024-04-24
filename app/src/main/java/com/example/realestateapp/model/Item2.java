package com.example.realestateapp.model;

public class Item2 {
    private String location;
    private String name;
    private String price;
    private String imageUrl;

    private String description;

    private String ownername ;
    private String contactno;
    private String type;

    public Item2(String location, String name, String price, String imageUrl, String description ,String ownername ,String contactno, String type ) {
        this.location = location;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.contactno = contactno;
        this.type = type;
        this.ownername = ownername;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getContactno() {
        return contactno;
    }

    public void setContactno(String location) {
        this.contactno = contactno;
    }


    public String getType() {
        return type;
    }

    public void setType(String location) {
        this.type = type;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String location) {
        this.ownername = ownername;
    }

    public String getDescription() {
        return description;
    }

    public void setDescriptione(String location) {
        this.description = description;
    }
}
