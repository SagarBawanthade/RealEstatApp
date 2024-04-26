package com.example.realestateapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestateapp.R;
import com.example.realestateapp.model.FavouriteProperty;
import com.example.realestateapp.screens.DetailsActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class FavouriteAdapter  extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder>{
    private Context context;
    private List<FavouriteProperty> favouriteList;

    private FirebaseFirestore firestore;

    public FavouriteAdapter(Context context, List<FavouriteProperty> favouriteList) {
        this.context = context;
        this.favouriteList = favouriteList;


        firestore = FirebaseFirestore.getInstance();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.property_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavouriteProperty favoriteProperty = favouriteList.get(position);

        // Bind data to ViewHolder views
        holder.locationTextView.setText(favoriteProperty.getLocation());
        holder.propertyTitleTextView.setText(favoriteProperty.getShortDescription());
        holder.priceTextView.setText(favoriteProperty.getPrice());
        holder.rent_sellTextView.setText(favoriteProperty.getType());
        // Load image using Glide or Picasso
        Glide.with(context).load(favoriteProperty.getFavImageUrl()).into(holder.propertyImageView);





        // Set onClickListener for propertyImageView  WIll to property detail page
        holder.propertyImageView.setOnClickListener(v -> {
            // Navigate to property detail page with property details
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("location", favoriteProperty.getLocation()); // Pass location
            intent.putExtra("price", favoriteProperty.getPrice()); // Pass price
            intent.putExtra("shortdescription", favoriteProperty.getShortDescription()); // Pass short description
            intent.putExtra("imageuri", favoriteProperty.getFavImageUrl());
            intent.putExtra("contactno", favoriteProperty.getContactno());
            intent.putExtra("description", favoriteProperty.getDescription());
            intent.putExtra("type", favoriteProperty.getType());
            intent.putExtra("ownername", favoriteProperty.getOwnername());
            context.startActivity(intent);


        });

        holder.RemoveFavourite.setOnClickListener(v -> {
            // Query the "Favorites" collection based on the contactno field
            firestore.collection("Favorites")
                    .whereEqualTo("contactno", favoriteProperty.getContactno()) // Adjust this query based on your data model
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        // Loop through the query results
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Get the reference to the document
                            DocumentReference docRef = documentSnapshot.getReference();
                            // Delete the document
                            docRef.delete()
                                    .addOnSuccessListener(aVoid -> {
                                        // Document successfully deleted
                                        Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Failed to delete document
                                        Toast.makeText(context, "Failed to remove from favorites", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Error querying documents
                        Toast.makeText(context, "Failed to remove from favorites", Toast.LENGTH_SHORT).show();
                    });
        });


    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView propertyImageView;

        private TextView RemoveFavourite,propertyTitleTextView,locationTextView,priceTextView,rent_sellTextView ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyTitleTextView = itemView.findViewById(R.id.propertyTitleTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            rent_sellTextView = itemView.findViewById(R.id.rent_sellTextView);
            propertyImageView = itemView.findViewById(R.id.propertyImageView);
            RemoveFavourite = itemView.findViewById(R.id.remove_fav_button);

        }
    }

}
