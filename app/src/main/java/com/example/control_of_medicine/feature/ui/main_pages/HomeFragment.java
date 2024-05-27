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
import com.example.control_of_medicine.data.HomeItemsRepository;
import com.example.control_of_medicine.databinding.FragmentHomeBinding;
import com.example.control_of_medicine.domain.model.DictionaryItemAdapter;
import com.example.control_of_medicine.domain.model.HomeItemAdapter;
import com.example.control_of_medicine.feature.presentation.HomeViewModel;
import com.example.control_of_medicine.feature.presentation.RegistrationViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        HomeItemsRepository.getItems(items -> {
//            Log.wtf("LOGG", items.toString());
            if (items != null){
                HomeItemAdapter adapter = new HomeItemAdapter();
                binding.recycler.setAdapter(adapter);
                adapter.setItems(items);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

}