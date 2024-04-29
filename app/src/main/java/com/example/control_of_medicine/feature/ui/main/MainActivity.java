package com.example.control_of_medicine.feature.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.control_of_medicine.R;
import com.example.control_of_medicine.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        setContentView(binding.getRoot());
        
        setFragment();
    }

    private void setFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_reg_sign, SignFragment.newInstance(), null)
                .commit();
    }
}