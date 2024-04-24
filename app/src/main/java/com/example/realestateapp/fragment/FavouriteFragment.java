package com.example.realestateapp.fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestateapp.R;
import com.example.realestateapp.adapters.FavouriteAdapter;
import com.example.realestateapp.model.FavouriteProperty;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {
    private RecyclerView favouriteRecyclerView;
    private FavouriteAdapter favouriteAdapter;
    private List<FavouriteProperty> favouriteList;

    // Override onCreateView to inflate the layout for this fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        favouriteRecyclerView = view.findViewById(R.id.top_deal_RV2);

        // Initialize the list to hold favorite properties
        favouriteList = new ArrayList<>();

        // Set up RecyclerView adapter
        favouriteAdapter = new FavouriteAdapter(getContext(), favouriteList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        favouriteRecyclerView.setLayoutManager(layoutManager);
        favouriteRecyclerView.setAdapter(favouriteAdapter);

        // Fetch data from Firebase and update the adapter
        fetchFavoriteProperties();

        return view;
    }

    private void fetchFavoriteProperties() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Assuming "Favorites" is your collection name
        db.collection("Favorites")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        favouriteList.clear(); // Clear the list before adding new data
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convert document snapshot to your FavoriteProperty model class
                            String location = document.getString("location");
                            String type = document.getString("type");
                            String price = document.getString("price");
                            String shortdescription = document.getString("shortdescription");
                            String imageuri = document.getString("imageuri");
                            String ownername = document.getString("ownername");
                            String description = document.getString("description");
                            String contactno = document.getString("contactno");

                            // Create a new FavouriteProperty object
                            FavouriteProperty favouriteProperty = new FavouriteProperty(imageuri,location, type, price, shortdescription,ownername,description,contactno);
                            favouriteList.add(favouriteProperty);
                        }
                        favouriteAdapter.notifyDataSetChanged(); // Notify adapter that data has changed
                    } else {
                        Log.d(TAG, "Error getting favorite properties: ", task.getException());
                    }
                });
    }
}
