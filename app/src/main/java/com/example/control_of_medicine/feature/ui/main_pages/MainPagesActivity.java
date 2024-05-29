package com.example.control_of_medicine.feature.ui.main_pages;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.control_of_medicine.R;
import com.example.control_of_medicine.databinding.ActivityMainPagesBinding;
import com.example.control_of_medicine.feature.ui.main.RegistrationFragment;

public class MainPagesActivity extends AppCompatActivity {

    private ActivityMainPagesBinding binding;

    public static Intent creatIntent(Context context) {
        return new Intent(context, MainPagesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPagesBinding.inflate(getLayoutInflater(), null, false);
        setContentView(binding.getRoot());
        requestLocationPermission();

        binding.navigationMain.setSelectedItemId(R.id.navigationHome);

        binding.navigationMain.setOnItemSelectedListener(menuItem -> {
            MainPagesActivity.this.onNavigationItemSelected(menuItem);
            return true;
        });

        setFragment(HomeFragment.newInstance());
    }

    private void requestLocationPermission(){
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.navigationDict) {
            setFragment(DictionaryFragment.newInstance());
        } else if (itemId == R.id.navigationMap) {
            setFragment(MapFragment.newInstance());
        } else if (itemId == R.id.navigationHome) {
            setFragment(HomeFragment.newInstance());
        } else if (itemId == R.id.navigationMed) {
            setFragment(MedFragment.newInstance());
        } else if (itemId == R.id.navigationAcc) {
            setFragment(AccountFragment.newInstance());
        }
    }
    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_pages, fragment, null)
                .addToBackStack("Go back")
                .commit();
    }
}
