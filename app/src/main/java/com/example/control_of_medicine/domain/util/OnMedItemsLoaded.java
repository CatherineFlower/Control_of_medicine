package com.example.control_of_medicine.domain.util;

import com.example.control_of_medicine.domain.model.MedItem;

import java.util.List;

public interface OnMedItemsLoaded {
    void onItemsLoaded(List<MedItem> items);
}
