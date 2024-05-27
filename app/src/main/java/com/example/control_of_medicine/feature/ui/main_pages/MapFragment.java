package com.example.control_of_medicine.feature.ui.main_pages;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.control_of_medicine.Keys;
import com.example.control_of_medicine.R;
import com.example.control_of_medicine.databinding.FragmentHomeBinding;
import com.example.control_of_medicine.databinding.FragmentMapBinding;
import com.example.control_of_medicine.feature.presentation.AccountViewModel;
import com.example.control_of_medicine.feature.presentation.MapViewModel;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

import java.util.ArrayList;

public class MapFragment extends Fragment {

    private Keys keys;
    private final String MAPKIT_API_KEY = Keys.getMAPKIT_API_KEY();
    private FragmentMapBinding binding;
    private MapViewModel mViewModel;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(requireActivity().getApplicationContext());
        binding = FragmentMapBinding.inflate(getLayoutInflater());

        binding.mapview.getMapWindow().getMap().move(new CameraPosition(new Point(59, 30), 13f, 0f, 0f),
                new Animation(Animation.Type.SMOOTH, 300f), null);

        return binding.getRoot();
    }

    private void requestLocationPermission(){
        if(ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity().getApplicationContext(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CALL);
        }
    }

    @Override
    public void onStop() {
        binding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        binding.mapview.onStart();
        MapKitFactory.getInstance().onStart();
        super.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MapViewModel.class);
    }

}