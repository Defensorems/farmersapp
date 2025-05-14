package com.agrohelper.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.agrohelper.R;
import com.agrohelper.activities.PlantDetailActivity;
import com.agrohelper.models.Plant;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {
    private final Context context;
    private List<Plant> plants;
    private final SimpleDateFormat dateFormat;

    public PlantAdapter(Context context) {
        this.context = context;
        this.dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        if (plants != null && position < plants.size()) {
            Plant plant = plants.get(position);
            
            holder.plantName.setText(plant.getName());
            holder.plantType.setText(plant.getType());
            
            if (plant.getDatePlanted() != null) {
                holder.plantDate.setText(dateFormat.format(plant.getDatePlanted()));
            }
            
            if (plant.getPhotoUri() != null && !plant.getPhotoUri().isEmpty()) {
                Glide.with(context)
                        .load(Uri.parse(plant.getPhotoUri()))
                        .placeholder(R.drawable.placeholder_plant)
                        .error(R.drawable.placeholder_plant)
                        .centerCrop()
                        .into(holder.plantImage);
            } else {
                holder.plantImage.setImageResource(R.drawable.placeholder_plant);
            }
            
            holder.cardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, PlantDetailActivity.class);
                intent.putExtra("plantId", plant.getId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return plants != null ? plants.size() : 0;
    }

    static class PlantViewHolder extends RecyclerView.ViewHolder {
        final CardView cardView;
        final ImageView plantImage;
        final TextView plantName;
        final TextView plantType;
        final TextView plantDate;

        PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            plantImage = itemView.findViewById(R.id.plant_image);
            plantName = itemView.findViewById(R.id.plant_name);
            plantType = itemView.findViewById(R.id.plant_type);
            plantDate = itemView.findViewById(R.id.plant_date);
        }
    }
}
