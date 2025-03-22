package com.example.planer;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ViewHolder> {
    private List<Integer> images;

    // Construcor get list of pictures
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

        // Ustawienie kliknięcia na element
        holder.itemView.setOnClickListener(v -> {
            // Odbieramy id obrazka
            int imageRes = images.get(position);

            Intent intent = null;

            // Sprawdzanie, który obrazek jest kliknięty i uruchamianie odpowiedniej aktywności
            if (imageRes == R.drawable.calendar) {
                intent = new Intent(holder.itemView.getContext(), CalendarActivity.class);
            } else if (imageRes == R.drawable.cloudy) {
                intent = new Intent(holder.itemView.getContext(), MainActivity.class);
            }

            // Jeśli mamy intent, uruchamiamy aktywność z animacją
            if (intent != null) {
                // Uruchamianie aktywności
                holder.itemView.getContext().startActivity(intent);

                // Jeżeli kontekst to MainActivity, to animacja przejścia będzie wykonywana w tej aktywności
                if (holder.itemView.getContext() instanceof MainActivity) {
                    ((MainActivity) holder.itemView.getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else if (holder.itemView.getContext() instanceof CalendarActivity) {
                    ((CalendarActivity) holder.itemView.getContext()).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        // Returning the size to adapter
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image); // Finding ImageView in circle_item.xml
        }
    }
}

