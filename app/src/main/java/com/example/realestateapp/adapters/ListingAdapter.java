package com.example.realestateapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestateapp.R;
import com.example.realestateapp.model.Item2;
import com.example.realestateapp.screens.DetailsActivity;

import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder>{

    private Context context;
    private List<Item2> item2List;

    public ListingAdapter(List<Item2> item2List , Context context) {
        this.context = context;
        this.item2List = item2List;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item2 item = item2List.get(position);
        holder.location2.setText(item.getLocation());
        holder.name1.setText(item.getName());
        holder.price3.setText(item.getPrice());
        // Load image using Glide or Picasso
        Glide.with(holder.itemView.getContext()).load(item.getImageUrl()).into(holder.bg4);



        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open detail activity and pass item data
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("location", item.getLocation()); // Pass location
                intent.putExtra("price", item.getPrice()); // Pass price
                intent.putExtra("shortdescription", item.getName()); // Pass short description
                intent.putExtra("imageuri", item.getImageUrl());
                intent.putExtra("contactno", item.getContactno());
                intent.putExtra("description", item.getDescription());
                intent.putExtra("type", item.getType());
                intent.putExtra("ownername", item.getOwnername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item2List.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bg4;
        TextView name1, location2, price3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name1 = itemView.findViewById(R.id.name1);
            location2 = itemView.findViewById(R.id.location2);
            price3 = itemView.findViewById(R.id.price3);
            bg4 = itemView.findViewById(R.id.bg4);
        }
    }
}
