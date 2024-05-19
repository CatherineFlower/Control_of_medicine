package com.example.control_of_medicine.data;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.example.control_of_medicine.domain.model.DictionaryItem;
import com.example.control_of_medicine.domain.util.OnDictionaryItemsLoaded;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class HomeItemsRepository {
    @SuppressLint("StaticFieldLeak")
    private static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static final String COLLECTION_HOME_ITEMS = "home_items";

    public static void getItems(OnDictionaryItemsLoaded OnDictionaryItemsLoaded){
        firestore.collection(COLLECTION_HOME_ITEMS)
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
                                List<DictionaryItem> items = value.toObjects(DictionaryItem.class);
                                OnDictionaryItemsLoaded.OnItemsLoaded(items);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
