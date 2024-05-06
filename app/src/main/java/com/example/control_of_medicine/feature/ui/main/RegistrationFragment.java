package com.example.control_of_medicine.feature.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.control_of_medicine.R;
import com.example.control_of_medicine.databinding.FragmentRegistrationBinding;
import com.example.control_of_medicine.feature.presentation.RegistrationViewModel;

import com.example.control_of_medicine.feature.ui.main_pages.MainPagesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Objects;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;

    private RegistrationViewModel mViewModel;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(getLayoutInflater());

        binding.signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment();
            }
        });

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithEmail();
            }
        });

        FirebaseAuth.getInstance().getCurrentUser();

        return binding.getRoot();
    }

    private void loginWithEmail() {
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();
        if (validateFields(email, password)) {
            signupFirebase(email, password);
        }
    }

    public Boolean validateFields(String email, String password) {
        if (!email.contains("@")) {
            binding.email.setError("Wrong email");
            return false;
        }
        if (password.isEmpty()) {
            binding.password.setError("Password should not be empty");
            return false;
        }
        return true;
    }

    private void signupFirebase(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
            Toast.makeText(requireActivity().getApplicationContext(), "Login success: "
                    + authResult.getUser().getUid(), Toast.LENGTH_SHORT).show();

            RegistrationFragment.this.startActivity(MainPagesActivity
                    .creatIntent(requireActivity().getApplicationContext()));
        }).addOnFailureListener(e -> {
            if (e instanceof FirebaseAuthUserCollisionException) {
                signinFirebase(email, password);
            } else {
                Toast.makeText(requireActivity().getApplicationContext(),
                        "SignUp error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signinFirebase(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
            Toast.makeText(requireActivity().getApplicationContext(),
                    "Login success: " + authResult.getUser().getUid(), Toast.LENGTH_SHORT).show();
//            showUser();
        }).addOnFailureListener(e -> Toast.makeText(requireActivity().getApplicationContext(),
                        "SignIn error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
    }

    private void setFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_reg_sign,
                SignFragment.newInstance(), null).commit();
    }

}