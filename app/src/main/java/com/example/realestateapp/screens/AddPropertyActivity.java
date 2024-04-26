package com.example.realestateapp.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.realestateapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddPropertyActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText locationEditText, typeEditText, descriptionEditText, shortDescriptionEditText,
            ownerNameEditText, contactNoEditText, priceEditText,  categoryEditText;
    private ImageView imageViewUploaded;
    private Button buttonUploadImage, buttonSubmit;

    private ImageButton back_button;

    private Uri imageUri;
    private StorageReference storageReference;
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_property_listing);

        locationEditText = findViewById(R.id.property_location);
        typeEditText = findViewById(R.id.property_type);
        descriptionEditText = findViewById(R.id.property_description);
        shortDescriptionEditText = findViewById(R.id.property_shortdescription);
        ownerNameEditText = findViewById(R.id.property_ownername);
        contactNoEditText = findViewById(R.id.property_contactno);
        priceEditText = findViewById(R.id.property_price);
        categoryEditText = findViewById(R.id.property_category);
        imageViewUploaded = findViewById(R.id.imageViewUploaded);
        buttonUploadImage = findViewById(R.id.buttonUploadImage);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        back_button = findViewById(R.id.back_button);

        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        // Set onClickListener for backButton
        back_button.setOnClickListener(v -> {
            // Navigate back to the previous activity
            finish();
        });



        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open image gallery
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditText fields
                String location = locationEditText.getText().toString();
                String type = typeEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String shortDescription = shortDescriptionEditText.getText().toString();
                String ownerName = ownerNameEditText.getText().toString();
                String contactNo = contactNoEditText.getText().toString();
                String price = priceEditText.getText().toString();
                String imageuri = imageViewUploaded.toString();
                String category = categoryEditText.getText().toString();

                // Create a Map to store the property data
                Map<String, Object> propertyData = new HashMap<>();
                propertyData.put("location", location);
                propertyData.put("type", type);
                propertyData.put("description", description);
                propertyData.put("shortdescription", shortDescription);
                propertyData.put("ownername", ownerName);
                propertyData.put("contactno", contactNo);
                propertyData.put("price", price);
                propertyData.put("imageuri", imageuri);
                propertyData.put("category", category);

                /* Add data to Firestore
                db.collection("Properties")
                        .add(propertyData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Data added successfully
                                Toast.makeText(AddPropertyActivity.this, "Property added successfully", Toast.LENGTH_SHORT).show();
                                // Clear the form after successful submission
                                clearForm();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to add data
                                Toast.makeText(AddPropertyActivity.this, "Failed to add property", Toast.LENGTH_SHORT).show();
                            }
                        });*/

                // Upload image to Firebase Storage and get image URL
                if (imageUri != null) {
                    StorageReference imageRef = storageReference.child("images/" + System.currentTimeMillis() + ".jpg");
                    imageRef.putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Image uploaded successfully
                                    Toast.makeText(AddPropertyActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                                    // Get the image URL
                                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Add the image URL to the property data
                                            propertyData.put("imageuri", uri.toString());
                                            // Add the property data to Firestore
                                            db.collection("Properties").add(propertyData)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            // Property data added to Firestore successfully
                                                            Toast.makeText(AddPropertyActivity.this, "Property added successfully", Toast.LENGTH_SHORT).show();
                                                            // Clear the form after successful submission
                                                            clearForm();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            // Failed to add property data to Firestore
                                                            Toast.makeText(AddPropertyActivity.this, "Failed to add property", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Failed to upload image
                                    Toast.makeText(AddPropertyActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(AddPropertyActivity.this, "Please upload an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            imageUri = data.getData();
            // Display the selected image in the ImageView
            imageViewUploaded.setImageURI(imageUri);
        }
    }

    private void clearForm() {
        locationEditText.setText("");
        typeEditText.setText("");
        descriptionEditText.setText("");
        shortDescriptionEditText.setText("");
        ownerNameEditText.setText("");
        contactNoEditText.setText("");
        priceEditText.setText("");
        // Clear image if needed
        imageViewUploaded.setImageDrawable(null);
    }
}
