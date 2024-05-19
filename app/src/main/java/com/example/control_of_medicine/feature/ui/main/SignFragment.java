package com.example.control_of_medicine.feature.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.control_of_medicine.R;
import com.example.control_of_medicine.databinding.FragmentSignBinding;
import com.example.control_of_medicine.feature.presentation.RegistrationViewModel;
import com.example.control_of_medicine.feature.presentation.SignViewModel;
import com.example.control_of_medicine.feature.ui.main_pages.MainPagesActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SignFragment extends Fragment {

    private FragmentSignBinding binding;

    private SignViewModel mViewModel;

    public static SignFragment newInstance() {
        return new SignFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSignBinding.inflate(getLayoutInflater());

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(RegistrationFragment.newInstance());
            }
        });

        binding.signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                signinFirebase(email, password);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignViewModel.class);
    }

    private void signinFirebase(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(requireActivity().getApplicationContext(), "Login success: "
                            + authResult.getUser().getUid(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(requireActivity().getApplicationContext(),
                            MainPagesActivity.class));
                }).addOnFailureListener(e -> Toast.makeText(requireActivity().getApplicationContext(),
                        "SignIn error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void setFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_reg_sign, fragment, null)
                .commit();
    }

}