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
import com.example.control_of_medicine.data.DictionaryItemRepository;
import com.example.control_of_medicine.data.MedItemRepository;
import com.example.control_of_medicine.databinding.FragmentMedBinding;
import com.example.control_of_medicine.domain.model.DictionaryItemAdapter;
import com.example.control_of_medicine.domain.model.MedItemAdapter;
import com.example.control_of_medicine.feature.presentation.MedViewModel;

public class MedFragment extends Fragment {

    private FragmentMedBinding binding;
    private MedViewModel mViewModel;

    public static MedFragment newInstance() {
        return new MedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMedBinding.inflate(getLayoutInflater());

        binding.virusGrFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(DiseaseFragment.newInstance());
            }
        });
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(AddMedsFragment.newInstance());
            }
        });

        MedItemRepository.getItems(items -> {
//            Log.wtf("LOGG", items.toString());
            MedItemAdapter adapter = new MedItemAdapter();
            binding.recycler.setAdapter(adapter);
            adapter.setItems(items);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MedViewModel.class);
    }

    private void setFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_pages, fragment, null)
                .addToBackStack("Go back")
                .commit();
    }

}