package com.example.control_of_medicine.domain.util;

import com.example.control_of_medicine.domain.model.Error;

public interface ErrorListener {

    void onError(Error error);
}

