package com.example.control_of_medicine.domain.model;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.control_of_medicine.databinding.ItemHomeBinding;
import com.example.control_of_medicine.feature.ui.main_pages.HomeItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HomeItemAdapter extends Adapter<HomeItemViewHolder> {
    private List<HomeItem> items = new ArrayList<>();

    @NonNull
    @Override
    public HomeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHomeBinding binding = ItemHomeBinding.inflate(inflater, parent, false);
        return new HomeItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemViewHolder holder, int position) {
        HomeItem item = items.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<HomeItem> items) {
        int itemCount = getItemCount();
        this.items = new ArrayList<>(items);
        notifyItemRangeChanged(0, Math.max(itemCount, getItemCount()));
    }
}
