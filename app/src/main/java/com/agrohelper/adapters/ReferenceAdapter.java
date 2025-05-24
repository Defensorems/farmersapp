package com.agrohelper.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.agrohelper.R;
import com.agrohelper.models.PlantIdResponse;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceAdapter.ViewHolder> {
    private List<PlantIdResponse.Entity> entities = new ArrayList<>();

    public void submitList(List<PlantIdResponse.Entity> newEntities) {
        entities.clear();
        if (newEntities != null) {
            entities.addAll(newEntities);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reference, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlantIdResponse.Entity entity = entities.get(position);

        // Установка названия
        holder.name.setText(entity.getEntityName());

        // Установка matched_in
        holder.matchedIn.setText("Совпадение: " + entity.getMatchedIn());

        // Установка типа совпадения
        holder.matchedInType.setText("Тип: " + entity.getMatchedInType());

        // Очистка изображения, если нет данных
        holder.image.setImageResource(android.R.color.transparent);
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView matchedIn;
        TextView matchedInType;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.entity_name);
            matchedIn = itemView.findViewById(R.id.matched_in);
            matchedInType = itemView.findViewById(R.id.matched_in_type);
            image = itemView.findViewById(R.id.entity_image);
        }
    }
}