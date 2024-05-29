package com.example.control_of_medicine.domain.business;

import com.example.control_of_medicine.data.UserRepository;
import com.example.control_of_medicine.domain.model.User;
import com.example.control_of_medicine.domain.util.ErrorListener;
import com.example.control_of_medicine.domain.util.SuccessListener;

public class GetUser {

    public static void getUserById(String uid, SuccessListener<User> successListener, ErrorListener errorListener) {
        UserRepository.getUserById(uid, successListener, errorListener);
    }
}
