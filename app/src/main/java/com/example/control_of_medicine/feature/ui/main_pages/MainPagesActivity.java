package com.example.control_of_medicine.feature.ui.main_pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
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

        binding.navigationMain.setSelectedItemId(R.id.navigationHome);

        binding.navigationMain.setOnItemSelectedListener(menuItem -> {
            MainPagesActivity.this.onNavigationItemSelected(menuItem);
            return true;
        });

        setFragment(HomeFragment.newInstance());
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
                .commit();
    }
}
