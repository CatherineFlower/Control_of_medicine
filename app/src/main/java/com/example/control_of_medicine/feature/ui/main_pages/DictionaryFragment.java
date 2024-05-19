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
import com.example.control_of_medicine.databinding.FragmentDictionaryBinding;
import com.example.control_of_medicine.domain.model.DictionaryItem;
import com.example.control_of_medicine.domain.model.DictionaryItemAdapter;
import com.example.control_of_medicine.domain.util.OnDictionaryItemsLoaded;
import com.example.control_of_medicine.feature.presentation.DictionaryViewModel;

import java.util.List;

public class DictionaryFragment extends Fragment {

    private FragmentDictionaryBinding binding;

    private DictionaryViewModel mViewModel;

    public static DictionaryFragment newInstance() {
        return new DictionaryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDictionaryBinding.inflate(getLayoutInflater());

        DictionaryItemRepository.getItems(items -> {
//            Log.wtf("LOGG", items.toString());
            DictionaryItemAdapter adapter = new DictionaryItemAdapter();
            binding.recycler.setAdapter(adapter);
            adapter.setItems(items);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DictionaryViewModel.class);
    }

}