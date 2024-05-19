package com.example.control_of_medicine.domain.model;

import androidx.annotation.NonNull;
import com.google.firebase.firestore.DocumentId;

public class HomeItem {
    // Аннотация позволяет записать в поле класса имя документа
    @DocumentId
    public String id;
    public String name;
    public String time;
    public String day;

    // Пустой конструктор необходим для парсинга модели firestore
    public HomeItem() {

    }

    public HomeItem(String id, String name, String time, String day) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.day = day;
    }

    public String getId(){ return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @NonNull
    @Override
    public String toString() {
        return "Item {id=" + id + " name=" + name + "}";
    }
}
