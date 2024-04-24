package com.example.realestateapp.model;

public class FavouriteProperty {

    private String imageUrl;
    private String location;
    private String type;
    private String price;
    private String shortdescription;
    private String description;
    private String contactno;

    private String ownername;

    public FavouriteProperty() {
        // Empty constructor needed for Firestore
    }

    public FavouriteProperty(String imageUrl, String location, String type, String price, String shortdescription, String ownername, String description, String contactno ) {

        this.imageUrl = imageUrl;
        this.location = location;
        this.type = type;
        this.price = price;
        this.shortdescription = shortdescription;
        this.ownername = ownername;
        this.description = description;
        this.contactno = contactno;

    }



    public String getFavImageUrl() {
        return imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getShortDescription() {
        return shortdescription;
    }
    public String getDescription() {
        return description;
    }
    public String getContactno() {
        return contactno;
    }
    public String getOwnername() {
        return ownername;
    }
}
