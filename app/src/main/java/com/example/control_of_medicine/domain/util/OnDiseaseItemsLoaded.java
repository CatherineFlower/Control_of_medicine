package com.example.control_of_medicine.domain.util;

import com.example.control_of_medicine.domain.model.DiseaseItem;

import java.util.List;

public interface OnDiseaseItemsLoaded {

    void onItemsLoaded(List<DiseaseItem> items);
}
