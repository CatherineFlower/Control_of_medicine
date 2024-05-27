package com.example.control_of_medicine.domain.util;

import com.example.control_of_medicine.domain.model.HomeItem;

import java.util.List;

public interface OnHomeItemsLoaded {

    void onItemsLoaded(List<HomeItem> items);
}