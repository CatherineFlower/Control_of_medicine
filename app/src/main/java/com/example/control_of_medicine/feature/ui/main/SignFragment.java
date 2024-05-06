package com.example.control_of_medicine.feature.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.control_of_medicine.R;
import com.example.control_of_medicine.databinding.FragmentSignBinding;
import com.example.control_of_medicine.feature.presentation.SignViewModel;

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
                setFragment();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_reg_sign, RegistrationFragment.newInstance(), null)
                .commit();
    }

}