package com.example.control_of_medicine.feature.ui.main_pages;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.control_of_medicine.databinding.ItemDictBinding;
import com.example.control_of_medicine.domain.model.DictionaryItem;

public class DictionaryItemViewHolder extends ViewHolder {
    private ItemDictBinding binding;

    public DictionaryItemViewHolder(ItemDictBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(DictionaryItem item, int position) {
        binding.id.setText(String.valueOf(position + 1));
        binding.name.setText(item.getName());
        binding.description.setText(item.getDescription());
        binding.type.setText(item.getType());
    }
}
