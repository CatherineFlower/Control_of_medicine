package com.example.control_of_medicine.feature.ui.main_pages;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.control_of_medicine.databinding.ItemMedBinding;
import com.example.control_of_medicine.domain.model.MedItem;

public class MedItemViewHolder extends RecyclerView.ViewHolder {
    private ItemMedBinding binding;

    public MedItemViewHolder(ItemMedBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(MedItem item){
        binding.name.setText(item.getName());
        binding.type.setText(item.getType());
        binding.dose.setText(item.getDose());
        String string = item.getDescription().replace("\\n", " \n ");
        string = string.replace("\n", " \n ");
        binding.description.setText(string);
    }
}
