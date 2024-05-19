package com.example.control_of_medicine.feature.ui.main_pages;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.control_of_medicine.databinding.ItemHomeBinding;
import com.example.control_of_medicine.domain.model.HomeItem;

public class HomeItemViewHolder extends ViewHolder {
    private ItemHomeBinding binding;

    public HomeItemViewHolder(ItemHomeBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(HomeItem item, int position) {
//        binding.id.setText(String.valueOf(position + 1));
        binding.name.setText(item.getName());
//        binding.day.setText(item.getDay());
//        binding.time.setText(item.getTime());
    }
}
