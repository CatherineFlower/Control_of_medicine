package com.example.control_of_medicine.feature.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.control_of_medicine.R;
import com.example.control_of_medicine.databinding.ActivityMainBinding;
import com.example.control_of_medicine.feature.ui.main_pages.MainPagesActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        setContentView(binding.getRoot());
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            setFragment();
        }
        else{
            startActivity(new Intent(this, MainPagesActivity.class));
            // Если будет крашиться, то надо вынести в OnResume
        }
    }

    private void setFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_reg_sign, SignFragment.newInstance(), null)
                .commit();
    }
}