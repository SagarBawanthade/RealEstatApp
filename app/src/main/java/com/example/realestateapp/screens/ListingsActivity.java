package com.example.realestateapp.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestateapp.R;
import com.example.realestateapp.adapters.ListingAdapter;
import com.example.realestateapp.model.Item2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListingAdapter adapter;
    private List<Item2> item2List;

    private ImageButton backButton;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_listing);

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.listing_recycler);
        item2List = new ArrayList<>();
        adapter = new ListingAdapter(item2List,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        backButton = findViewById(R.id.back_button2);


        // Set onClickListener for backButton
        backButton.setOnClickListener(v -> {
            // Navigate back to the previous activity
            finish();
        });


        // Retrieve the category from the intent extras
        String category = getIntent().getStringExtra("category");

        // Fetch property data from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Properties")
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                // Retrieve property data from each document
                                String name = document.getString("shortdescription");
                                String description = document.getString("description");
                                String ownername = document.getString("ownername");
                                String price = document.getString("price");
                                String type = document.getString("type");
                                String imageuri = document.getString("imageuri");
                                String contactno = document.getString("contactno");
                                String location = document.getString("location");
                                // Retrieve other property fields as needed

                                // Create Item2 object
                                Item2 item = new Item2(location, name, price, imageuri,description,ownername,contactno,type);

                                // Add item to the list
                                item2List.add(item);
                            }
                            // Notify the adapter that data set has changed
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("ListingsActivity", "Error getting documents:", task.getException());
                        }
                    }
                });
    }
}
