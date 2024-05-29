package com.example.control_of_medicine.data;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.example.control_of_medicine.domain.model.Error;
import com.example.control_of_medicine.domain.model.HomeItem;
import com.example.control_of_medicine.domain.model.MedItem;
import com.example.control_of_medicine.domain.model.User;
import com.example.control_of_medicine.domain.util.ErrorListener;
import com.example.control_of_medicine.domain.util.OnHomeItemsLoaded;
import com.example.control_of_medicine.domain.util.OnMedItemsLoaded;
import com.example.control_of_medicine.domain.util.SuccessListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MedItemRepository {

    private static final String COLLECTION_USERS = "users";
    private static final String COLLECTION_MEDS = "medicine";
    private static final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @SuppressLint("StaticFieldLeak")
    private static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static final String COLLECTION_HOME_ITEMS = "home_items";

    public static void getItems(OnMedItemsLoaded OnMedItemsLoaded){
        firestore.collection("users").document(USER_ID).collection(COLLECTION_MEDS)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value == null) {
                            if (error != null) {
                                error.printStackTrace();
                            } else {
                                error.printStackTrace();
                            }
                        } else {
                            try {
                                List<MedItem> items = value.toObjects(MedItem.class);
                                OnMedItemsLoaded.onItemsLoaded(items);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public static void createMed(String idUser, MedItem item){
        Map<String, Object> medAdd = new HashMap<>();
        medAdd.put("name", item.getName());
        medAdd.put("type", item.getType());
        String string = item.getDescription().replace("\\n", " \n ");
        medAdd.put("description", string);
        medAdd.put("dose", item.getDose());
        firestore.collection(COLLECTION_USERS).document(idUser)
                .collection(COLLECTION_MEDS).document().set(medAdd);
    }
}
