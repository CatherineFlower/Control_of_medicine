package com.example.control_of_medicine.domain.model;

import com.google.firebase.firestore.DocumentId;

import java.sql.DataTruncation;
import java.util.List;
import java.util.Map;

public class MedItem {

    @DocumentId
    public String id;
    public String name;
    public String description;
    public String type;
    public String dose;
//    public int dayCount;
//    public List<Integer> dayTime; // часы, когда нужно принять таблетку, пока не реализован выбор вручную можно просто разделить день на количество приемов
//    public String repeatType; // (day, week, month)
//    public List<Integer> repeatWeekDays; // 0, 1, 2, 3 - пн, вт, ср, чт
//    public List<Integer> repeatMonthDays; // 13, 15, 21 - числа месяца
//    public Map<String, Integer> takeCount; // Словарь, где ключ - строка с датой приема вида день.месяц.год, значение - колво принятых раз

    public MedItem() {

    }

    public MedItem(String id, String name, String type, String description, String dose) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.dose = dose;
//        this.dayCount = dayCount;
    }

    public MedItem(String name, String type, String description, String dose) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.dose = dose;
//        this.dayCount = dayCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
