package com.example.control_of_medicine.domain.util;

import com.example.control_of_medicine.domain.model.DictionaryItem;

import java.util.List;

public interface OnDictionaryItemsLoaded {
    void OnItemsLoaded(List<DictionaryItem> items);
}
