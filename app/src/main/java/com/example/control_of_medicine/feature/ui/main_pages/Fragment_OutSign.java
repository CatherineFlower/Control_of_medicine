package com.example.control_of_medicine.feature.ui.main_pages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.control_of_medicine.feature.ui.main.MainActivity;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class Fragment_OutSign extends DialogFragment {

    public Fragment_OutSign() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = "Вы уверены, что хотите выйти?";
        String button1String = "Да";
        String button2String = "Нет";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setPositiveButton(button1String, (dialog, id) -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(requireContext(), MainActivity.class));
        });
        builder.setNegativeButton(button2String, (dialog, id) -> dismiss());
        builder.setCancelable(true);

        return builder.create();
    }
}
