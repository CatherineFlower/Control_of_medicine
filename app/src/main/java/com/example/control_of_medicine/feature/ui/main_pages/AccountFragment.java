package com.example.control_of_medicine.feature.ui.main_pages;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.control_of_medicine.R;
import com.example.control_of_medicine.databinding.FragmentAccountBinding;
import com.example.control_of_medicine.databinding.FragmentHomeBinding;
import com.example.control_of_medicine.feature.presentation.AccountViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private AccountViewModel mViewModel;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = "Email: " + user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            if(!Objects.equals(name, "") && name != null){
                name = "Имя: " + name;
                binding.name.setText(name);
            }
            else{
                binding.name.setText("Имя: Нет имени пользователя 😔");
            }
            binding.email.setText(email);
        }
        binding.signoutbtn.setOnClickListener(view -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            Fragment_OutSign dialogFragment = new Fragment_OutSign();
            dialogFragment.show(fragmentManager, "dialog_OutSign");
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
    }

}