package com.agrohelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agrohelper.R;
import com.agrohelper.models.ReferenceItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceAdapter.ReferenceViewHolder> {
    private final Context context;
    private List<ReferenceItem> referenceItems;

    public ReferenceAdapter(Context context, List<ReferenceItem> referenceItems) {
        this.context = context;
        this.referenceItems = referenceItems;
    }

    public void updateData(List<ReferenceItem> newItems) {
        this.referenceItems = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReferenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reference, parent, false);
        return new ReferenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReferenceViewHolder holder, int position) {
        ReferenceItem item = referenceItems.get(position);
        
        holder.nameTextView.setText(item.getName());
        holder.typeTextView.setText(item.getType());
        holder.descriptionTextView.setText(item.getDescription());
        
        // Load image
        Glide.with(context)
                .load(item.getImageResId())
                .centerCrop()
                .into(holder.imageView);
        
        // Set up expand/collapse functionality
        holder.itemView.setOnClickListener(v -> {
            boolean isExpanded = holder.careInstructionsTextView.getVisibility() == View.VISIBLE;
            holder.careInstructionsTextView.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return referenceItems.size();
    }

    static class ReferenceViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameTextView;
        final TextView typeTextView;
        final TextView descriptionTextView;
        final TextView careInstructionsTextView;

        ReferenceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.reference_image);
            nameTextView = itemView.findViewById(R.id.reference_name);
            typeTextView = itemView.findViewById(R.id.reference_type);
            descriptionTextView = itemView.findViewById(R.id.reference_description);
            careInstructionsTextView = itemView.findViewById(R.id.reference_care_instructions);
        }
    }
}
