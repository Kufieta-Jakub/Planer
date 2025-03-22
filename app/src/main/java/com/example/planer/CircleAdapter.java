package com.example.planer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ViewHolder> {
    private List<Integer> images;

    // Konstruktor przyjmujący listę obrazków
    public CircleAdapter(List<Integer> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_item, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(layoutParams);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Ustawianie obrazka dla elementu
        holder.image.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        // Zwracamy liczbę elementów w adapterze
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image); // Znalezienie ImageView w circle_item.xml
        }
    }
}

