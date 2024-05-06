package com.example.control_of_medicine.domain.model;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.control_of_medicine.databinding.ItemDictBinding;
import com.example.control_of_medicine.feature.ui.main_pages.DictionaryItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DictionaryItemAdapter extends Adapter<DictionaryItemViewHolder> {
    private List<DictionaryItem> items = new ArrayList<>();

    @NonNull
    @Override
    public DictionaryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDictBinding binding = ItemDictBinding.inflate(inflater, parent, false);
        return new DictionaryItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryItemViewHolder holder, int position) {
        DictionaryItem item = items.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<DictionaryItem> items) {
        int itemCount = getItemCount();
        this.items = new ArrayList<>(items);
        notifyItemRangeChanged(0, Math.max(itemCount, getItemCount()));
    }
}
