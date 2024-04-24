package com.example.realestateapp.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.realestateapp.R;
import com.example.realestateapp.adapters.HomeAdapter;
import com.example.realestateapp.adapters.PropertyHomeAdapter;
import com.example.realestateapp.listeners.ItemListener;
import com.example.realestateapp.model.Item;
import com.example.realestateapp.model.Property;
import com.example.realestateapp.screens.DetailsActivity;
import com.example.realestateapp.screens.SearchActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment implements ItemListener {

    private RecyclerView topDealRV ,topDealRV1;
    private HomeAdapter adapter;
    private TextView userName;
    private List<Item> itemList;

    private EditText Search;


    private CircleImageView profileImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName = view.findViewById(R.id.user_name);
        profileImage = view.findViewById(R.id.profile_image);
        topDealRV = view.findViewById(R.id.top_deal_RV);
        topDealRV1 = view.findViewById(R.id.top_deal_RV1);
        Search = view.findViewById(R.id.search);

        Search.setOnClickListener(v -> {
            // Start the new activity when the EditText is clicked
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });





        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // Set user's name from Firestore to TextView
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users")
                    .document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String name = document.getString("name");
                                userName.setText("Welcome back, " + name);

                                // Load user's profile image using Picasso
                                String imageUrl = document.getString("imageUrl");
                                if (imageUrl != null) {
                                    Picasso.get().load(imageUrl).into(profileImage);
                                }
                            }
                        }
                    });
        }

       ///For The Category
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Category")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Property> propertyList = new ArrayList<>(); // Create a new list for the second RecyclerView
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve data for the second RecyclerView
                            String title = document.getString("title");
                            String imageuri = document.getString("imageuri");
                            String category = document.getString("category");


                            // Create a new property object
                            Property property = new Property(imageuri,title,category);

                            // Add the property to the itemList2
                            propertyList.add(property);

                        }

                        // Create adapter for the second RecyclerView
                        PropertyHomeAdapter adapter2 = new PropertyHomeAdapter(getContext(), propertyList, this);
                        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
                        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
                        topDealRV1.setLayoutManager(layoutManager2);
                        topDealRV1.setAdapter(adapter2);
                    } else {
                        Log.d(TAG, "Error getting properties categories: ", task.getException());
                    }
                });



        itemList = new ArrayList<>();
        // Fetch property data from Firestore
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Properties")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String location = document.getString("location");
                            String price = document.getString("price");
                            String shortDescription = document.getString("shortdescription");
                            String imageuri = document.getString("imageuri");
                            String ownername = document.getString("ownername");
                            String type = document.getString("type");
                            String contactno = document.getString("contactno");
                            String description = document.getString("description");


                            // Create a new Item object with the retrieved data
                            Item item = new Item(location, price, shortDescription, imageuri, contactno, ownername, type,description);

                            // Add the item to the itemList
                            itemList.add(item);
                        }


                        adapter = new HomeAdapter(getContext(), itemList, this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        topDealRV.setLayoutManager(linearLayoutManager);
                        topDealRV.setAdapter(adapter);

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void OnItemPosition(int position) {

        Item selectedItem = itemList.get(position);

        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra("location", selectedItem.getLocation()); // Pass location
        intent.putExtra("price", selectedItem.getPrice()); // Pass price
        intent.putExtra("shortdescription", selectedItem.getShortDescription()); // Pass short description
        intent.putExtra("imageuri", selectedItem.getImageUri());
        intent.putExtra("contactno", selectedItem.getContactNo());
        intent.putExtra("description", selectedItem.getDescription());
        intent.putExtra("type", selectedItem.getType());
        intent.putExtra("ownername", selectedItem.getOwnerName());


        startActivity(intent);
    }
}