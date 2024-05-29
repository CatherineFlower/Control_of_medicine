package com.example.control_of_medicine.domain.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;
import java.util.List;

public class User {

    // Аннотация позволяет записать в поле класса имя документа
    @DocumentId
    public String id;
    public String name;
    public String email;
    public List<HomeItem> dayItems;
    public List<MedItem> medItems;
    public List<DiseaseItem> diseaseCollectList;

    // Пустой конструктор необходим для парсинга модели firestore
    public User() {

    }

    public User(String name, String email) {
        this.email = email;
        this.name = name;
        this.dayItems = new ArrayList<>();
        this.medItems = new ArrayList<>();
        this.diseaseCollectList = new ArrayList<>();
    }

    @NonNull
    @Override
    public String toString() {
        return "User = {id=" + id + ", email=" + email + ", name=" + name + ", dayItems=" + dayItems + "}";
    }
}
