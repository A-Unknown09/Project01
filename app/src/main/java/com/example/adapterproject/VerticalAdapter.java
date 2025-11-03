package com.example.adapterproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder> {

    private List<List<User>> verticalList;
    private OnUserClickListener listener;

    public VerticalAdapter(List<List<User>> verticalList, OnUserClickListener listener) {
        this.verticalList = verticalList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vertical, parent, false);
        return new VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder holder, int position) {
        holder.tvCategory.setText("Category " + (position + 1));

        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(verticalList.get(position), listener);
        holder.horizontalRecyclerView.setLayoutManager(
                new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        holder.horizontalRecyclerView.setAdapter(horizontalAdapter);
    }

    @Override
    public int getItemCount() {
        return verticalList.size();
    }

    static class VerticalViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;
        RecyclerView horizontalRecyclerView;

        VerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontalRecyclerView);
        }
    }
}
