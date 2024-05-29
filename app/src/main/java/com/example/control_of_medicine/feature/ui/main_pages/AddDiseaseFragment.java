package com.example.control_of_medicine.feature.ui.main_pages;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.control_of_medicine.R;
import com.example.control_of_medicine.feature.presentation.AddDiseaseViewModel;

public class AddDiseaseFragment extends Fragment {

    private AddDiseaseViewModel mViewModel;

    public static AddDiseaseFragment newInstance() {
        return new AddDiseaseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_disease, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddDiseaseViewModel.class);
        // TODO: Use the ViewModel
    }

}