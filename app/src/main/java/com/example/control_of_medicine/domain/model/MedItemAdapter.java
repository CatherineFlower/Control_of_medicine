package com.example.control_of_medicine.domain.model;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.control_of_medicine.databinding.ItemDictBinding;
import com.example.control_of_medicine.databinding.ItemMedBinding;
import com.example.control_of_medicine.feature.ui.main_pages.DictionaryItemViewHolder;
import com.example.control_of_medicine.feature.ui.main_pages.MedItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MedItemAdapter extends RecyclerView.Adapter<MedItemViewHolder> {
    private List<MedItem> items = new ArrayList<>();

    @NonNull
    @Override
    public MedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMedBinding binding = ItemMedBinding.inflate(inflater, parent, false);
        return new MedItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedItemViewHolder holder, int position) {
        MedItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<MedItem> items) {
        int itemCount = getItemCount();
        this.items = new ArrayList<>(items);
        notifyItemRangeChanged(0, Math.max(itemCount, getItemCount()));
    }
}
