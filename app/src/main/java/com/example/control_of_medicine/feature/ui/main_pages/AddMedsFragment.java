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
import com.example.control_of_medicine.data.MedItemRepository;
import com.example.control_of_medicine.databinding.FragmentAddMedsBinding;
import com.example.control_of_medicine.databinding.FragmentMedBinding;
import com.example.control_of_medicine.domain.model.MedItem;
import com.example.control_of_medicine.feature.presentation.AddMedsViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class AddMedsFragment extends Fragment {

    private AddMedsViewModel mViewModel;
    private FragmentAddMedsBinding binding;

    public static AddMedsFragment newInstance() {
        return new AddMedsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddMedsBinding.inflate(getLayoutInflater());
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.name.getText().toString();
                String type = binding.type.getText().toString();
                String dose = binding.dose.getText().toString();
                String description = binding.description.getText().toString();
                MedItem item = new MedItem(name, type, description, dose);
                MedItemRepository.createMed(FirebaseAuth.getInstance().getCurrentUser().getUid(), item);
                setFragment();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddMedsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_pages, MedFragment.newInstance(), null)
                .addToBackStack("Go back")
                .commit();
    }

}