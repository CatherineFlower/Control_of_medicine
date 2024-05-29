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
        binding.name.setText(item.getName());
        String string = item.getDescription().replace("\\n ", "\n");
        binding.description.setText(string);
        binding.type.setText(item.getType());
    }
}
