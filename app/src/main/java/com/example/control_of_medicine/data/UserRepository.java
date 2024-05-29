package com.example.control_of_medicine.data;

import com.example.control_of_medicine.domain.model.User;
import com.example.control_of_medicine.domain.model.Error;
import com.example.control_of_medicine.domain.util.ErrorListener;
import com.example.control_of_medicine.domain.util.SuccessListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserRepository {

    private static final String COLLECTION_USERS = "users";
    private static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public static void getUserById(String uid, SuccessListener<User> successListener, ErrorListener errorListener) {
        getUserDocument(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    try {
                        User user = documentSnapshot.toObject(User.class);
                        successListener.onSuccess(user);
                    } catch (ClassCastException exception) {
                        errorListener.onError(Error.CAST);
                    } catch (AssertionError exception) {
                        errorListener.onError(Error.NOT_FOUND);
                    }
                })
                .addOnFailureListener(exception -> {
                    errorListener.onError(getError(exception));
                });
    }

    public static void createUser(FirebaseUser user){
        String id = user.getUid();
        String name = user.getDisplayName();
        String email = user.getEmail();
        if(Objects.equals(name, "") || name == null){
            name = "No_name";
        }
        Map<String, Object> userAdd = new HashMap<>();
        userAdd.put("name", name);
        userAdd.put("email", email);
        firestore.collection(COLLECTION_USERS).document(id)
                .set(userAdd);
    }

    private static DocumentReference getUserDocument(String id) {
        return FirebaseFirestore.getInstance()
                .collection(COLLECTION_USERS)
                .document(id);
    }

    private static Error getError(Exception exception) {
        if (exception instanceof FirebaseNetworkException) {
            return Error.NETWORK;
        } else {
            return Error.OTHER;
        }
    }
}
