package com.example.control_of_medicine.feature.ui.main_pages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.control_of_medicine.R;
import com.example.control_of_medicine.databinding.ActivityMainPagesBinding;
import com.example.control_of_medicine.feature.ui.main.SignFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainPagesActivity extends AppCompatActivity {

    private ActivityMainPagesBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainPagesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPagesBinding.inflate(getLayoutInflater(), null, false);
        setContentView(binding.getRoot());

        binding.navigationMain.setOnItemSelectedListener(menuItem -> {
            MainPagesActivity.this.onNavigationItemSelected(menuItem);
            return true;
        });

        setFragment(HomeFragment.newInstance());
    }

    @SuppressLint("NonConstantResourceId")
    private void onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigationDict:
                setFragment(DictionaryFragment.newInstance());
                break;
            case R.id.navigationMap:
                setFragment(MapFragment.newInstance());
                break;
            case R.id.navigationHome:
                setFragment(HomeFragment.newInstance());
                break;
            case R.id.navigationMed:
                setFragment(MedFragment.newInstance());
                break;
            case R.id.navigationAcc:
                setFragment(AccountFragment.newInstance());
                break;
        }
    }
    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_pages, fragment, null)
                .commit();
    }
}
