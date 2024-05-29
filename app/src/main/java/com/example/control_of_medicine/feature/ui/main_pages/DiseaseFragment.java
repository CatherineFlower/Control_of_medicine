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
import com.example.control_of_medicine.databinding.FragmentDiseaseBinding;
import com.example.control_of_medicine.feature.presentation.AddMedsViewModel;
import com.example.control_of_medicine.feature.presentation.DiseaseViewModel;

public class DiseaseFragment extends Fragment {

    private DiseaseViewModel mViewModel;
    private FragmentDiseaseBinding binding;

    public static DiseaseFragment newInstance() {
        return new DiseaseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDiseaseBinding.inflate(getLayoutInflater());

        binding.medsFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(MedFragment.newInstance());
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(AddDiseaseFragment.newInstance());
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DiseaseViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_pages, fragment, null)
                .addToBackStack("Go back")
                .commit();
    }
}