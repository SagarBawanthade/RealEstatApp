/**package com.example.realestateapp.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.realestateapp.MainActivity;
import com.example.realestateapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    private TextView priceTextView, shortDescriptionTextView, descriptionTextView, ownerNameTextView, contactNoTextView ,rentSellTextView, locationTextView;
    private ImageView propertyImageView;

    private Button callButton , favButton ;

    private ImageButton backButton;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        // Retrieve passed data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String location = intent.getStringExtra("location");
            String price = intent.getStringExtra("price");
            String shortdescription = intent.getStringExtra("shortdescription");
            String imageuri = intent.getStringExtra("imageuri");
            String description = intent.getStringExtra("description");
            String contactno = intent.getStringExtra("contactno");
            String type = intent.getStringExtra("type");
            String ownername = intent.getStringExtra("ownername");


            // Set data to TextViews
            priceTextView.setText(price);
            shortDescriptionTextView.setText(shortdescription);
            descriptionTextView.setText(description);
            ownerNameTextView.setText(ownername);
            contactNoTextView.setText(contactno);
            rentSellTextView.setText(type);
            locationTextView.setText(location);


            // Load image using Glide
            Glide.with(this)
                    .load(imageuri)
                    .into(propertyImageView);


            // Initialize views
            priceTextView = findViewById(R.id.price);
            shortDescriptionTextView = findViewById(R.id.short_description);
            descriptionTextView = findViewById(R.id.description);
            ownerNameTextView = findViewById(R.id.owner_name);
            contactNoTextView = findViewById(R.id.contact_no);
            propertyImageView = findViewById(R.id.imageView);
            callButton = findViewById(R.id.callButton);
            rentSellTextView = findViewById(R.id.rent_sell);
            favButton = findViewById(R.id.add_to_fav);
            locationTextView = findViewById(R.id.location);
            backButton = findViewById(R.id.back_button);


            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailsActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });


            // Retrieve property details from Firestore
        /*FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Properties")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String price = document.getString("price");
                            String shortdescription = document.getString("shortdescription");
                            String description = document.getString("description");
                            String ownername = document.getString("ownername");
                            String imageuri = document.getString("imageuri");
                            String contactno = document.getString("contactno");
                            String type = document.getString("type");
                            String location = document.getString("location");



                           /* // Set data to TextViews
                            priceTextView.setText(price);
                            shortDescriptionTextView.setText(shortdescription);
                            descriptionTextView.setText(description);
                            ownerNameTextView.setText(ownername);
                            contactNoTextView.setText(contactno);
                            rentSellTextView.setText(type);
                            locationTextView.setText(location);


                            // Load image using Glide
                            Glide.with(this)
                                    .load(imageuri)
                                    .into(propertyImageView);

            // Set onClickListener for callButton
            callButton.setOnClickListener(v -> {
                // Directly call the owner's phone number
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contactno));
                startActivity(intent);
            });

            // Set onClickListener for favButton
            favButton.setOnClickListener(v -> {

                //addToFavorites(document); // Pass the DocumentSnapshot representing the property to the addToFavorites method
            });
        }

                       /** }
                    }
                    else {
                        Toast.makeText(DetailsActivity.this, "Data Failed to fetch from database",Toast.LENGTH_SHORT).show();
                    }
                });**/



   /* private void addToFavorites(DocumentSnapshot propertySnapshot) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Get all property data from the DocumentSnapshot
        Map<String, Object> propertyData = propertySnapshot.getData();
        if (propertyData != null) {
            // Add the property data to the "Favorites" collection
            db.collection("Favorites").document(propertySnapshot.getId())
                    .set(propertyData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(DetailsActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("DetailsActivity", "Error adding to favorites", e);
                        Toast.makeText(DetailsActivity.this, "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(DetailsActivity.this, "Property data is null", Toast.LENGTH_SHORT).show();
        }
    }
}
**/

package com.example.realestateapp.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.realestateapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    private TextView priceTextView, shortDescriptionTextView, descriptionTextView, ownerNameTextView, contactNoTextView ,rentSellTextView, locationTextView;
    private ImageView propertyImageView;
    private Button callButton, bookProperty  ;
    private ImageButton backButton, favButton;

    private FirebaseFirestore db;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        priceTextView = findViewById(R.id.price);
        shortDescriptionTextView = findViewById(R.id.short_description);
        descriptionTextView = findViewById(R.id.description);
        ownerNameTextView = findViewById(R.id.owner_name);
        contactNoTextView = findViewById(R.id.contact_no);
        propertyImageView = findViewById(R.id.imageView);
        callButton = findViewById(R.id.callButton);
        rentSellTextView = findViewById(R.id.rent_sell);
        favButton = findViewById(R.id.add_to_fav);
        locationTextView = findViewById(R.id.location);
        backButton = findViewById(R.id.back_button);
        bookProperty = findViewById(R.id.book_property);





        bookProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject , content, to_email;
                subject = "Booking Inquiry for Property at " + locationTextView.getText().toString();
                content = "I am interested in booking the property.Please provide me with more details to book this Property.  Thank you." ;
                to_email = "sagar.bawanthade2004@example.com";
                sendEmail(subject , content, to_email);
            }
        });



        // Retrieve passed data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String location = intent.getStringExtra("location");
            String price = intent.getStringExtra("price");
            String shortdescription = intent.getStringExtra("shortdescription");
            String imageuri = intent.getStringExtra("imageuri");
            String description = intent.getStringExtra("description");
            String contactno = intent.getStringExtra("contactno");
            String type = intent.getStringExtra("type");
            String ownername = intent.getStringExtra("ownername");




            // Set data to TextViews
            priceTextView.setText(price);
            shortDescriptionTextView.setText(shortdescription);
            descriptionTextView.setText(description);
            ownerNameTextView.setText(ownername);
            contactNoTextView.setText(contactno);
            rentSellTextView.setText(type);
            locationTextView.setText(location);

            // Load image using Glide
            Glide.with(this)
                    .load(imageuri)
                    .into(propertyImageView);

            // Set onClickListener for callButton
            callButton.setOnClickListener(v -> {
                // Directly call the owner's phone number
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + contactno));
                startActivity(dialIntent);
            });

            // Set onClickListener for backButton
            backButton.setOnClickListener(v -> {
                // Navigate back to the previous activity
                finish();
            });

            // Set onClickListener for favButton
            favButton.setOnClickListener(v -> {
                // Add the property to favorites
                addToFavorites(location, price, shortdescription, imageuri, description, contactno, type, ownername);
            });
        } else {
            Toast.makeText(this, "Failed to retrieve property details", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no data is passed
        }
    }

    private void sendEmail(String subject, String content, String to_email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to_email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("message/rfc822");
        startActivity(intent.createChooser(intent, "Choose email Client"));
    }


    private void addToFavorites(String location, String price, String shortdescription, String imageuri, String description, String contactno, String type, String ownername) {
        // Generate a unique document ID based on property details

        db.collection("Favorites")
                .whereEqualTo("location", location)
                .whereEqualTo("price", price)
                .whereEqualTo("shortdescription", shortdescription)
                .whereEqualTo("imageuri", imageuri)
                .whereEqualTo("description", description)
                .whereEqualTo("contactno", contactno)
                .whereEqualTo("type", type)
                .whereEqualTo("ownername", ownername)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // If property doesn't exist in favorites, add it
                        if (task.getResult().isEmpty()) {

                            String documentId = contactno;
                            // Create a HashMap to store property data
                            Map<String, Object> propertyData = new HashMap<>();
                            propertyData.put("location", location);
                            propertyData.put("price", price);
                            propertyData.put("shortdescription", shortdescription);
                            propertyData.put("imageuri", imageuri);
                            propertyData.put("description", description);
                            propertyData.put("contactno", contactno);
                            propertyData.put("type", type);
                            propertyData.put("ownername", ownername);

                            // Add the property data to the "Favorites" collection with the custom document ID
                            db.collection("Favorites")
                                    .document(documentId)
                                    .set(propertyData)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            // Property already exists in favorites
                            Toast.makeText(this, "Property already added to favorites", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error getting favorites data
                        Toast.makeText(this, "Error checking favorites", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private String generateDocumentId(String location, String price, String shortdescription, String imageuri, String description, String contactno, String type, String ownername) {
        // Concatenate property details to create a unique string
        String details = location + price + shortdescription + imageuri + description + contactno + type + ownername;
        // Use a hash function to generate a unique hash for the string
        return Integer.toHexString(details.hashCode());
    }
}
