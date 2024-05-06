package com.example.control_of_medicine.domain.model;

import androidx.annotation.NonNull;
import com.google.firebase.firestore.DocumentId;

public class DictionaryItem {
    // Аннотация позволяет записать в поле класса имя документа
    @DocumentId
    public String id;
    public String name;
    public String type;
    public String description;

    // Пустой конструктор необходим для парсинга модели firestore
    public DictionaryItem() {

    }

    public DictionaryItem(String id, String name, String type, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return "Item {id=" + id + " name=" + name + "}";
    }
}
