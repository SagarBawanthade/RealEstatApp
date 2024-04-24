package com.example.realestateapp.model;

public class Item {

    private String location;
    private String price;
    private String description;
    private String shortdescription;
    private String imageuri;
    private String contactNo;
    private String ownerName;
    private String type;

    public Item() {
    }

    public Item(String location, String price, String shortdescription, String imageuri, String contactNo, String ownerName, String type, String description) {
        this.location = location;
        this.price = price;
        this.shortdescription = shortdescription;
        this.imageuri = imageuri;
        this.contactNo = contactNo;
        this.ownerName = ownerName;
        this.type = type;
        this.description = description;
    }

    public String getImageUri() {
        return imageuri;
    }

    public void setImageUri(String imageUri) {
        this.imageuri = imageUri;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortdescription;
    }

    public void setShortDescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
